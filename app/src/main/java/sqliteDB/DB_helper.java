package sqliteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IROCK on 17/01/2018.
 */

public class DB_helper extends SQLiteOpenHelper {

    public DB_helper(Context context) {
        super(context, DB_Data.DB_NAME, null, DB_Data.DB_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table tabel_name(col1 int primerykey autoincrement,col2 txt)


        String sql = "CREATE TABLE " + DB_Data.TABLE_NAME + "(" + DB_Data.ID + " INTEGER primary key autoincrement,"
                +
                DB_Data.TRIP_NAME + " TEXT ,"
                +
                DB_Data.START_POINT_ADDRESS + " TEXT,"
                +
                DB_Data.DESTINATION_ADRESS + " TEXT,"
                +
                DB_Data.NOTES + " TEXT,"
                +
                DB_Data.ROUND_OR_ONE + " TEXT,"
                +
                DB_Data.FINISHED_OR_UPCOMING + " TEXT,"
                +
                DB_Data.START_POINT_LATITUDE + " double,"
                +
                DB_Data.START_POINT_LONGTUDE + " double,"
                +
                DB_Data.DESTINATION_LATITUDE + " double,"
                +
                DB_Data.DESTINATION_LONGTUDE + " double,"
                +
                DB_Data.HOUR+" int,"
                +
                DB_Data.MINUETS+" int,"
                +
                DB_Data.YEAR+" int,"
                +
                DB_Data.MONTH+" int,"
                +
                DB_Data.DAY+" int"+ ")";

        String CREATE_USER_TABLE="CREATE TABLE "+DB_Data.USER_TABLE_NAME+" ("+ DB_Data.Name_COLUMN + " TEXT," + DB_Data.Email_COLUMN +" TEXT  PRIMARY KEY, "+ DB_Data.password_COLUMN + " Text )";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        // this method is executed if the version of database is changed

        // drop table table_name

        String sql = "drop table" + DB_Data.TABLE_NAME;
        String sql2= "drop table" + DB_Data.USER_TABLE_NAME;
        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);// to recreate database after drop

    }
}
