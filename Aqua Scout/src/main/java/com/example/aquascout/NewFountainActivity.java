package com.example.aquascout;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class NewFountainActivity extends Activity{

    private EditText address;
    private EditText details;
    private EditText comments;
    private ParseGeoPoint geoPoint;
    private int temp = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fountain);

        Spinner tempSpinner = (Spinner) findViewById(R.id.temperatureSpinner);

        AdapterView.OnItemSelectedListener onSpinner =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        temp = position;


                    }
                    @Override
                    public void onNothingSelected(
                            AdapterView<?>  parent) {
                    }
                };

        Button submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address = (EditText) findViewById(R.id.addressField);
                details = (EditText) findViewById(R.id.detailsField);
                comments = (EditText) findViewById(R.id.commentsField);

                ReviewParseObject review = new ReviewParseObject();
                review.setUser(ParseUser.getCurrentUser());
                //review.setAddress(address.getText().toString());
                Geocoder geoCoder = new Geocoder(NewFountainActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocationName(address.getText().toString(), 1);
                    Double lat = (double) (addresses.get(0).getLatitude());
                    Double lon = (double) (addresses.get(0).getLongitude());

                    geoPoint = new ParseGeoPoint(lat, lon);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                review.setAddress(geoPoint);
                review.setDetails(details.getText().toString());
                review.setComment(comments.getText().toString());
                review.setTemp(temp);
                review.saveInBackground();

                Intent intent = new Intent(NewFountainActivity.this, ResultsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
