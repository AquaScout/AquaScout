package com.example.aquascout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.ParseUser;

public class CheckLoginActivity extends Activity {
    public CheckLoginActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            Intent intent = new Intent(CheckLoginActivity.this, ResultsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //startActivity(new Intent(this, ResultsActivity.class));
        } else {
            // Start and intent for the logged out activity
            Intent intent = new Intent(CheckLoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //startActivity(new Intent(this, MainActivity.class));
        }
    }
}