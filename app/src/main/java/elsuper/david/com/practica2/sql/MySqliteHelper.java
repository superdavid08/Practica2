package elsuper.david.com.practica2.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Andrés David García Gómez
 */
public class MySqliteHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "elsuperdavid_apps_management";
    private final static int DATABASE_VERSION = 1;

    //Tabla app_table
    public final static String TABLENAME_APP = "app_table";
    public final static String COLUMN_APP_ID = BaseColumns._ID;
    public final static String COLUMN_APP_DEVELOPER = "developer_name";
    public final static String COLUMN_APP_NAME = "name";
    public final static String COLUMN_APP_RESOURCE = "resource_id";
    public final static String COLUMN_APP_UPDATED = "updated";
    public final static String COLUMN_APP_DETAIL = "detail";
    private final static String CREATE_TABLE_APP = "create table " + TABLENAME_APP +
            "(" + COLUMN_APP_ID + " integer primary key autoincrement, " +
            COLUMN_APP_DEVELOPER + " text not null, " +
            COLUMN_APP_NAME + " text not null, " +
            COLUMN_APP_RESOURCE + " integer not null, " +
            COLUMN_APP_UPDATED + " integer not null, " +
            COLUMN_APP_DETAIL + " text not null)";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_APP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
