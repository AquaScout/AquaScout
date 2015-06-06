package com.example.aquascout;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReviewActivity extends ActionBarActivity {

    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Geocoder geocoder = new Geocoder(ReviewActivity.this, Locale.getDefault());


        TextView address = (TextView) findViewById(R.id.addressDisplay);
        TextView details = (TextView) findViewById(R.id.detailDisplay);
        TextView comments = (TextView) findViewById(R.id.commentsDisplay);

        details.setText(getIntent().getStringExtra("details"));
        comments.setText(getIntent().getStringExtra("comments"));


        try {
            addresses = geocoder.getFromLocation(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("lon", 0), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        address.setText(addresses.get(0).getAddressLine(0));

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