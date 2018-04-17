package com.example.eslamjava.tripplanner;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

import dto.TripData;
import sqliteDB.DB_Adapter;


public class CreatNewTripActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = CreatNewTripActivity.class.getName();
    public static final int PERMISSION_REQUEST_FINE_LOCATION = 111;
    public static final int PLACE_PICKER_REQUEST1 = 1;
    public static final int PLACE_PICKER_REQUEST2 = 2;
    static final int TIME_DIALOG_ID = 999;
    static final int DATE_DIALOG_ID = 1000;

    public static final String ON_WAY_TRIP = "oneWay";
    public static final String ROUND_TRIP = "roundTrip";
    public static final String UPCOMING = "upcoming";
    public static final String FINISHED = "finished";
    public static final String CANCELED = "canceled";

    private boolean mIsEnabled;
    private GoogleApiClient nClient;


    Button saveBtn;
    EditText tripNameEdt;
    EditText startPointEdt;
    EditText destinationEdt;
    EditText dateEdt;
    EditText timeEdt;
    EditText notesEdt;
    Switch switch1;
    Button deleteBtn;
    Button editBtn;


    //===================
    String tripName = "";
    String startPoint = "";
    String destination = "";
    String notes = "";
    String switchValue = "";
    int hour;
    int minute;
    int _day;
    int _month;
    int _year;
    double startLongtiude;
    double startLatitude;
    double endLongtiude;
    double endLatitude;
    //===================

    DB_Adapter db_adapter;
    Intent comingIntent;
    //==========================

    AlarmManager alarm_manager;


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_new_trip_activity);
        db_adapter = new DB_Adapter(this);
        switchValue = ON_WAY_TRIP;

        saveBtn = (Button) findViewById(R.id.saveBtn);
        tripNameEdt = (EditText) findViewById(R.id.tripNameEdt);
        startPointEdt = (EditText) findViewById(R.id.startPointEdt);
        destinationEdt = (EditText) findViewById(R.id.destinationEdt);
        dateEdt = (EditText) findViewById(R.id.dateEdt);
        timeEdt = (EditText) findViewById(R.id.timeEdt);
        notesEdt = (EditText) findViewById(R.id.notesEdt);
        switch1 = (Switch) findViewById(R.id.switch1);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        editBtn = (Button) findViewById(R.id.editBtn);


        nClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        comingIntent = getIntent();

        if (comingIntent.getAction().equals(Navigation_info_Activity.CREAT_TRIP_INTENT_ACTION)) {

            editBtn.setClickable(false);
            editBtn.setVisibility(View.INVISIBLE);

            deleteBtn.setClickable(false);
            deleteBtn.setVisibility(View.INVISIBLE);

        } else if (comingIntent.getAction().equals(Navigation_info_Activity.DETAILES_TRIP_INTENT_ACTION)) {

            saveBtn.setClickable(false);
            saveBtn.setVisibility(View.INVISIBLE);

            TripData trData = db_adapter.getCertaineTripData(comingIntent.getIntExtra("tripId", 0));

            tripNameEdt.setText(trData.getTripName());
            tripNameEdt.setClickable(false);
            tripNameEdt.setEnabled(false);

            startPointEdt.setText(trData.getStartPointAddress());
            startPointEdt.setClickable(false);
            startPointEdt.setEnabled(false);

            destinationEdt.setText(trData.getDestinationAddress());
            destinationEdt.setClickable(false);
            destinationEdt.setEnabled(false);

            notesEdt.setText(trData.getNotes());
            notesEdt.setClickable(false);
            notesEdt.setEnabled(false);

            _day=trData.getDay();
            _month=trData.getMonthe();
            _year=trData.getYear();

            dateEdt.setText(" " + trData.getDay() + "-" + (trData.getMonthe()+1)  + "-" + trData.getYear());
            dateEdt.setClickable(false);
            dateEdt.setEnabled(false);

            hour=trData.getHour();
            minute= trData.getMinuets();

            timeEdt.setText(" " + trData.getHour() + ":" + trData.getMinuets());
            timeEdt.setClickable(false);
            timeEdt.setEnabled(false);

            switch1.setClickable(false);
            switch1.setEnabled(false);

            if (trData.getRoundTripOrOneWay().equals(CreatNewTripActivity.ROUND_TRIP)) {

                switch1.setChecked(true);
            }


        }


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreatNewTripActivity.this);
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

                        int tripid = comingIntent.getIntExtra("tripId", 0);
                        db_adapter.deleteTrip(tripid);
                        Intent intent = new Intent(getBaseContext(), AlarmDialogActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),comingIntent.getIntExtra("tripId", 0), intent, 0);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                        finish();

                    }
                });
                alertDialog.show();


            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CreatNewTripActivity.this,"trip id:"+comingIntent.getIntExtra("tripId", 0),Toast.LENGTH_LONG).show();
                Log.e("edit", "onClick: "+comingIntent.getIntExtra("tripId", 0) );
                Intent intent = new Intent(getBaseContext(), AlarmDialogActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),comingIntent.getIntExtra("tripId", 0), intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                saveBtn.setClickable(true);
                saveBtn.setVisibility(View.VISIBLE);

                deleteBtn.setClickable(false);
                deleteBtn.setVisibility(View.INVISIBLE);

                editBtn.setClickable(false);
                editBtn.setVisibility(View.INVISIBLE);


                tripNameEdt.setClickable(true);
                tripNameEdt.setEnabled(true);


                startPointEdt.setClickable(true);
                startPointEdt.setEnabled(true);

                destinationEdt.setClickable(true);
                destinationEdt.setEnabled(true);

                notesEdt.setClickable(true);
                notesEdt.setEnabled(true);

                dateEdt.setClickable(true);
                dateEdt.setEnabled(true);

                timeEdt.setClickable(true);
                timeEdt.setEnabled(true);

                switch1.setClickable(true);
                switch1.setEnabled(true);

            }
        });

        startPointEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(CreatNewTripActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(CreatNewTripActivity.this, "You need to enable location first", Toast.LENGTH_SHORT).show();
                    showSettingsAlerts();
                    return;
                }
                if (ActivityCompat.checkSelfPermission(CreatNewTripActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(CreatNewTripActivity.this, "You need to enable Network first", Toast.LENGTH_SHORT).show();
                    showSettingsAlertsForNetwork();
                    return;
                }


                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    startActivityForResult(builder.build(CreatNewTripActivity.this), PLACE_PICKER_REQUEST1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        destinationEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(CreatNewTripActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(CreatNewTripActivity.this, "You need to enable location first", Toast.LENGTH_SHORT).show();
                    showSettingsAlerts();
                    return;
                }
                if (ActivityCompat.checkSelfPermission(CreatNewTripActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(CreatNewTripActivity.this, "You need to enable Network first", Toast.LENGTH_SHORT).show();
                    showSettingsAlertsForNetwork();
                    return;
                }


                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    startActivityForResult(builder.build(CreatNewTripActivity.this), PLACE_PICKER_REQUEST2);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        if (comingIntent.getAction().equals(Navigation_info_Activity.CREAT_TRIP_INTENT_ACTION)) {

            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            _day = c.get(Calendar.DAY_OF_MONTH);
            _month = c.get(Calendar.MONTH);
            _year = c.get(Calendar.YEAR);

            // set current time into textview
            timeEdt.setText(
                    new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));

            dateEdt.setText(new StringBuilder().append(_day)
                    .append("-").append(_month).append("-").append(_year));
        }

        timeEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }
        });

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switch1.isChecked()) {
                    switchValue = ROUND_TRIP;
                } else {

                    switchValue = ON_WAY_TRIP;
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comingIntent.getAction().equals(Navigation_info_Activity.DETAILES_TRIP_INTENT_ACTION)) {

                    db_adapter.deleteTrip(comingIntent.getIntExtra("tripId", 0));

                }

                TripData trData = new TripData();

                tripName = tripNameEdt.getText().toString();
                notes = notesEdt.getText().toString();

            if (tripName.length() < 1) {

                  tripNameEdt.setError("Please write a name for your trip");

              } else if (startPoint.length() < 1) {

                   startPointEdt.setError("Please choose your start point");
               } else if (destination.length() < 1) {

                  destinationEdt.setError("Please choose your destination");
            } else {



                trData.setTripName(tripName);
                trData.setStartPointAddress(startPoint);
                trData.setDestinationAddress(destination);
                trData.setFinishedOrUpcoming(UPCOMING);
                trData.setRoundTripOrOneWay(switchValue);
                trData.setStartPointLatitude(startLatitude);
                trData.setStartPointLongtude(startLongtiude);
                trData.setDestinationLatitude(endLatitude);
                trData.setDestinationLongtude(endLongtiude);
                trData.setNotes(notes);
                trData.setYear(_year);
                trData.setMonthe(_month);
                trData.setDay(_day);
                trData.setHour(hour);
                trData.setMinuets(minute);

                int trId = (int) db_adapter.addTripData(trData);

                //=========================================================
                alarm_manager=(AlarmManager) getSystemService(ALARM_SERVICE);

                Calendar alarmCalendar=Calendar.getInstance();
                Calendar calSet = (Calendar) alarmCalendar.clone();

                calSet.set(Calendar.DAY_OF_MONTH,_day);
                calSet.set(Calendar.MONTH,_month);
                calSet.set(Calendar.YEAR,_year);
                calSet.set(Calendar.HOUR,hour);
                calSet.set(Calendar.MINUTE,minute);
                if (calSet.compareTo(alarmCalendar) <= 0) {
                    calSet.add(Calendar.DATE, 1);
                }
                Intent intent = new Intent(getBaseContext(), AlarmDialogActivity.class);
                intent.putExtra("tripId",trId);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),trId, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
                //===================================================




                System.out.println("Trip Nmae: " + tripName);
                System.out.println("Start point: " + startPoint);
                System.out.println("Destination: " + destination);
                System.out.println("Date: " + new StringBuilder().append(_day)
                        .append("-").append(_month).append("-").append(_year));
                System.out.println("Time: " + new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)));
                System.out.println("Notes: " + notes);
                System.out.println("Switch" + switchValue);

                finish();


                }


            }
        });


    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                if (place.equals(null)) {

                    Toast.makeText(this, "place is null", Toast.LENGTH_LONG).show();
                }

                String toastMsg = String.format("Place that you need is : ", place.getAddress().toString());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                startPointEdt.setText(place.getAddress().toString());
                startPoint = place.getAddress().toString();
                startLatitude = place.getLatLng().latitude;
                startLongtiude = place.getLatLng().longitude;
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                if (place.equals(null)) {

                    Toast.makeText(this, "place is null", Toast.LENGTH_LONG).show();
                }

                String toastMsg = String.format("Place that you need is : ", place.getAddress().toString());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                destination = place.getAddress().toString();
                destinationEdt.setText(destination);

                endLatitude = place.getLatLng().latitude;
                endLongtiude = place.getLatLng().longitude;
            }
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        System.out.println("Api client connection successfull ! ");
        Toast.makeText(CreatNewTripActivity.this, "Api client connection successfull !", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onConnectionSuspended(int i) {

        System.out.println("Api client connection Suspended ! ");
        Toast.makeText(CreatNewTripActivity.this, "Api client connection Suspended !", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        System.out.println("Api client connection Failed ! ");
        Toast.makeText(CreatNewTripActivity.this, "Api client connection Failed !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == TIME_DIALOG_ID) {
            return new TimePickerDialog(this, timePickerListener, hour, minute, false);
        } else if (id == DATE_DIALOG_ID) {

            return new DatePickerDialog(this, datePickerListener, _year, _month, _day);
        }


        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into textview
                    timeEdt.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));

                    // set current time into timepicker


                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {


                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    _day = dayOfMonth;
                    _month = month;
                    _year = year;


                    dateEdt.setText(new StringBuilder().append(_day)
                            .append("-").append(_month).append("-").append(_year));

                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void showSettingsAlerts() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled . Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        alertDialog.show();
    }

    public void showSettingsAlertsForNetwork() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Network is settings");
        alertDialog.setMessage("Network is not enabled . Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                startActivity(i);

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        alertDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
