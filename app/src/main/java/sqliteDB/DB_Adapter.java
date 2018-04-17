package sqliteDB;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;


import java.util.ArrayList;

import dto.TripData;
import dto.UserData;

/**
 * Created by IROCK on 17/01/2018.
 */

public class DB_Adapter {

    SQLiteDatabase db;
    DB_helper helper;
    UserData userdata;
    private static final String[] allColumns={DB_Data.Name_COLUMN,DB_Data.Email_COLUMN,DB_Data.password_COLUMN};



    public DB_Adapter(Context activity) {

        helper = new DB_helper(activity);

    }


    public void open() {
        Log.i("info", "databaseopen");
        db = helper.getWritableDatabase();


    }

    public void close() {
        Log.i("info", "databasclose");
        helper.close();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<TripData> getTripData() {

        open();
        String[] columns = {DB_Data.ID, DB_Data.TRIP_NAME, DB_Data.START_POINT_ADDRESS, DB_Data.DESTINATION_ADRESS,
                DB_Data.START_POINT_LATITUDE, DB_Data.START_POINT_LONGTUDE, DB_Data.DESTINATION_LATITUDE,
                DB_Data.DESTINATION_LONGTUDE, DB_Data.ROUND_OR_ONE, DB_Data.FINISHED_OR_UPCOMING,
                DB_Data.YEAR, DB_Data.MONTH, DB_Data.DAY, DB_Data.HOUR, DB_Data.MINUETS,
                DB_Data.NOTES};

        Cursor c = db.query(true, DB_Data.TABLE_NAME, columns, null, null, null, null, null, null, null);
        ArrayList<TripData> data = new ArrayList<>();
        while (c.moveToNext()) {

            int columnIndex = 0;
            TripData trData = new TripData();

            columnIndex = c.getColumnIndex(DB_Data.ID);
            trData.setId(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.TRIP_NAME);
            trData.setTripName(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_ADDRESS);
            trData.setStartPointAddress(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_ADRESS);
            trData.setDestinationAddress(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.NOTES);
            trData.setNotes(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_LATITUDE);
            trData.setStartPointLatitude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_LONGTUDE);
            trData.setStartPointLongtude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_LATITUDE);
            trData.setDestinationLatitude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_LONGTUDE);
            trData.setDestinationLongtude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.FINISHED_OR_UPCOMING);
            trData.setFinishedOrUpcoming(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.ROUND_OR_ONE);
            trData.setRoundTripOrOneWay(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.YEAR);
            trData.setYear(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.MONTH);
            trData.setMonthe(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DAY);
            trData.setDay(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.HOUR);
            trData.setHour(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.MINUETS);
            trData.setMinuets(c.getInt(columnIndex));

            data.add(trData);


        }
        close();

        return data;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<TripData> getSelectionTripData(String selectetionValue ) {

        open();
        String[] selectionArguments={selectetionValue};
        String[] columns = {DB_Data.ID, DB_Data.TRIP_NAME, DB_Data.START_POINT_ADDRESS, DB_Data.DESTINATION_ADRESS,
                DB_Data.START_POINT_LATITUDE, DB_Data.START_POINT_LONGTUDE, DB_Data.DESTINATION_LATITUDE,
                DB_Data.DESTINATION_LONGTUDE, DB_Data.ROUND_OR_ONE, DB_Data.FINISHED_OR_UPCOMING,
                DB_Data.YEAR, DB_Data.MONTH, DB_Data.DAY, DB_Data.HOUR, DB_Data.MINUETS,
                DB_Data.NOTES};

        Cursor c = db.query(true, DB_Data.TABLE_NAME, columns, DB_Data.FINISHED_OR_UPCOMING+"='"+selectetionValue+"'" , null, null, null, null, null, null);
        ArrayList<TripData> data = new ArrayList<>();
        while (c.moveToNext()) {

            int columnIndex = 0;
            TripData trData = new TripData();

            columnIndex = c.getColumnIndex(DB_Data.ID);
            trData.setId(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.TRIP_NAME);
            trData.setTripName(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_ADDRESS);
            trData.setStartPointAddress(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_ADRESS);
            trData.setDestinationAddress(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.NOTES);
            trData.setNotes(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_LATITUDE);
            trData.setStartPointLatitude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_LONGTUDE);
            trData.setStartPointLongtude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_LATITUDE);
            trData.setDestinationLatitude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_LONGTUDE);
            trData.setDestinationLongtude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.FINISHED_OR_UPCOMING);
            trData.setFinishedOrUpcoming(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.ROUND_OR_ONE);
            trData.setRoundTripOrOneWay(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.YEAR);
            trData.setYear(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.MONTH);
            trData.setMonthe(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DAY);
            trData.setDay(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.HOUR);
            trData.setHour(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.MINUETS);
            trData.setMinuets(c.getInt(columnIndex));

            data.add(trData);


        }
        close();

        return data;

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TripData getCertaineTripData(long tripId ) {

        open();
        String[] selectionArguments={""+tripId};
        String[] columns = {DB_Data.ID, DB_Data.TRIP_NAME, DB_Data.START_POINT_ADDRESS, DB_Data.DESTINATION_ADRESS,
                DB_Data.START_POINT_LATITUDE, DB_Data.START_POINT_LONGTUDE, DB_Data.DESTINATION_LATITUDE,
                DB_Data.DESTINATION_LONGTUDE, DB_Data.ROUND_OR_ONE, DB_Data.FINISHED_OR_UPCOMING,
                DB_Data.YEAR, DB_Data.MONTH, DB_Data.DAY, DB_Data.HOUR, DB_Data.MINUETS,
                DB_Data.NOTES};

        Cursor c = db.query(true, DB_Data.TABLE_NAME, columns, DB_Data.ID + "=" + tripId, null, null, null, null, null, null);
        c.moveToFirst();

            int columnIndex = 0;
            TripData trData = new TripData();

            columnIndex = c.getColumnIndex(DB_Data.ID);
            trData.setId(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.TRIP_NAME);
            trData.setTripName(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_ADDRESS);
            trData.setStartPointAddress(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_ADRESS);
            trData.setDestinationAddress(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.NOTES);
            trData.setNotes(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_LATITUDE);
            trData.setStartPointLatitude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.START_POINT_LONGTUDE);
            trData.setStartPointLongtude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_LATITUDE);
            trData.setDestinationLatitude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DESTINATION_LONGTUDE);
            trData.setDestinationLongtude(c.getDouble(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.FINISHED_OR_UPCOMING);
            trData.setFinishedOrUpcoming(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.ROUND_OR_ONE);
            trData.setRoundTripOrOneWay(c.getString(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.YEAR);
            trData.setYear(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.MONTH);
            trData.setMonthe(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.DAY);
            trData.setDay(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.HOUR);
            trData.setHour(c.getInt(columnIndex));

            columnIndex = c.getColumnIndex(DB_Data.MINUETS);
            trData.setMinuets(c.getInt(columnIndex));


        close();

        return trData;

    }


    public long addTripData(TripData tripData) {



        open();

        ContentValues values = new ContentValues();
        values.put(DB_Data.TRIP_NAME, tripData.getTripName());
        values.put(DB_Data.START_POINT_ADDRESS, tripData.getStartPointAddress());
        values.put(DB_Data.DESTINATION_ADRESS, tripData.getDestinationAddress());
        values.put(DB_Data.START_POINT_LATITUDE, tripData.getStartPointLatitude());
        values.put(DB_Data.START_POINT_LONGTUDE, tripData.getStartPointLongtude());
        values.put(DB_Data.DESTINATION_LATITUDE, tripData.getDestinationLatitude());
        values.put(DB_Data.DESTINATION_LONGTUDE, tripData.getDestinationLongtude());
        values.put(DB_Data.FINISHED_OR_UPCOMING, tripData.getFinishedOrUpcoming());
        values.put(DB_Data.ROUND_OR_ONE, tripData.getRoundTripOrOneWay());
        values.put(DB_Data.YEAR, tripData.getYear());
        values.put(DB_Data.MONTH, tripData.getMonthe());
        values.put(DB_Data.DAY, tripData.getDay());
        values.put(DB_Data.HOUR, tripData.getHour());
        values.put(DB_Data.MINUETS, tripData.getMinuets());
        values.put(DB_Data.NOTES, tripData.getNotes());

        long row = db.insert(DB_Data.TABLE_NAME, null, values);
        Log.i("raw number", " " + row);
        close();
        return row;


    }

    public long deleteTrip(long id){
        int deletedRow=0;
        open();


       deletedRow= db.delete(DB_Data.TABLE_NAME, DB_Data.ID + "=" + id, null);

        close();
        return deletedRow;

    }

    public boolean updateTripStatus(long rowId, String statusValue)
    {
        boolean updated=false;
        open();
        ContentValues args = new ContentValues();
        args.put(DB_Data.FINISHED_OR_UPCOMING, statusValue);

        updated= db.update(DB_Data.TABLE_NAME, args, DB_Data.ID + "=" + rowId, null) > 0;

        return updated;
    }

    public  int  insert_user(UserData userdata)
    {
        Log.i("info","datainsert");
        ContentValues values  = new ContentValues();
        values.put(DB_Data.Name_COLUMN,userdata.getName());
        values.put(DB_Data.Email_COLUMN,userdata.getEmail());
        values.put(DB_Data.password_COLUMN,userdata.getPassword());
        Log.i("info",userdata.getName()+"+"+userdata.getEmail()+"+"+userdata.getPassword());
        int id= (int) db.insert(DB_Data.USER_TABLE_NAME,null,values);
        Log.i("info"," "+id);

        return id;



    }


    public  UserData userdata_retrive()
    {
        Cursor cursor = db.query(DB_Data.USER_TABLE_NAME,  allColumns, null,null, null, null,null);
        if (cursor != null)
            cursor.moveToFirst();
        UserData userdata2 = new  UserData(cursor.getString(0),cursor.getString(1),cursor.getString(2));


        return  userdata2;


    }
    public Boolean searchUser(String userName)

    {
        String limit = "1";
        String[] selectionArgs = {userName};
        Cursor cursor = db.query(DB_Data.USER_TABLE_NAME, allColumns, DB_Data.Email_COLUMN + "=?", selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;


    }
}
