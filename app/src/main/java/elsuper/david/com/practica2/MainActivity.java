package elsuper.david.com.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import elsuper.david.com.practica2.adapter.AdapterItemList;
import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.sql.AppDataSource;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class MainActivity extends AppCompatActivity {

    private ListView listViewApps;
    private static final int CODE_APP_SAVE = 1;
    private AppDataSource appDataSource;;
    private TextView tvListEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos los controles
        listViewApps = (ListView)findViewById(R.id.main_lvListApps);
        tvListEmpty = (TextView)findViewById(R.id.main_tvListEmpty);
        //Instanciamos el acceso a la base de datos
        appDataSource = new AppDataSource(getApplicationContext());

        //Mostramos la lista
        showList();

        //Manejamos su evento click
        listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Accedemos a la información del item
                AdapterItemList adapter = (AdapterItemList)parent.getAdapter();
                ModelApp modelApp = adapter.getItem(position);

                //Lanzamos la Activity de detalle y agregamos Extras
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("name_developer",modelApp.appDeveloperName);
                startActivity(intent);
            }
        });
    }

    private void showList() {
        //Obtenemos las Apps de la base de datos
        List<ModelApp> modelAppList = appDataSource.getAllApps();
        //Si no hay elementos mostramos un texto indicandolo
        if(modelAppList.size() == 0){
            tvListEmpty.setVisibility(View.VISIBLE);
            listViewApps.setVisibility(View.GONE);
        }
        else { //Si los hay, se muestran
            tvListEmpty.setVisibility(View.GONE);
            listViewApps.setVisibility(View.VISIBLE);
            listViewApps.setAdapter(new AdapterItemList(getApplicationContext(), modelAppList));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_add:
                startActivityForResult(new Intent(getApplicationContext(), InsertActivity.class),
                        CODE_APP_SAVE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(CODE_APP_SAVE == requestCode && requestCode == RESULT_OK){
            long result = data.getExtras().getLong(Keys.KEY_SAVE, -1);

            if(result != -1){
                showList();
            }
            else{
                //Toast.makeText(getApplicationContext(),R.string.reg_txtError, Toast.LENGTH_SHORT).show();
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }
}
