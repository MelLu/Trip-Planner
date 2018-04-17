package com.example.eslamjava.tripplanner;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import listview_adapters.UpcomingListViewAdapter;
import sqliteDB.DB_Adapter;
import viewholders.ListViewHolder;

public class CanceledTripsListViewActivity extends AppCompatActivity {

    ListView list;
    DB_Adapter db_adapter;
    List tripDataList;
    public static final String CANCELED="canceled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceled_trips_list_view);

        list = (ListView) findViewById(R.id.canceledListView);

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
                Intent i = new Intent(CanceledTripsListViewActivity.this, TripDetailesActivity.class);
                i.setAction(CANCELED);
                i.putExtra("tripId", holder.getTripId());
                startActivity(i);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        tripDataList = db_adapter.getSelectionTripData(CreatNewTripActivity.CANCELED);

        UpcomingListViewAdapter listAdapter = new UpcomingListViewAdapter(this, R.layout.custom_item_list_view, R.id.tripNametxtv, tripDataList);
        list.setAdapter(listAdapter);
        ;

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
