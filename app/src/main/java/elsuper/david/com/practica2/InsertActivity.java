package elsuper.david.com.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import elsuper.david.com.practica2.model.ModelApp;
import elsuper.david.com.practica2.sql.AppDataSource;
import elsuper.david.com.practica2.util.Keys;

/**
 * Created by Andrés David García Gómez
 */
public class InsertActivity extends AppCompatActivity {

    private EditText etAppName;
    private EditText etDeveloperName;
    private EditText etDetail;
    private CheckBox cbUpdated;
    //Para las operaciones con la tabla app_table
    private AppDataSource appDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //Obtenemos los controles
        etAppName = (EditText)findViewById(R.id.insert_etAppName);
        etDeveloperName = (EditText)findViewById(R.id.insert_etDeveloperName);
        etDetail = (EditText)findViewById(R.id.insert_etDetail);
        cbUpdated = (CheckBox) findViewById(R.id.insert_cbUpdated);
        //Instanciamos el acceso a la base de datos
        appDataSource = new AppDataSource(getApplicationContext());

        //Manejamos el evento click del botón guardar
        findViewById(R.id.insert_btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validamos que todos los campos tengan texto
                if(!TextUtils.isEmpty(etAppName.getText().toString()) &&
                        !TextUtils.isEmpty(etDeveloperName.getText().toString()) &&
                        !TextUtils.isEmpty(etDetail.getText().toString())) {

                    //Obtenemos el contenido de los controles
                    String appName = etAppName.getText().toString();
                    String developerName = etDeveloperName.getText().toString();
                    String detail = etDetail.getText().toString();
                    //Si no está seleccionado se guarda como instalado (Updated = 0)
                    //Caso contrario, se guarda como actualizado (Updated = 1)
                    int updated = cbUpdated.isChecked() ? 0 : 1;

                    //Para la imagen generamos un número aleatorio y, según éste,
                    //se le asigna una de las 8 imagenes disponibles
                    Random random = new Random();
                    int number = (int)(random.nextDouble() * 8);
                    //Construimos el nuevo modelo
                    ModelApp modelApp = new ModelApp(0,developerName,appName,Keys.resourcesId[number],updated,detail);

                    //Guardamos en base de datos
                    long result = appDataSource.saveApp(modelApp);

                    //Si se pudo guardar
                    if(result != -1){
                        Toast.makeText(getApplicationContext(),R.string.insert_msgAppSaved, Toast.LENGTH_SHORT).show();

                        //Creamos el intent para el ActivityResult
                        Intent intent = new Intent();
                        intent.putExtra(Keys.KEY_APP_SAVE_RECORD, result);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.insert_msgAppNotSaved, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.insert_msgRequiredFields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Para el toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
