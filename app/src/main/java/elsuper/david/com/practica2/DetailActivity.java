package elsuper.david.com.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import elsuper.david.com.practica2.fragment.FragmentDetail;
import elsuper.david.com.practica2.fragment.FragmentEdit;
import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.sql.AppDataSource;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class DetailActivity extends AppCompatActivity {

    //Identificador del modelo
    private static int idModel;
    //Para las operaciones con la tabla app_table
    private AppDataSource appDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Obtenemos los Extras
        int appId = getIntent().getExtras().getInt(Keys.KEY_APP_ID);
        String appDeveloper = getIntent().getExtras().getString(Keys.KEY_APP_DEVELOPER);
        String appName = getIntent().getExtras().getString(Keys.KEY_APP_NAME);
        int appResourceId = getIntent().getExtras().getInt(Keys.KEY_APP_RESOURCEID);
        int appUpdated = getIntent().getExtras().getInt(Keys.KEY_APP_UPDATED);
        String appDetail = getIntent().getExtras().getString(Keys.KEY_APP_DETAIL);

        //Asignamos el identificador
        idModel = appId;

        //Instanciamos el modelo
        ModelApp modelAppDetail = new ModelApp(appId,appDeveloper,appName,appResourceId,appUpdated,appDetail);

        //Por default mostramos el Fragment de detalle
        FragmentDetail fragmentDetail = FragmentDetail.newInstance(modelAppDetail);
        getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();

        //Instanciamos el acceso a la base de datos
        appDataSource = new AppDataSource(getApplicationContext());

        //Para el toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Consultamos el modelo para tener los datos actualizados
        ModelApp model = appDataSource.getApp(idModel);
        if(model == null) return false;

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_detail_edit:
                //Ponemos el fragmento de edición
                FragmentEdit fragmentEdit = FragmentEdit.newInstance(model);
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentEdit).commit();
                return true;
            case R.id.menu_detail_show:
                //Ponemos el fragmento de detalle
                FragmentDetail fragmentDetail = FragmentDetail.newInstance(model);
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
