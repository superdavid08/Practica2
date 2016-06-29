package elsuper.david.com.practica2.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import elsuper.david.com.practica2.R;
import elsuper.david.com.practica2.model.ModelApp;

/**
 * Created by Andrés David García Gómez
 */
public class AdapterItemList extends ArrayAdapter<ModelApp>{

    public AdapterItemList(Context context, List<ModelApp> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            //Inflamos la vista del adaptador
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_list,parent,false);
        }

        //Obtenemos los controles
        ImageView appImg = (ImageView)convertView.findViewById(R.id.adapter_ivImage);
        TextView appName = (TextView)convertView.findViewById(R.id.adapter_tvAppName);
        TextView appDeveloper = (TextView)convertView.findViewById(R.id.adapter_tvDeveloperName);
        TextView appStatus = (TextView)convertView.findViewById(R.id.adapter_tvAppStatus);

        //Seteamos los datos en el elemento
        ModelApp modelApp = getItem(position);
        appImg.setImageResource(modelApp.appResourceId);
        appName.setText(modelApp.appName);
        appDeveloper.setText(modelApp.appDeveloperName);
        appStatus.setText(modelApp.appUpdate == 0 ?
                String.format(String.valueOf(R.string.adapter_messageStatus0)) :
                String.format(String.valueOf(R.string.adapter_messageStatus1)));

        //TODO el menú

        return convertView;
    }
}