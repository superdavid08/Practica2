package elsuper.david.com.practica2.model;

/**
 * Created by Andrés David García Gómez
 */
public class ModelApp {
    public int id; //Identificador
    public String appDeveloperName; //Nombre del desarrollador
    public String appName; //Nombre de la App
    public int appResourceId; //Id del recurso de la imagen de la App
    public int appUpdated; //Bandera para saber si la App está actualizada
    public String appDetail; //Detalle de la App

    //Constructores
    public ModelApp() { }

    public ModelApp(String appDeveloperName, String appName, int appResourceId, int appUpdated, String appDetail) {
        this.appDeveloperName = appDeveloperName;
        this.appName = appName;
        this.appResourceId = appResourceId;
        this.appUpdated = appUpdated;
        this.appDetail = appDetail;
    }
}
