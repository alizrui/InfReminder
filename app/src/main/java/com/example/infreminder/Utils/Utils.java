package com.example.infreminder.Utils;

import androidx.room.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    /**
     * Pasar objeto string a JSONObject
     * Este método coge un string en formato Json y lo convierte en un JSONObject
     *
     * @param value
     * @return El objeto json si está bien formado o null en caso contrario
     * @throws JSONException
     */
    public static JSONObject stringToJson(String value) throws JSONException {
        if(value == null) {
            return null;
        }
        else {
            JSONObject json = new JSONObject(value);
            return json;
        }
    }

    /**
     * Pasar objeto Json a String
     * Este método coge un objeto JSNObject y lo convierte en un String
     *
     * @param json
     * @return EL String bien formado o null en caso contrario
     */
    public static String jsonToString(JSONObject json)  {
        return json == null ? null : json.toString();
    }
}
