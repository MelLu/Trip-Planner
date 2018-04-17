package com.example.eslamjava.tripplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sqliteDB.DB_Adapter;

public class CancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_cancel);

        DB_Adapter adabter=new DB_Adapter(this);
        Intent i=getIntent();
        int tripId=i.getIntExtra("id",0);

        adabter.updateTripStatus(tripId, CreatNewTripActivity.CANCELED);
        finish();
    }
}
