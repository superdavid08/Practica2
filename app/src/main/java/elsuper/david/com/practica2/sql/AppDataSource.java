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

    //Sacamos a db del constructor para poder abrir y cerrar la conexión en cada método utilizado
    private SQLiteDatabase db = null;
    private MySqliteHelper helper;

    public AppDataSource(Context context) {
        helper = new MySqliteHelper(context);
        //db = helper.getWritableDatabase();
    }

    public long saveApp(ModelApp modelApp){
        //Abrimos la conexión
        db = helper.getWritableDatabase();

        //Preparamos el modelo a guardar
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_APP_DEVELOPER, modelApp.appDeveloperName);
        contentValues.put(MySqliteHelper.COLUMN_APP_NAME, modelApp.appName);
        contentValues.put(MySqliteHelper.COLUMN_APP_RESOURCE, modelApp.appResourceId);
        contentValues.put(MySqliteHelper.COLUMN_APP_UPDATED, modelApp.appUpdated);
        contentValues.put(MySqliteHelper.COLUMN_APP_DETAIL, modelApp.appDetail);

        //Insertamos el registro
        long result = db.insert(MySqliteHelper.TABLENAME_APP, null, contentValues);

        //Cerramos la conexión
        if(db.isOpen())
            db.close();

        return result;
    }

    public List<ModelApp> getAllApps(){
        //Abrimos la conexión
        db = helper.getWritableDatabase();

        //Consultamos toda la tabla app_table
        List<ModelApp> modelAppList = new ArrayList<>();
        Cursor cursor = db.query(MySqliteHelper.TABLENAME_APP,null,null,null,null,null,null);

        //Agregamos cada elemento del cursor a la lista
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

        //Cerramos la conexión
        if(db.isOpen())
            db.close();

        return  modelAppList;
    }

    public void deleteApp(ModelApp modelApp){
        //Abrimos la conexión
        db = helper.getWritableDatabase();

        //Eliminamos el registro
        db.delete(MySqliteHelper.TABLENAME_APP, MySqliteHelper.COLUMN_APP_ID + "=?",
                new String[]{String.valueOf(modelApp.id)});

        //Cerramos la conexión
        if(db.isOpen())
            db.close();
    }

    public ModelApp getApp(int id){
        //Abrimos la conexión
        db = helper.getWritableDatabase();

        //Objeto a devolver
        ModelApp modelApp = new ModelApp();

        //Consultamos
        Cursor cursor = db.query(MySqliteHelper.TABLENAME_APP, null, MySqliteHelper.COLUMN_APP_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null);

        //Obtenemos el primer registro del cursor
        if(cursor.moveToFirst()){
            modelApp.id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_ID));
            modelApp.appDeveloperName = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DEVELOPER));
            modelApp.appName = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_NAME));
            modelApp.appResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_RESOURCE));
            modelApp.appUpdated = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_UPDATED));
            modelApp.appDetail = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_APP_DETAIL));
        }
        else
            modelApp = null;

        //Cerramos la conexión
        if(db.isOpen())
            db.close();

        return modelApp;
    }

    public boolean updateApp(ModelApp modelApp){
        //Bandera de retorno
        boolean success;

        //Abrimos la conexión
        db = helper.getWritableDatabase();

        //Configuramos el elemento a actualizar
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.COLUMN_APP_DEVELOPER, modelApp.appDeveloperName);
        contentValues.put(MySqliteHelper.COLUMN_APP_NAME, modelApp.appName);
        contentValues.put(MySqliteHelper.COLUMN_APP_RESOURCE, modelApp.appResourceId);
        contentValues.put(MySqliteHelper.COLUMN_APP_UPDATED, modelApp.appUpdated);
        contentValues.put(MySqliteHelper.COLUMN_APP_DETAIL, modelApp.appDetail);

        //Actualizamos y devolvemos verdadero si se actualizó
        success = db.update(MySqliteHelper.TABLENAME_APP, contentValues, MySqliteHelper.COLUMN_APP_ID + "=?",
                new String[]{String.valueOf(modelApp.id)}) == 1 ? true : false;

        //Cerramos la conexión
        if(db.isOpen())
            db.close();

        return success;
    }
}