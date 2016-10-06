package rjobse.mysqliteexample;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String _keyvalue;
    private ExpandableListView airportListView;
    private AirportCursorAdapter airportCursorAdapter;
    HashMap<String, List<Airport>> listChildren;
    List<String> listDataHeader;
    HashMap<String, List<Airport>> searchChilds = null;
    List<String> searchHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listChildren = new HashMap<String, List<Airport>>();
        listDataHeader = new ArrayList<>();

        //Inflate listview
        airportListView = (ExpandableListView) findViewById(R.id.airportsListView);

        List<Airport> groupAirports = new ArrayList<Airport>();
        // init database and query
        AirportsDatabase adb = new AirportsDatabase(this);

        Cursor cursor = adb.getAirports();
        cursor.moveToFirst();

//        // Construct HashMap
//        listChildren = new HashMap<String, List<Airport>>();
//        for(String header : listDataHeader){
//            ArrayList<Airport> items = new ArrayList<Airport>();
//            cursor.moveToFirst();
//            while(cursor.moveToNext()){
//                Airport airport = new Airport();
//                airport.setName(cursor.getString(cursor.getColumnIndex("name")));
//                airport.setIcao(cursor.getString(cursor.getColumnIndex("icao")));
//                airport.setIso_country(cursor.getString(cursor.getColumnIndex("iso_country")));
//
//                if(airport.getIso_country().equals(header)){
//                    items.add(airport);
//                }
//            }
//            listChildren.put(header, items);
//        }


        while (!cursor.isAfterLast()) {
            Airport airport = new Airport();
            airport.setName(cursor.getString(cursor.getColumnIndex("name")));
            airport.setIcao(cursor.getString(cursor.getColumnIndex("icao")));
            airport.setIso_country(cursor.getString(cursor.getColumnIndex("iso_country")));
            airport.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            airport.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));

            if (!listDataHeader.contains(airport.getIso_country())) {
                listDataHeader.add(airport.getIso_country());
                listChildren.put(_keyvalue, groupAirports);

                _keyvalue = airport.getIso_country();
            }

            if (groupAirports.size() != 0) {
                if (groupAirports.get(groupAirports.size() - 1).getIso_country().equals(airport.getIso_country())) {
                    groupAirports.add(airport);
                } else {
                    groupAirports = new ArrayList<Airport>();
                    groupAirports.add(airport);
                }
            } else {
                groupAirports.add(airport);
            }
            cursor.moveToNext();
        }
        if (cursor.isAfterLast()) {
            listChildren.put(_keyvalue, groupAirports);
        }

        airportCursorAdapter = new AirportCursorAdapter(this, listDataHeader, listChildren);
        airportListView.setAdapter(airportCursorAdapter);

        airportListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent i = new Intent(getApplicationContext(), AirportDetail.class);

                Airport airport = null;
                if(searchChilds == null) {
                     airport = listChildren.get(listDataHeader.get(groupPosition)).get(childPosition);
                }
                else{
                     airport = searchChilds.get(searchHeader.get(groupPosition)).get(childPosition);
                }

                i.putExtra("airport", airport);
                startActivity(i);

                return true;
            }
        });

//        airportCursorAdapter.notifyDataSetChanged();

        EditText searchText = (EditText)findViewById(R.id.searchText);
//        searchText.setFocusable(false);
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                HashMap<String, List<Airport>> tempChilds = new HashMap<String, List<Airport>>();
                List<String> tempHeaders = new ArrayList<String>();
                int textlength = cs.length();

                if(textlength > 0) {
                    Iterator it = listChildren.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        ArrayList<Airport> tempArrayList = (ArrayList<Airport>) pair.getValue();
                        ArrayList<Airport> matchedArraylist = new ArrayList<Airport>();
                        _keyvalue = tempArrayList.get(0).getIso_country();

                        for (Airport a : tempArrayList) {
                            if (textlength <= a.getName().length()) {
                                if (a.getName().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    matchedArraylist.add(a);
                                }
                            }
                        }

                        if (matchedArraylist.size() > 0) {
                            tempHeaders.add(_keyvalue);
                            tempChilds.put(_keyvalue, matchedArraylist);
                        }
                    }
                    searchChilds = tempChilds;
                    searchHeader = tempHeaders;
                    airportCursorAdapter = new AirportCursorAdapter(getApplicationContext(), tempHeaders, tempChilds);
                    airportListView.setAdapter(airportCursorAdapter);
                }
                else{
                    searchChilds = null;
                    searchHeader = null;
                    airportCursorAdapter = new AirportCursorAdapter(getApplicationContext(), listDataHeader, listChildren);
                    airportListView.setAdapter(airportCursorAdapter);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
            displayIndex();
    }

    public void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(listChildren.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        String index = selectedIndex.getText().toString();
        int listposition = 0;
        for(int i = 0; i < listDataHeader.size(); i++){
            if(listDataHeader.get(i) == index){
                listposition = i;
            }
        }
        airportListView.setSelection((listposition));
    }
}
