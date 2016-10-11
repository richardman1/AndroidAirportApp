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
    private TextView distanceToAmsterdam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_detail);

        icao = (TextView) findViewById(R.id.airporticao);
        name = (TextView) findViewById(R.id.airportname);
        iso_country = (TextView) findViewById(R.id.airportiso_country);
        distanceToAmsterdam = (TextView) findViewById(R.id.airportDistanceToAmsterdam);

        Intent i = getIntent();

        Airport airport = (Airport) i.getSerializableExtra("airport");

        icao.setText(airport.getIcao());
        name.setText(airport.getName());
        iso_country.setText(airport.getIso_country());

        MapFragment fragment =(MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_b);

        fragment.setAirport(airport);

        double distance = distance(airport.getLatitude(), 52.30833333, airport.getLongitude(), 4.76805555, airport.getElevation(), -3);

        double roundedDistance = Math.round(distance*100.0)/100.0;
        distanceToAmsterdam.setText(Double.toString(roundedDistance) + " km");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to kilometres

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
