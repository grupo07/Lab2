package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;
/**
 * Created by dfrancisco.hernandez on 3/09/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper { //
    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlLogin = String.format(
          "create table %s(%s int primary key, %s text unique)",
                StatusContract.TABLE_LOGIN,
                StatusContract.Column_Login.ID,
                StatusContract.Column_Login.NAME);
        db.execSQL(sqlLogin);
        String sqlUser = String
                .format("create table %s (%s int primary key, %s text unique, %s text, %s text, %s text, %s blob)",
                        StatusContract.TABLE_USER,
                        StatusContract.Column_User.ID,
                        StatusContract.Column_User.NAME,
                        StatusContract.Column_User.MAIL,
                        StatusContract.Column_User.PASS,
                        StatusContract.Column_User.PHONE,
                        StatusContract.Column_User.PICTURE);
        db.execSQL(sqlUser);
        String sqlRace = String
                .format("create table %s (%s int primary key, %s text,%s text, %s text, %s text, %s blob)",
                        StatusContract.TABLE_PLACE,
                        StatusContract.Column_Place.ID,
                        StatusContract.Column_Place.NPLACE,
                        StatusContract.Column_Place.DESCRIPTION,
                        StatusContract.Column_Place.PLACE,
                        StatusContract.Column_Place.PUNTS,
                        StatusContract.Column_Place.PICTURE);
        db.execSQL(sqlRace);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + StatusContract.TABLE_USER);
        db.execSQL("drop table if exists " + StatusContract.TABLE_PLACE);
        db.execSQL("drop table if exists " + StatusContract.TABLE_LOGIN);
    }
}
