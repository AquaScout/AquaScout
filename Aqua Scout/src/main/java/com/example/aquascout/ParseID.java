package com.example.aquascout;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class ParseID extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                "XiqXJOSnSutw7nJ9GNmdOESzQ6a5lM5rl37buR2v",
                "iYoUtDsGBEskF2AzP1HvPJZDV1nOqGsHI2sNt9w9");
        // You need to register the subclass if you want to define
        // your own data class like Professor
        ParseObject.registerSubclass(ReviewParseObject.class);

    }

}
