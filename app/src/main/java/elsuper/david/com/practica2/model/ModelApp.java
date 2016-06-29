package elsuper.david.com.practica2.model;

/**
 * Created by Andrés David García Gómez
 */
public class ModelApp {
    public int id; //Identificador
    public String appDeveloperName; //Nombre del desarrollador
    public String appName; //Nombre de la App
    public int appResourceId; //Id de la imagen de la App
    public int appUpdate; //Bandera para saber si la App está actualizada
    public String appDetail; //Detalle de la App

    //Constructor
    public ModelApp(String appDeveloperName, String appName, int appResourceId, int appUpdate, String appDetail) {
        this.appDeveloperName = appDeveloperName;
        this.appName = appName;
        this.appResourceId = appResourceId;
        this.appUpdate = appUpdate;
        this.appDetail = appDetail;
    }
}
