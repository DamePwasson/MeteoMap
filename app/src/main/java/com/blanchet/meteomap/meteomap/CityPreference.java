package com.blanchet.meteomap.meteomap;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Blanchet on 12/03/2015.
 */
public class CityPreference {
    static String town;

    public static void setCityPreference(String p){
        CityPreference.town = p;
    }

    public static String getCityPreference(){
        return (CityPreference.town);
    }
}
