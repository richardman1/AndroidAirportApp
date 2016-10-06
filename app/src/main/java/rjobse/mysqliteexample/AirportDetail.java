package rjobse.mysqliteexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AirportDetail extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener{

    private TextView icao;
    private TextView name;
    private TextView iso_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_detail);

        icao = (TextView) findViewById(R.id.airporticao);
        name = (TextView) findViewById(R.id.airportname);
        iso_country = (TextView) findViewById(R.id.airportiso_country);

        Intent i = getIntent();

        Airport airport = (Airport) i.getSerializableExtra("airport");

        icao.setText(airport.getIcao());
        name.setText(airport.getName());
        iso_country.setText(airport.getIso_country());

        MapFragment fragment =(MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_b);

        fragment.setAirport(airport);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
