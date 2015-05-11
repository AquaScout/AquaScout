package com.example.aquascout;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ResultsActivity extends FragmentActivity {

    private ListView resultList;
    private String[] results = new String[]{"Result 1", "Result 2", "Result 3",
                "Result 4", "Result 5"};
    private String[] addressList;
    private GoogleMap map;
    private double lat;
    private double lon;
    private Marker marker;
    private ParseGeoPoint parseGeoPoint;
    private ReviewParseObject reviewParseObject;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        MapFragment mapFragment = (MapFragment) this.getFragmentManager().findFragmentById(R.id.googleMap);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setMyLocationEnabled(true);
                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(ll, 12);
                        map.animateCamera(cu);
                        // You can update your location whenever the location is changed
                        // focusOnMarkers(ll);
                    }
                });
            }
        });

        ParseQuery<ReviewParseObject> query = ParseQuery.getQuery("Review");
        query.whereExists("address");
        System.out.println("Before find");
        try {
            List<ReviewParseObject> reviews = query.find();
            Geocoder geocoder = new Geocoder(ResultsActivity.this, Locale.getDefault());
            addressList = new String[reviews.size()];
            System.out.println("Size " + reviews.size());
            for (int i = 0; i < reviews.size(); i++) {
                reviewParseObject = reviews.get(i);
                parseGeoPoint = reviewParseObject.getAddress();
                lat = parseGeoPoint.getLatitude();
                lon = parseGeoPoint.getLongitude();
                try {
                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    addressList[i] = addresses.get(i).getAddressLine(i);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*
        query.findInBackground(new FindCallback<ReviewParseObject>() {
            public void done(List<ReviewParseObject> reviews, ParseException e) {
                System.out.println("Before if");
                if (e == null) {
                    Geocoder geocoder = new Geocoder(ResultsActivity.this, Locale.getDefault());
                    addressList = new String[reviews.size()];
                    System.out.println("Size " + reviews.size());
                    for (int i = 0; i < reviews.size(); i++) {
                        reviewParseObject = reviews.get(i);
                        parseGeoPoint = reviewParseObject.getAddress();
                        lat = parseGeoPoint.getLatitude();
                        lon = parseGeoPoint.getLongitude();
                        try {
                            addresses = geocoder.getFromLocation(lat, lon, 1);
                            addressList[i] = addresses.get(i).toString();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Error " + e);

                }
            }
        });
        */

        resultList = (ListView) findViewById(R.id.resultList);
        resultList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, addressList));

        final TextView addressText = (TextView) findViewById(R.id.addressField);

        Button searchButton = (Button) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geoCoder = new Geocoder(ResultsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocationName(addressText.getText().toString(), 1);
                    Double lat = (double) (addresses.get(0).getLatitude());
                    Double lon = (double) (addresses.get(0).getLongitude());

                    final LatLng ll = new LatLng(lat, lon);

                    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(ll, 12);
                    map.animateCamera(cu);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button addFountainButton = (Button) findViewById(R.id.addFountain);

        addFountainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, NewFountainActivity.class);
                startActivity(intent);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
