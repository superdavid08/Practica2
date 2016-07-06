package elsuper.david.com.practica2.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.sql.AppDataSource;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class FragmentEdit extends Fragment implements View.OnClickListener{

    private EditText etAppName;
    private EditText etDeveloperName;
    private EditText etDetail;
    private CheckBox cbUpdated;
    private ModelApp modelApp;
    //Para las operaciones con la tabla app_table
    private AppDataSource appDataSource;

    //Metodo que obtiene el modelo para pasarselo al fragmento a través del bundle
    public static FragmentEdit newInstance(ModelApp modelApp)
    {
        FragmentEdit fragment = new FragmentEdit();
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
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        //Instanciamos el acceso a la base de datos
        appDataSource = new AppDataSource(getActivity());

        //Obtenemos los controles
        etAppName = (EditText) view.findViewById(R.id.fragedit_etAppName);
        etDeveloperName = (EditText) view.findViewById(R.id.fragedit_etDeveloperName);
        etDetail = (EditText) view.findViewById(R.id.fragedit_etDetail);
        cbUpdated = (CheckBox) view.findViewById(R.id.fragedit_cbUpdated);

        //Les asignamos los valores del bundle
        etAppName.setText(getArguments().getString(Keys.KEY_APP_NAME));
        etDeveloperName.setText(getArguments().getString(Keys.KEY_APP_DEVELOPER));
        etDetail.setText(getArguments().getString(Keys.KEY_APP_DETAIL));
        cbUpdated.setChecked(getArguments().getInt(Keys.KEY_APP_UPDATED) == 0 ? false : true );

        //Asignamos también el id y el resourceId al objeto que se editará
        modelApp = new ModelApp();
        modelApp.id = getArguments().getInt(Keys.KEY_APP_ID);
        modelApp.appResourceId = getArguments().getInt(Keys.KEY_APP_RESOURCEID);

        //Seteamos el escucha para el botón
        view.findViewById(R.id.fragedit_btnEdit).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragedit_btnEdit:
                updateModel();
                break;
        }
    }

    private void updateModel() {
        //Validamos que todos los campos tengan texto
        if(!TextUtils.isEmpty(etAppName.getText().toString()) &&
                !TextUtils.isEmpty(etDeveloperName.getText().toString()) &&
                !TextUtils.isEmpty(etDetail.getText().toString())) {

            //Llenamos el model que se actualizará (ya teníamos el id y el resourceId)
            modelApp.appName = etAppName.getText().toString();
            modelApp.appDeveloperName = etDeveloperName.getText().toString();
            modelApp.appDetail = etDetail.getText().toString();
            modelApp.appUpdated = cbUpdated.isChecked()? 1 : 0;

            //Actualizamos el modelo
            if(appDataSource.updateApp(modelApp)) {
                //Sustituimos el Fragment de edición por el de detalle pasandole el modelo actualizado
                FragmentDetail fragmentDetail = FragmentDetail.newInstance(modelApp);
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();
                Toast.makeText(getActivity(),R.string.fragedit_msgAppEdited, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(),R.string.fragedit_msgAppNotEdited, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(),R.string.insert_msgRequiredFields, Toast.LENGTH_SHORT).show();
        }
    }
}
