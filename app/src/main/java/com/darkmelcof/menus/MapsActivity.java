package com.darkmelcof.menus;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity{

    private Button b_depart, b_arrivee;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String depart="", arrivee="";
    private Marker marker;
    private boolean markerSelected;

    private void setup_bouton(){
        b_depart = (Button)  findViewById(R.id.depart);
        b_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerSelected)
                    depart = marker.getTitle();
                Toast.makeText(MapsActivity.this, "Depart Set to : "+depart, Toast.LENGTH_LONG).show();

                // Choix et retour au menu
                if (depart!= "" && arrivee!=""){
                    Intent result = new Intent();
                    result.putExtra("depart", depart);
                    result.putExtra("arrivee", arrivee);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });
        b_arrivee= (Button)  findViewById(R.id.arrivee);
        b_arrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerSelected)
                    arrivee = marker.getTitle();
                Toast.makeText(MapsActivity.this, "Arrival Set to : "+arrivee, Toast.LENGTH_LONG).show();

                // Choix et retour au menu
                if (depart!= "" && arrivee!=""){
                    Intent result = new Intent();
                    result.putExtra("depart", depart);
                    result.putExtra("arrivee", arrivee);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Instancie la map et invoque la fonction des markers
        setUpMapIfNeeded();
        // On annule le zoom
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);

        markerSelected = false;
        setup_bouton();

        /**
         * BULLE INFO AVEC BOUTONS
         * http://stackoverflow.com/questions/14123243/google-maps-android-api-v2-interactive-infowindow-like-in-original-android-go/15040761#15040761
         */

        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                try {
                    markerSelected = true;
                    marker = arg0;
                    Log.d("arg0", arg0.getTitle());
                    Log.d("arg0", arg0.getSnippet());
                } catch (Exception e) {

                }
                // Comportement par defaut centre sur le marker
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    /**
     * Pour modifier les markers suivre la documentation ci-dessuos
     * https://developers.google.com/maps/documentation/android-api/marker
     */
    private void setUpMap() {

        /**
         * RECUPERE INFORMATION DE LA BDD POUR AFFICHER CHAQUE ELEMENTS
         */
        DestinationBDD d = new DestinationBDD(this);
        ArrayList<Destination> liste = new ArrayList<Destination>();

        liste = d.cursorToDestination(d.getCursorDestinations());

        Iterator<Destination> it = liste.iterator();
        while (it.hasNext()){
            Destination dest = it.next();
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(dest.getLatitude()), Double.valueOf(dest.getLongitude()))).title(dest.getNom()).snippet(""));
        }

    }
}
