package elsuper.david.com.practica2.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class ServiceNotificationUpdate extends Service {

    public static final String ACTION_SEND_UPDATE_NOTIFICATION = "com.david.elsuper.SEND_UPDATE_NOTIFICATION";
    //Esta bandera indicará el momento en que ha terminado el proceso
    public static boolean UPDATED = false;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable,1000);
            Intent intent = new Intent(ACTION_SEND_UPDATE_NOTIFICATION);
            //Devolvemos la bandera de actualización
            intent.putExtra(Keys.KEY_SERVICE_UPDATE,UPDATED);
            sendBroadcast(intent);
        }
    };

    private  MyAsyncTask myAsyncTask;

    @Override
    public void onCreate() {
        super.onCreate();
        //Iniciamos el monitoreo de la bandera de actualización
        handler.post(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Ejecutamos nuestra tarea asincrona
        if(myAsyncTask == null){
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Terminamos el monitoreo
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,Boolean> {

        private NotificationCompat.Builder notification;
        //Número máximo de conteo en el proceso
        private int MAX = 10;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Configuramos la notificación inicial
            notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.serviceupdate_preContentTitle))
                    .setContentText(getString(R.string.services_preContentText))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_android))
                    .setSmallIcon(android.R.drawable.ic_dialog_email);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            //Ciclo del progreso (10 segundos)
            for (int i = 0; i < MAX; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
            //Avance del progreso
            notification.setProgress(MAX, values[0], false);
            if(values[0]== MAX-1) //Si ha llegado al máximo
                UPDATED = true; //Avisamos que ha terminado de actualizar

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(Keys.KEY_NOTIFICATION_UPDATE_ID, notification.build());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //super.onPostExecute(aBoolean);
            if (result) {
                //Configuramos la notificación final
                notification.setProgress(0, 0, false);//Eliminamos el progress
                notification.setContentTitle(getString(R.string.serviceupdate_postContentTitle));
                notification.setContentText(getString(R.string.serviceupdate_postContentText));
                notification.setContentInfo(getString(R.string.adapter_messageStatus1));

                notification.setAutoCancel(true);

                notification.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.serviceupdate_postContentText)));

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(Keys.KEY_NOTIFICATION_UPDATE_ID, notification.build());
                UPDATED = false; //Volvemos a poner el valor default
            }

            myAsyncTask = null;
            stopSelf();
        }
    }
}
