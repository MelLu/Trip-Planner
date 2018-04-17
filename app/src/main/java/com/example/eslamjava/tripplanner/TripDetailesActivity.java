package com.example.eslamjava.tripplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import dto.TripData;
import sqliteDB.DB_Adapter;

public class TripDetailesActivity extends AppCompatActivity {

    EditText tripNameDetaileEdt;
    EditText startPointDetailEdt;
    EditText destinationDetailEdt;
    EditText dateDetailEdt;
    EditText timeDetailEdt;
    EditText notesDetailEdt;
    Switch switch1Detail;
    Button deleteDetaileBtn;

    DB_Adapter adapter;
    int id;
    Intent i;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detailes);

        tripNameDetaileEdt= (EditText) findViewById(R.id.tripNameDetaileEdt);
        startPointDetailEdt= (EditText) findViewById(R.id.startPointDetailEdt);
        destinationDetailEdt= (EditText) findViewById(R.id.destinationDetailEdt);
        timeDetailEdt= (EditText) findViewById(R.id.timeDetailEdt);
        dateDetailEdt= (EditText) findViewById(R.id.dateDetailEdt);
        notesDetailEdt= (EditText) findViewById(R.id.notesDetailEdt);
        deleteDetaileBtn= (Button) findViewById(R.id.deleteDetaileBtn);
        switch1Detail= (Switch) findViewById(R.id.switch1Detail);

        adapter=new DB_Adapter(this);

        i=getIntent();

        id=i.getIntExtra("tripId",0) ;
        TripData trData=adapter.getCertaineTripData(id);

        tripNameDetaileEdt.setText(trData.getTripName());
        startPointDetailEdt.setText(trData.getStartPointAddress());
        destinationDetailEdt.setText(trData.getDestinationAddress());
        dateDetailEdt.setText(trData.getHour()+":"+trData.getMinuets());
        timeDetailEdt.setText(trData.getDay()+"-"+trData.getMonthe()+"-"+trData.getYear());
        notesDetailEdt.setText(trData.getNotes());

        if(trData.getRoundTripOrOneWay().equals(CreatNewTripActivity.ROUND_TRIP)){

            switch1Detail.setChecked(true);
        }

        deleteDetaileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TripDetailesActivity.this);
                alertDialog.setTitle("Delet Trip");
                alertDialog.setMessage("Are you sure that you want to delete this trip ?");
                alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        adapter.deleteTrip(id);
                        finish();

                    }
                });
                alertDialog.show();


            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
