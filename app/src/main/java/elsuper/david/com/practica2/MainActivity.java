package elsuper.david.com.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    private TextView tvListEmpty;
    //Para el ActivityResult
    private static final int REQUEST_CODE_APP_SAVE = 1;
    //Para las operaciones con la tabla app_table
    private AppDataSource appDataSource;;

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

        //Manejamos el evento click de cada elemento
        listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Accedemos a la información del elemento
                AdapterItemList adapter = (AdapterItemList)parent.getAdapter();
                ModelApp modelApp = adapter.getItem(position);

                //Lanzamos la Activity de detalle y enviamos información del elemento a través de Extras
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra(Keys.KEY_APP_ID,modelApp.id);
                intent.putExtra(Keys.KEY_APP_DEVELOPER,modelApp.appDeveloperName);
                intent.putExtra(Keys.KEY_APP_NAME,modelApp.appName);
                intent.putExtra(Keys.KEY_APP_RESOURCEID,modelApp.appResourceId);
                intent.putExtra(Keys.KEY_APP_UPDATED,modelApp.appUpdated);
                intent.putExtra(Keys.KEY_APP_DETAIL,modelApp.appDetail);
                startActivity(intent);
            }
        });

        //Le ponemos su toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //Método que muestra todos las Apps existentes en la tabla app_table
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
            //Si el usuario de click en la opción del menú para agragar un nuevo elemento
            case R.id.menu_main_add:
                //Iniciamos la actividad de insert y quedamos a la espera de respuesta
                startActivityForResult(new Intent(getApplicationContext(), InsertActivity.class),
                        REQUEST_CODE_APP_SAVE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Si resultó exitoso el proceso de guardar
        if(REQUEST_CODE_APP_SAVE == requestCode && resultCode == RESULT_OK){
            //Recibimos el resultado de la activity de inserción
            long result = data.getExtras().getLong(Keys.KEY_APP_SAVE_RECORD, -1);

            //Si se pudo insertar, mostramos la lista con el nuevo elemento incluido
            if(result != -1){
                showList();
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }


    //Necesrio para cuando viene desde DetailActivity
    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }
}
