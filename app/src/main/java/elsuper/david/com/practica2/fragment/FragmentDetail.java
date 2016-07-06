package elsuper.david.com.practica2.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.service.ServiceNotificationUninstall;
import elsuper.david.com.practica2.service.ServiceNotificationUpdate;
import elsuper.david.com.practica2.sql.AppDataSource;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class FragmentDetail extends Fragment implements View.OnClickListener {

    private ImageView ivImage;
    private TextView tvAppName;
    private TextView tvDeveloperName;
    private TextView tvDetail;
    private Button btnUninstall;
    private Button btnOpen;
    private Button btnUpdate;
    private ProgressBar pbLoading;
    private TextView tvUninstalled;
    //Identificador del modelo
    private static int idModel;
    //Para las operaciones con la tabla app_table
    private AppDataSource appDataSource;

    private BroadcastReceiver broadcastReceiverUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean updated = intent.getExtras().getBoolean(Keys.KEY_SERVICE_UPDATE);
            if(updated) {
                //Si el servicio terminó, habilitamos botones
                btnUninstall.setEnabled(true);
                btnOpen.setEnabled(true);
                //Ocultamos el loading
                pbLoading.setVisibility(View.GONE);

                //Consultamos el modelo a través de su identificador
                ModelApp model = appDataSource.getApp(idModel);

                //Actualizamos su campo updated
                model.appUpdated = 1;
                appDataSource.updateApp(model);

                //Ponemos el fragmento de detalle con información actualizada
                FragmentDetail fragmentDetail = FragmentDetail.newInstance(model);
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();
            }
            else{
                //Bloqueamos botónes mientras no haya termiado el servicio de actualización
                btnUninstall.setEnabled(false);
                btnOpen.setEnabled(false);
            }
        }
    };

    private BroadcastReceiver broadcastReceiverUninstall = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean uninstalled = intent.getExtras().getBoolean(Keys.KEY_SERVICE_UNINSTALL);
            if(uninstalled) {
                //Si el servicio terminó, ocultamos el loading
                pbLoading.setVisibility(View.GONE);

                //Consultamos el modelo a través de su identificador
                ModelApp model = appDataSource.getApp(idModel);

                //Eliminamos el modelo de la base de datos
                appDataSource.deleteApp(model);

                //Mostramos el texto al usuario
                tvUninstalled.setVisibility(View.VISIBLE);
            }
            else{
                //Desaparecemos los botónes porque la APP se está desinstalando
                btnUninstall.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
            }
        }
    };

    //Metodo que obtiene el modelo para pasarselo al fragmento a través del bundle
    public static FragmentDetail newInstance(ModelApp modelApp)
    {
        FragmentDetail fragment = new FragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.KEY_APP_ID, modelApp.id);
        bundle.putString(Keys.KEY_APP_DEVELOPER, modelApp.appDeveloperName);
        bundle.putString(Keys.KEY_APP_NAME, modelApp.appName);
        bundle.putInt(Keys.KEY_APP_RESOURCEID, modelApp.appResourceId);
        bundle.putInt(Keys.KEY_APP_UPDATED, modelApp.appUpdated);
        bundle.putString(Keys.KEY_APP_DETAIL, modelApp.appDetail);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //Instanciamos el acceso a la base de datos
        appDataSource = new AppDataSource(getActivity());

        //Obtenemos los controles
        ivImage = (ImageView)view.findViewById(R.id.fragdetail_ivImage);
        tvAppName = (TextView)view.findViewById(R.id.fragdetail_tvAppName);
        tvDeveloperName = (TextView)view.findViewById(R.id.fragdetail_tvDeveloperName);
        tvDetail = (TextView)view.findViewById(R.id.fragdetail_tvDetail);
        btnUninstall = (Button)view.findViewById(R.id.fragdetail_btnUninstall);
        btnOpen = (Button)view.findViewById(R.id.fragdetail_btnOpen);
        btnUpdate = (Button)view.findViewById(R.id.fragdetail_btnUpdate);
        pbLoading = (ProgressBar)view.findViewById(R.id.fragdetail_pbLoading);
        tvUninstalled = (TextView)view.findViewById(R.id.fragdetail_tvUninstalled);

        //Les asignamos los valores del bundle
        ivImage.setImageResource(getArguments().getInt(Keys.KEY_APP_RESOURCEID));
        tvAppName.setText(getArguments().getString(Keys.KEY_APP_NAME));
        tvDeveloperName.setText(getArguments().getString(Keys.KEY_APP_DEVELOPER));
        tvDetail.setText(getArguments().getString(Keys.KEY_APP_DETAIL));
        idModel = getArguments().getInt(Keys.KEY_APP_ID);

        //Si ya está actualizada, el botón de "Actualizar" permanece deshabilitado y su texto cambia a "Actualizado"
        if(getArguments().getInt(Keys.KEY_APP_UPDATED) == 1) {
            btnUpdate.setEnabled(false);
            btnUpdate.setText(R.string.adapter_messageStatus1);
        }

        //Seteamos el escucha para los botones
        btnUninstall.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragdetail_btnUninstall:
                //Mostramos un mensaje de confirmación
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.fragdetail_btnUninstall)
                        .setMessage(R.string.fragdetail_msgQuestionUninstall)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Mostramos el loading e iniciamos el servicio
                                pbLoading.setVisibility(View.VISIBLE);
                                getActivity().startService(new Intent(getActivity(), ServiceNotificationUninstall.class));
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }})
                        .setCancelable(false).create().show();
                break;
            case R.id.fragdetail_btnOpen:
                //Abrimos la página de google
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.google.com.mx"));
                getActivity().startActivity(intent);
                break;
            case R.id.fragdetail_btnUpdate:
                //Mostramos el loading e iniciamos el servicio
                pbLoading.setVisibility(View.VISIBLE);
                getActivity().startService(new Intent(getActivity(), ServiceNotificationUpdate.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Registramos los broadcastReceiver
        IntentFilter filterUpdate = new IntentFilter();
        filterUpdate.addAction(ServiceNotificationUpdate.ACTION_SEND_UPDATE_NOTIFICATION);
        getActivity().registerReceiver(broadcastReceiverUpdate,filterUpdate);

        IntentFilter filterUninstall = new IntentFilter();
        filterUninstall.addAction(ServiceNotificationUninstall.ACTION_SEND_UNINSTALL_NOTIFICATION);
        getActivity().registerReceiver(broadcastReceiverUninstall,filterUninstall);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Quitamos los broadcastReceiver
        getActivity().unregisterReceiver(broadcastReceiverUpdate);
        getActivity().unregisterReceiver(broadcastReceiverUninstall);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Detenemos los servicios
        getActivity().stopService(new Intent(getActivity(),ServiceNotificationUpdate.class));
        getActivity().stopService(new Intent(getActivity(),ServiceNotificationUninstall.class));
    }
}
