package elsuper.david.com.practica2.util;


import elsuper.david.com.practica2.R;

/**
 * Created by Andrés David García Gómez
 */
public class Keys {

    public static final String KEY_SAVE = "key_save"; //InsertActivity - MainActivity //Tal vez se borre

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

    public static final String KEY_APP_ID = "key_id"; //InsertActivity - MainActivity
    public static final String KEY_APP_DEVELOPER = "key_developer"; //InsertActivity - MainActivity
    public static final String KEY_APP_NAME = "key_name"; //InsertActivity - MainActivity
    public static final String KEY_APP_RESOURCEID = "key_resource"; //InsertActivity - MainActivity
    public static final String KEY_APP_UPDATED = "key_updated"; //InsertActivity - MainActivity
    public static final String KEY_APP_DETAIL = "key_detail"; //InsertActivity - MainActivity
}