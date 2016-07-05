package elsuper.david.com.practica2.util;


import elsuper.david.com.practica2.R;

/**
 * Created by Andrés David García Gómez
 */
public class Keys {

    public static final String KEY_APP_SAVE_RECORD = "key_save_record"; //MainActivity - InsertActivity

    /*Guardamos en un arreglo los identificadores de las imagenes para
      tomarlas de forma aleatoria en la inserción de un ModelApp*/
    public static final int[ ] resourcesId = {  R.drawable.ic_action_3d_rotation,
                                                R.drawable.ic_action_android,
                                                R.drawable.ic_action_bug_report,
                                                R.drawable.ic_action_group_work,
                                                R.drawable.ic_action_https,
                                                R.drawable.ic_action_open_with,
                                                R.drawable.ic_action_settings_input_component,
                                                R.drawable.ic_action_shopping_cart};

    public static final String KEY_APP_ID = "key_id"; //MainActivity - DetailActivity - FragmentDetail - FragmentEdit
    public static final String KEY_APP_DEVELOPER = "key_developer"; //MainActivity - DetailActivity - FragmentDetail - FragmentEdit
    public static final String KEY_APP_NAME = "key_name"; //MainActivity - DetailActivity - FragmentDetail - FragmentEdit
    public static final String KEY_APP_RESOURCEID = "key_resource"; //MainActivity - DetailActivity - FragmentDetail - FragmentEdit
    public static final String KEY_APP_UPDATED = "key_updated"; //MainActivity - DetailActivity - FragmentDetail - FragmentEdit
    public static final String KEY_APP_DETAIL = "key_detail"; //MainActivity - DetailActivity - FragmentDetail - FragmentEdit

    public static final String KEY_SERVICE_UPDATE = "key_service_update"; //ServiceNotificationUpdate
}