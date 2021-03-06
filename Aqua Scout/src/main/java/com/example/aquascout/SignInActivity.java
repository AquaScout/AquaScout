package com.example.aquascout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignInActivity extends Activity {

    EditText userName;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                userName = (EditText) findViewById(R.id.usernameField);
                password = (EditText) findViewById(R.id.passwordField);

                if(userName.getText().toString().length() < 3)
                {
                    Toast.makeText(SignInActivity.this, "Username and/or password does not match", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().length() < 6)
                {
                    Toast.makeText(SignInActivity.this, "Username and/or password does not match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback()
                    {
                        @Override
                        public void done(ParseUser user, ParseException e)
                        {
                            if (e != null)
                            {
                                // Show the error message
                                Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                // Start an intent for the dispatch activity
                                Intent intent = new Intent(SignInActivity.this, CheckLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                    //Intent intent = new Intent(SignInActivity.this, ResultsActivity.class);
                    //startActivity(intent);
                }
            }
        });

        TextView forgotPasswordButton = (TextView) findViewById(R.id.forgotPasswordLink);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, NewFountainActivity.class);
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
