package com.darkmelcof.menus;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class DestinationBDD {

    private SQLiteDatabase bdd;

    private DataBaseHelper maBaseSQLite;

    public DestinationBDD(Context context){
        //On creer la BDD et sa table
        maBaseSQLite = new DataBaseHelper(context);

        // Importation de la base du dossier Assets
        try {
            maBaseSQLite.createDataBase();
            Log.v("Info", "Base importee !");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.open();
    }

    public void open(){
        //on ouvre la BDD en ecriture
        maBaseSQLite.openDataBase();
    }

    public void close(){
        //on ferme l'acces a la BDD
        maBaseSQLite.close();
    }


    public Cursor getCursorDestinations(){
        Cursor c = maBaseSQLite.getBDD().rawQuery("SELECT * FROM Templacement;", null);
        return c;
    }

    public ArrayList cursorToDestination(Cursor c){
        ArrayList<Destination> tab_destination = new ArrayList<Destination>();
        getCursorDestinations();

        // Boucle ligne par ligne
        if(c.moveToFirst()){
            do {
                // TODO
                Destination dest = new Destination(c.getString(0), c.getString(1), c.getString(2));
                tab_destination.add(dest);
            } while (c.moveToNext());
        }else{
            Log.e("Erreur", "Requete vide !" );
        }
        c.close();
        this.close();
        return tab_destination;
    }

}