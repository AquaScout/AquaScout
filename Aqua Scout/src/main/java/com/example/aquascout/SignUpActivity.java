package com.example.aquascout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Pattern;

import static com.example.aquascout.R.*;


public class SignUpActivity extends ActionBarActivity {

    EditText userName;
    EditText email;
    EditText password;
    EditText repeatPassword;
    CharSequence emailCheck = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_sign_up);

        Button signUpButton = (Button) findViewById(id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                userName = (EditText) findViewById(id.usernameField);
                email = (EditText) findViewById(id.emailField);
                password = (EditText) findViewById(id.passwordField);
                repeatPassword = (EditText) findViewById(id.repeatPasswordField);
                emailCheck = email.getText().toString();


                if(userName.getText().toString().length() < 3)
                {
                    Toast.makeText(SignUpActivity.this, "Username must be 3 or more in length", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailCheck).matches())
                {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().length() < 6)
                {
                    Toast.makeText(SignUpActivity.this, "Password must be 6 or more in length", Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(repeatPassword.getText().toString()))
                {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ParseUser user = new ParseUser();
                    user.setUsername(userName.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setEmail(email.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e != null) {
                                 // Show the error message
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                // Start an intent for the dispatch activity
                                Intent intent = new Intent(SignUpActivity.this, CheckLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                    //Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    //Toast.makeText(SignUpActivity.this, "Account created. Please sign in", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                }

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
