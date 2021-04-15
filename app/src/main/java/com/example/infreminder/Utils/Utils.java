package com.example.infreminder.Utils;

import androidx.room.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static JSONObject stringToJson(String value) throws JSONException {
        //Esto coge un string y lo convierte en json
        if(value == null) {
            return null;
        }
        else {
            JSONObject json = new JSONObject();
            return json.getJSONObject(value);
        }
    }

    public static String jsonToString(JSONObject json)  {
        //esto coge un json y lo convierte en un string
        return json == null ? null : json.toString();
    }
}
//Ejemplo de uso json:
//JSONObject myObject = new JSONObject();
//myObject.put("id", "00");
//myObject.put("action", "register");
// -> MyObject: {id:00, action:register}