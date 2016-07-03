package elsuper.david.com.practica2.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class FragmentDetail extends Fragment implements View.OnClickListener {

    private ImageView ivImage;
    private TextView tvAppName;
    private TextView tvDeveloperName;
    private TextView tvDetail;


    //Metodo que obtiene el modelo para pasarselo al fragmento a través del bundle
    public static FragmentDetail newInstance(ModelApp modelApp)
    {
        FragmentDetail f = new FragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.KEY_APP_ID, modelApp.id);
        bundle.putString(Keys.KEY_APP_DEVELOPER, modelApp.appDeveloperName);
        bundle.putString(Keys.KEY_APP_NAME, modelApp.appName);
        bundle.putInt(Keys.KEY_APP_RESOURCEID, modelApp.appResourceId);
        bundle.putInt(Keys.KEY_APP_UPDATED, modelApp.appUpdated);
        bundle.putString(Keys.KEY_APP_DETAIL, modelApp.appDetail);

        f.setArguments(bundle);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //Obtenemos los controles y les asignamos sus valores
        ivImage = (ImageView)view.findViewById(R.id.fragdetail_ivImage);
        tvAppName = (TextView)view.findViewById(R.id.fragdetail_tvAppName);
        tvDeveloperName = (TextView)view.findViewById(R.id.fragdetail_tvDeveloperName);
        tvDetail = (TextView)view.findViewById(R.id.fragdetail_tvDetail);

        ivImage.setImageResource(getArguments().getInt(Keys.KEY_APP_RESOURCEID));
        tvAppName.setText(getArguments().getString(Keys.KEY_APP_NAME));
        tvDeveloperName.setText(getArguments().getString(Keys.KEY_APP_DEVELOPER));
        tvDetail.setText(getArguments().getString(Keys.KEY_APP_DETAIL));


        //Seteamos el escucha para los botone
        view.findViewById(R.id.fragdetail_btnUninstall).setOnClickListener(this);
        view.findViewById(R.id.fragdetail_btnOpen).setOnClickListener(this);
        view.findViewById(R.id.fragdetail_btnUpdate).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragdetail_btnUninstall:
                break;
            case R.id.fragdetail_btnOpen:
                //Abrimos la página de google
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.google.com.mx"));
                getActivity().startActivity(intent);
                break;
            case R.id.fragdetail_btnUpdate:
                break;
        }
    }
}

