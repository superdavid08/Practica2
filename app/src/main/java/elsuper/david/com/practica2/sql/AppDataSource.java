package elsuper.david.com.practica2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import elsuper.david.com.practica2.model.ModelApp;

/**
 * Created by Andrés David García Gómez
 */
public class AppDataSource {

    private final SQLiteDatabase db;

    public AppDataSource(Context context) {
        MySqliteHelper helper = new MySqliteHelper(context);
        db = helper.getWritableDatabase();
    }

    public long saveApp(ModelApp modelApp){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_APP_DEVELOPER, modelApp.appDeveloperName);
        contentValues.put(MySqliteHelper.COLUMN_APP_NAME, modelApp.appName);
        contentValues.put(MySqliteHelper.COLUMN_APP_RESOURCE, modelApp.appResourceId);
        contentValues.put(MySqliteHelper.COLUMN_APP_UPDATED, modelApp.appUpdated);
        contentValues.put(MySqliteHelper.COLUMN_APP_DETAIL, modelApp.appDetail);

        long result = db.insert(MySqliteHelper.TABLENAME_APP, null, contentValues);
        return result;
    }

    public List<ModelApp> getAllApps(){
        List<ModelApp> modelAppList = new ArrayList<>();
        Cursor cursor = db.query(MySqliteHelper.TABLENAME_APP,null,null,null,null,null,null);

        while(cursor.moveToNext()){
            ModelApp modelApp = new ModelApp();
            modelApp.id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_ID));
            modelApp.appDeveloperName = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DEVELOPER));
            modelApp.appName = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_NAME));
            modelApp.appResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_RESOURCE));
            modelApp.appUpdated = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_UPDATED));
            modelApp.appDetail = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DETAIL));
            modelAppList.add(modelApp);
        }
        return  modelAppList;
    }

    public void deleteApp(ModelApp modelApp){
        db.delete(MySqliteHelper.TABLENAME_APP, MySqliteHelper.COLUMN_APP_ID + "=?",
                new String[]{String.valueOf(modelApp.id)});
    }

    public ModelApp getApp(int id){
        Cursor cursor = db.query(MySqliteHelper.TABLENAME_APP, null, MySqliteHelper.COLUMN_APP_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null);

        if(cursor.moveToFirst()){
            ModelApp modelApp = new ModelApp();
            modelApp.id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_ID));
            modelApp.appDeveloperName = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DEVELOPER));
            modelApp.appName = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_NAME));
            modelApp.appResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_RESOURCE));
            modelApp.appUpdated = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_UPDATED));
            modelApp.appDetail = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DETAIL));

            return modelApp;
        }

        return null;
    }

    public boolean updateApp(ModelApp modelApp){

        boolean success;

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_APP_DEVELOPER, modelApp.appDeveloperName);
        contentValues.put(MySqliteHelper.COLUMN_APP_NAME, modelApp.appName);
        contentValues.put(MySqliteHelper.COLUMN_APP_RESOURCE, modelApp.appResourceId);
        contentValues.put(MySqliteHelper.COLUMN_APP_UPDATED, modelApp.appUpdated);
        contentValues.put(MySqliteHelper.COLUMN_APP_DETAIL, modelApp.appDetail);

        success = db.update(MySqliteHelper.TABLENAME_APP, contentValues, MySqliteHelper.COLUMN_APP_ID + "=?",
                new String[]{String.valueOf(modelApp.id)}) == 1 ? true : false;

        return success;
    }
}