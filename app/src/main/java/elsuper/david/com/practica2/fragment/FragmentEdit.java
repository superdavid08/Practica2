package elsuper.david.com.practica2.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import elsuper.david.com.practica2.DetailActivity;
import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.sql.AppDataSource;

/**
 * Created by Andrés David García Gómez
 */
public class FragmentEdit extends Fragment implements View.OnClickListener{

    private EditText etAppName;
    private EditText etDeveloperName;
    private EditText etDetail;
    private CheckBox cbUpdated;
    //Para las operaciones con la tabla app_table
    private AppDataSource appDataSource;
    private ModelApp modelApp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        //Instanciamos el acceso a la base de datos
        appDataSource = new AppDataSource(getActivity());

        //Obtenemos los controles y les asignamos sus valores
        etAppName = (EditText) view.findViewById(R.id.fragedit_etAppName);
        etDeveloperName = (EditText) view.findViewById(R.id.fragedit_etDeveloperName);
        etDetail = (EditText) view.findViewById(R.id.fragedit_etDetail);
        cbUpdated = (CheckBox) view.findViewById(R.id.fragedit_cbUpdated);

        //Obtenemos el modelo para presentar sus datos
        modelApp = ((DetailActivity)getActivity()).getModelAppDetail();

        etAppName.setText(modelApp.appName);
        etDeveloperName.setText(modelApp.appDeveloperName);
        etDetail.setText(modelApp.appDetail);
        cbUpdated.setChecked(modelApp.appUpdated == 0 ? false : true );

        //Seteamos el escucha para el botón
        view.findViewById(R.id.fragedit_btnEdit).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragedit_btnEdit:
                //Si son diferente de vacío
                modelApp.appName = etAppName.getText().toString();
                modelApp.appDeveloperName = etDeveloperName.getText().toString();
                modelApp.appDetail = etDetail.getText().toString();
                modelApp.appUpdated = cbUpdated.isChecked()? 1 : 0;

                appDataSource.updateApp(modelApp);
                FragmentDetail fragmentDetail = FragmentDetail.newInstance(modelApp);
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();
                break;
        }
    }
}
