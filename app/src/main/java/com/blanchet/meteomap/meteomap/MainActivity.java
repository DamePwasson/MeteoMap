package com.blanchet.meteomap.meteomap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    protected String KEY = "preferenceTown";
    protected String PREF_TOWN = "PREF_TOWN";

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);

        String value = getPreference(KEY);

        final Button btnSave = (Button) findViewById(R.id.buttonChoose);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BigCalcul calcul = new BigCalcul();
                calcul.execute();
            }
        });
    }
    private class BigCalcul extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast
                 .makeText(getApplicationContext(), "Enregistrement", Toast.LENGTH_LONG)
                 .show();
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... arg0){

            final EditText town = (EditText) findViewById(R.id.editChoose);
            final String myTown = town.getText().toString();
            setPreference(KEY, myTown);

            int progress;
            for (progress=0;progress<=100;progress++)
            {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                publishProgress(progress);
                progress++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            Intent goMeteo = new Intent(MainActivity.this, MeteoActivity.class);
            startActivity(goMeteo);
            MainActivity.this.finish();
        }
    }

    /**
     * Récupère la valeur de l'objet SharedPreferences
     * @param key (String) clée
     * @return la valeur de la clée
     */
    public String getPreference(String key) {
        SharedPreferences _preference = this.getSharedPreferences(PREF_TOWN, Context.MODE_PRIVATE);
        String preference = _preference.getString(key, "");
        return preference;
    }

    /**
     * Enregistre la valeur
     * @param key (String) clée de enregistrement
     * @param value (String) valeur associé à la clée
     */
    public void setPreference(String key, String value) {
        SharedPreferences _sharedPreferences = getSharedPreferences(PREF_TOWN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        Toast.makeText(this, getResources().getString(R.string.preference_save), Toast.LENGTH_SHORT).show();
        CityPreference.setCityPreference(value);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.quit) {
            Persistance.alertQuit(
                    MainActivity.this,
                    getResources().getString(R.string.confirm_quit_title),
                    getResources().getString(R.string.confirm_quit),
                    getResources().getString(R.string.btn_no),
                    getResources().getString(R.string.btn_yes));
        }

        return super.onOptionsItemSelected(item);
    }
}
