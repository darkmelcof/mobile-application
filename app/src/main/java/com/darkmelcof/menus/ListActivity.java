package com.darkmelcof.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Darkmelcof on 18/12/2015.
 */


public class ListActivity extends Activity{
    private String transport = "";
    private Button b_choix;

    private void setup_bouton(){
        b_choix = (Button)findViewById(R.id.transportChoisi);
        b_choix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transport != ""){
                    Intent result = new Intent();
                    result.putExtra("transport", transport);
                    setResult(2, result);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        final ListView listview = (ListView) findViewById(R.id.listview);

        setup_bouton();


        TransportBDD t = new TransportBDD(this);
        ArrayList<Transport> liste = new ArrayList<Transport>();

        liste = t.cursorToTransport(t.getCursorTransport());


        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };*/

       /* final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < liste.size(); ++i) {
            list.add(liste.get(i).getNom());
        }*/

        ArrayAdapter<Transport> adapter2 = new ArrayAdapter<Transport>(this, android.R.layout.simple_list_item_1, liste);

        //final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Transport item = (Transport) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_LONG).show();
                setTransport(item.getNom());
            }

        });
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
