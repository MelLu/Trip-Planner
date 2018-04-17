package com.example.eslamjava.tripplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import dto.TripData;
import listview_adapters.UpcomingListViewAdapter;
import sqliteDB.DB_Adapter;
import viewholders.ListViewHolder;

public class Navigation_info_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    DB_Adapter db_adapter;
    List tripDataList;
    TextView headerNameTxtV;
    TextView headerEmailTxtV;
    public static final String CREAT_TRIP_INTENT_ACTION = "creatTripIntent";
    public static final String DETAILES_TRIP_INTENT_ACTION ="detailesTripIntent";
    Session_mangment session_mangment;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_info_);

        session_mangment=new Session_mangment(getApplicationContext());
        HashMap<String, String> userDetails=session_mangment.getUserDetails();


        headerEmailTxtV= (TextView) findViewById(R.id.headerEmailTxtV);
        headerNameTxtV= (TextView) findViewById(R.id.headerNameTxtV);

       //headerEmailTxtV.setText(  userDetails.get(Session_mangment.KEY_EMAIL));
        //headerNameTxtV.setText(userDetails.get(Session_mangment.KEY_NAME));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.upcomingListView);

        db_adapter = new DB_Adapter(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            /**
             * Callback method to be invoked when an item in this AdapterView has
             * been clicked.
             * <p>
             * Implementers can call getItemAtPosition(position) if they need
             * to access the data associated with the selected item.
             *
             * @param parent   The AdapterView where the click happened.
             * @param view     The view within the AdapterView that was clicked (this
             *                 will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id       The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListViewHolder holder = (ListViewHolder) view.getTag();
                Intent i = new Intent(Navigation_info_Activity.this, CreatNewTripActivity.class);
                i.setAction(DETAILES_TRIP_INTENT_ACTION);
                i.putExtra("tripId", holder.getTripId());
                Log.i("navId", "onItemClick: "+holder.getTripId() );
                startActivity(i);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent in = new Intent(Navigation_info_Activity.this, CreatNewTripActivity.class);
                in.setAction(CREAT_TRIP_INTENT_ACTION);
                startActivity(in);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_info_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Arbic) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,getResources().getDisplayMetrics());

        }else if(id == R.id.action_English){
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,getResources().getDisplayMetrics());

        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_finished) {

            Intent i=new Intent(Navigation_info_Activity.this,FinishedTripsListviewActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_canceled) {
            Intent i=new Intent(Navigation_info_Activity.this,CanceledTripsListViewActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {

            new AlertDialog.Builder(this)
                   .setTitle("Confirmation")
                    .setMessage("Do you really want to Close?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                       public void onClick(DialogInterface dialog, int whichButton) {


                           session_mangment.logoutUser();
                           finish();


                       }})
                   .setNegativeButton(android.R.string.no, null).show();




        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        tripDataList = db_adapter.getSelectionTripData(CreatNewTripActivity.UPCOMING);

        UpcomingListViewAdapter listAdapter = new UpcomingListViewAdapter(this, R.layout.custom_item_list_view, R.id.tripNametxtv, tripDataList);
        list.setAdapter(listAdapter);
        ;

    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}
