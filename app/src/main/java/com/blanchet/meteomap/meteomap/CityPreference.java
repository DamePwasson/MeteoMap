package com.blanchet.meteomap.meteomap;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Blanchet on 12/03/2015.
 */
public class CityPreference {
    SharedPreferences prefs;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getCity(){
        return prefs.getString("city", "Nantes, FR");
    }

    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}
