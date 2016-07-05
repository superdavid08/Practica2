package elsuper.david.com.practica2.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import elsuper.david.com.practica2.DetailActivity;
import elsuper.david.com.practica2.MainActivity;
import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class ServiceNotificationUpdate extends Service {

    public static final String ACTION_SEND_UPDATE_NOTIFICATION = "com.david.elsuper.SEND_UPDATE_NOTIFICATION";//
    public static boolean UPDATED = false;//
    private Handler handler = new Handler();//
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable,1000);
            Intent intent = new Intent(ACTION_SEND_UPDATE_NOTIFICATION);
            intent.putExtra(Keys.KEY_SERVICE_UPDATE,UPDATED);
            sendBroadcast(intent);
        }
    };

    private  MyAsyncTask myAsyncTask;

    @Override
    public void onCreate() {
        super.onCreate();
        handler.post(runnable);//
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(myAsyncTask == null){
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }

        return START_STICKY;
    }

    @Override//
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,Boolean> {

        private NotificationCompat.Builder notification;
        private int MAX = 10;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.serviceupdate_preContentTitle))
                    .setContentText(getString(R.string.serviceupdate_preContentText))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_android))
                    .setSmallIcon(android.R.drawable.ic_dialog_email);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
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
            notification.setProgress(MAX, values[0], false);
            if(values[0]== MAX-1)
                //Avisamos que ha terminado de actualizar
                UPDATED = true;

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notification.build());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //super.onPostExecute(aBoolean);
            if (result) {

                notification.setProgress(0, 0, false);//Eliminamos el progress
                notification.setContentTitle(getString(R.string.serviceupdate_postContentTitle));
                notification.setContentText(getString(R.string.serviceupdate_postContentText));
                notification.setContentInfo(getString(R.string.adapter_messageStatus1));

                notification.setAutoCancel(true);//Para que se borre

                notification.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.serviceupdate_postContentText)));

                /*PendingIntent pendingIntent = PendingIntent
                        .getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(),
                                DetailActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                notification.setContentIntent(pendingIntent);*/

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notification.build());
                UPDATED = false;
            }

            myAsyncTask = null;
            stopSelf();
        }
    }
}
