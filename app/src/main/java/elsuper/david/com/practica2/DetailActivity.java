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
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class DetailActivity extends AppCompatActivity {

    //Para acceder a ella en los dos Fragments
    public static ModelApp modelAppDetail;

    public static ModelApp getModelAppDetail() {
        return modelAppDetail;
    }

    public static void setModelAppDetail(ModelApp modelAppDetail) {
        DetailActivity.modelAppDetail = modelAppDetail;
    }

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

        modelAppDetail = new ModelApp(appId,appDeveloper,appName,appResourceId,appUpdated,appDetail);


        //Por default mostramos el Fragment de detalle
        FragmentDetail fragmentDetail = FragmentDetail.newInstance(modelAppDetail);
        getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();

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
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_detail_edit:
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, new FragmentEdit()).commit();
                return true;
            case R.id.menu_detail_show:
                FragmentDetail fragmentDetail = FragmentDetail.newInstance(modelAppDetail);
                getFragmentManager().beginTransaction().replace(R.id.detail_flFragmentFolder, fragmentDetail).commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
