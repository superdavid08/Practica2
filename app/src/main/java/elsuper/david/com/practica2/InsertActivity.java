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
    private AppDataSource appDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        etAppName = (EditText)findViewById(R.id.insert_etAppName);
        etDeveloperName = (EditText)findViewById(R.id.insert_etDeveloperName);
        etDetail = (EditText)findViewById(R.id.insert_etDetail);
        cbUpdated = (CheckBox) findViewById(R.id.insert_cbUpdated);
        appDataSource = new AppDataSource(getApplicationContext());

        findViewById(R.id.insert_btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(etAppName.getText().toString()) &&
                        !TextUtils.isEmpty(etDeveloperName.getText().toString()) &&
                        !TextUtils.isEmpty(etDetail.getText().toString())) {

                    String appName = etAppName.getText().toString();
                    String developerName = etDeveloperName.getText().toString();
                    String detail = etDetail.getText().toString();
                    int updated = cbUpdated.isChecked() ? 0 : 1; //Updated = 0 en BD = Instalada = chechbox sin selección

                    //Para la imagen, generamos un número aleatorio y, según éste, se asigna una de las 8 imagenes
                    Random random = new Random();
                    int number = (int)(random.nextDouble() * 8);

                    ModelApp modelApp = new ModelApp(developerName,appName,Keys.resourcesId[number],updated,detail);
                    long result = appDataSource.saveApp(modelApp);

                    if(result != -1)
                        Toast.makeText(getApplicationContext(),"Insertado", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra(Keys.KEY_SAVE, result);
                    setResult(RESULT_OK, intent);
                    finish();
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
