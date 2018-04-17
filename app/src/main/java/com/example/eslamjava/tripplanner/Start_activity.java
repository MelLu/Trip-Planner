package com.example.eslamjava.tripplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Start_activity extends AppCompatActivity {
    Session_mangment session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);
        session = new Session_mangment(getApplicationContext());
        if(session.isLoggedIn())
        {
            Intent i=new Intent(Start_activity.this, Navigation_info_Activity.class);
            startActivity(i);
            finish();
        }
        else {
            session.checkLogin();
            finish();
        }




    }
}
