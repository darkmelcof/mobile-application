package com.darkmelcof.menus;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends ActionBarActivity {
    public static final int ID_NOTIFICATION = 2015;
    private Button b_trajet;
    private Button b_transport;
    private Button b_start;
    private EditText tv_depart;
    private EditText tv_arrivee;
    private TextView tv_transport;
    private TextView tv_temps_trajet;
    private String depart = "";
    private String arrivee = "";
    private String transport = "";
    private String temps = "";
    private Destination dest_depart, dest_arrivee;
    private Transport transport_choisi;
    private Trajet trajet;

    // Initialization of the interface variables
    public void setup() {
        b_trajet = (Button) findViewById(R.id.carte);
        b_trajet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        b_transport = (Button) findViewById(R.id.transport);
        b_transport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        b_start = (Button) findViewById(R.id.buttonStart);
        b_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((getDepart() != "") && (getArrivee() != "") && (getTransport() != "") && !tv_depart.getText().equals(tv_arrivee.getText())){

                    DestinationBDD d = new DestinationBDD(getApplicationContext());
                    ArrayList<Destination> listeDestination = new ArrayList<Destination>();

                    listeDestination = d.cursorToDestination(d.getCursorDestinations());

                    //On recupere les destinations
                    Iterator<Destination> it = listeDestination.iterator();
                    while (it.hasNext()){
                        Destination dest = it.next();
                        if (dest.getNom().equals(getDepart())){
                            dest_depart = dest;
                        }
                        if (dest.getNom().equals(getArrivee())){
                            dest_arrivee = dest;
                        }
                    }

                    TransportBDD t = new TransportBDD(getApplicationContext());
                    ArrayList<Transport> listeTransport = new ArrayList<Transport>();

                    listeTransport = t.cursorToTransport(t.getCursorTransport());

                    //On recupere le transport
                    Iterator<Transport> it2 = listeTransport.iterator();
                    while (it2.hasNext()){
                        Transport tr = it2.next();
                        if (tr.getNom().equals(getTransport())){
                            transport_choisi = tr;
                        }
                    }

                    /**
                     * On calcule le temps de trajet
                     */
                    trajet = new Trajet(dest_depart, dest_arrivee, transport_choisi);
                    setTemps(String.valueOf(trajet.getDuree()));
                    tv_temps_trajet.setText(getTemps());

                    createNotification(getTransport(), getTemps());
                    cancelNotify();

                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    startActivity(i);

                    alerteVoyage(getTemps());

                }
                if (getArrivee().equals(getDepart())){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.triche), Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_temps_trajet = (TextView) findViewById(R.id.tempsTrajet);
        tv_transport = (TextView) findViewById(R.id.transportChoisi);
        tv_transport.setText(getTransport());
        tv_depart = (EditText) findViewById(R.id.depart);
        tv_depart.setText(getDepart());
        tv_arrivee = (EditText) findViewById(R.id.arrivee);
        tv_depart.setText(getArrivee());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        cancelNotify();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK){
            setDepart(data.getStringExtra("depart"));
            setArrivee(data.getStringExtra("arrivee"));
            tv_depart.setText(getDepart());
            tv_arrivee.setText(getArrivee());
        }
        if (resultCode == 2){
            setTransport(data.getStringExtra("transport"));
            tv_transport.setText(getTransport());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("depart", getDepart());
        outState.putString("arrivee", getArrivee());
        outState.putString("transport", getTransport());
        outState.putString("temps", getTemps());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        setDepart(savedInstanceState.getString("depart"));
        setArrivee(savedInstanceState.getString("arrivee"));
        setTransport(savedInstanceState.getString("transport"));
        setTemps(savedInstanceState.getString("temps"));
        tv_depart.setText(getDepart());
        tv_arrivee.setText(getArrivee());
        tv_transport.setText(getTransport());
        tv_temps_trajet.setText(getTemps());
    }

    private void cancelNotify(){
        //On créé notre gestionnaire de notfication
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //on supprime la notification grâce à son ID
        notificationManager.cancel(ID_NOTIFICATION);
    }

    private void alerteVoyage(String duree){
        AlarmManager alarmeManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmeManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (Integer.valueOf(duree) * 1000),pendingIntent);
    }

    private void createNotification(String trans, String temps){
        //Récupération du notification Manager
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //Création de la notification avec spécification de l'icône de la notification et le texte qui apparait à la création de la notification
        Notification notification = new Notification(R.drawable.ic_media_play, "En route !", System.currentTimeMillis());

        //Définition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        //On définit le titre de la notif
        String titreNotification = "Le " + trans ;
        //On définit le texte qui caractérise la notif
        String texteNotification = temps +" secondes";

        //Notification & Vibration
        notification.setLatestEventInfo(this, titreNotification, texteNotification, pendingIntent);
        notification.vibrate = new long[] {0,200,100,200,100,200};

        notificationManager.notify(ID_NOTIFICATION, notification);
    }

    private void createNotification(){
        //Récupération du notification Manager
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //Création de la notification avec spécification de l'icône de la notification et le texte qui apparait à la création de la notification
        Notification notification = new Notification(R.drawable.ic_media_play, "En route !", System.currentTimeMillis());

        //Définition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        //On définit le titre de la notif
        String titreNotification = "C'est moi la notification !";
        //On définit le texte qui caractérise la notif
        String texteNotification = "Je suis une belle notification...";
        //Récupération du titre et description de la notification
        //final String notificationTitle = getResources().getString(R.string.notification_title);
        //final String notificationDesc = getResources().getString(R.string.notification_desc);

        //Notification & Vibration
        notification.setLatestEventInfo(this, titreNotification, texteNotification, pendingIntent);
        notification.vibrate = new long[] {0,200,100,200,100,200};

        notificationManager.notify(ID_NOTIFICATION, notification);
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }
}
