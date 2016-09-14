package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;

/**
 * Created by dfrancisco.hernandez on 3/09/16.
 */
import android.provider.BaseColumns;

public class StatusContract {

    public static final String DB_NAME = "lab2Activities.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USER= "usuario";
    public static final String TABLE_PLACE= "lugar";
    public static final String TABLE_LOGIN="logeado";

    public class Column_Login {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "nickname";
    }
    public class Column_User {
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "nickname";
        public static final String MAIL = "mail";
        public static final String PASS = "password";
        public static final String PHONE = "phone";
        public static final String PICTURE = "picture";
    }
    public class Column_Place {
        public static final String ID = BaseColumns._ID;
        public static final String NPLACE = "country";
        public static final String DESCRIPTION = "description";
        public static final String PLACE = "place";
        public static final String PUNTS = "Score";
        public static final String PICTURE = "picture";
    }
}
