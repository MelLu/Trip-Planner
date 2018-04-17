package sqliteDB;

/**
 * Created by IROCK on 17/01/2018.
 */

public class DB_Data {

    //DB Name&Version

    public static final String DB_NAME="trip_planner";
    public static final int DB_VERSION=1;

    //TableName

    public static final String TABLE_NAME="trip_data";

    //ColumnsName
    public static final String ID="_id";
    public static final String TRIP_NAME="trip_name";
    public static final String START_POINT_ADDRESS="start_point_address";
    public static final String DESTINATION_ADRESS="destination_address";
    public static final String START_POINT_LATITUDE="start_point_latitude";
    public static final String START_POINT_LONGTUDE="start_point_longtude";
    public static final String DESTINATION_LATITUDE="destination_latitude";
    public static final String DESTINATION_LONGTUDE="destination_longtude";
    public static final String HOUR="hour";
    public static final String MINUETS="minuets";
    public static final String YEAR="year";
    public static final String MONTH="month";
    public static final String DAY="day";
    public static final String NOTES="notes";
    public static final String ROUND_OR_ONE="round_or_one";
    public static final String FINISHED_OR_UPCOMING="finished_or_upcoming";
    public static final String USER_TABLE_NAME="User_data";
    public static final String ID_COLUMN="Id";
    public static final String  Name_COLUMN="name";
    public static final String Email_COLUMN="email";
    public static final String password_COLUMN="password";

}
