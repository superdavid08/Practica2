package elsuper.david.com.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import elsuper.david.com.practica2.adapter.AdapterItemList;
import elsuper.david.com.practica2.model.ModelApp;

public class MainActivity extends AppCompatActivity {

    private ListView listViewApps;
    private List<ModelApp> array = new ArrayList<>();

    /*
    private int counter;
    private boolean isWifi;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos el control de lista
        listViewApps = (ListView)findViewById(R.id.main_lvListApps);

        //Manejamos su evento click
        listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Accedemos a la información del item
                AdapterItemList adapter = (AdapterItemList)parent.getAdapter();
                ModelApp modelApp = adapter.getItem(position);
                ModelApp modelApp2 = array.get(position);
                //Toast.makeText(getApplicationContext(),modelApp2.appName, Toast.LENGTH_SHORT).show();

                //Lanzamos la Activity de detalle y agregamos Extras
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("name_developer",modelApp2.appDeveloperName);
                startActivity(intent);
            }
        });
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
                Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
