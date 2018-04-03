package com.borax12.materialdaterangepickerexample;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.borax12.materialdaterangepickerexample.CONF.Server;
import com.borax12.materialdaterangepickerexample.CONF.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Activity_terbaru extends AppCompatActivity {

    //JSON Node Names
    private static final String TAG_FIXTURE = "fixture";
    private static final String TAG_PIN = "pin";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_TANGGAL = "tanggal";
    private static final String TAG_JAM_DATANG = "jam_datang";
    private static final String TAG_ISTIRAHAT = "istirahat";
    private static final String TAG_MASUK = "masuk";
    private static final String TAG_JAM_PULANG = "jam_pulang";
    ListView lv;
    // Data JSONArray
    JSONArray matchFixture = null;
    //HashMap to keep your data
    ArrayList<HashMap<String, String>> matchFixtureList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terbarus);

        lv = findViewById(android.R.id.list);
        new GetFixture().execute();
        this.setTitle("Absensi Terakhir");
    }

    @SuppressLint("StaticFieldLeak")
    private class GetFixture extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... arg) {
            //Create service handler class instance
            ServiceHandler serviceClient = new ServiceHandler();
            //Url request and response
            String URL_ITEMS = Server.TERBARU;
            Log.d("url: ", "> " + URL_ITEMS);
            String json = serviceClient.makeServiceCall(URL_ITEMS, ServiceHandler.GET);
            // print the json response in the log
            Log.d("Get match fixture response: ", "> " + json);

            if (json != null) {
                try {
                    Log.d("try", "in the try");
                    JSONObject jsonObj = new JSONObject(json);
                    Log.d("jsonObject", "new json Object");
                    // Getting JSON Array node
                    matchFixture = jsonObj.getJSONArray(TAG_FIXTURE);
                    Log.d("json aray", "user point array");
                    int len = matchFixture.length();
                    Log.d("len", "get array length");

                    // Looping through all data
                    for (int i = 0; i < matchFixture.length(); i++) {
                        JSONObject c = matchFixture.getJSONObject(i);
                        String pin = c.getString(TAG_PIN);
                        Log.d("pin", pin);
                        String nama = c.getString(TAG_NAMA);
                        Log.d("nama", nama);
                        String tanggal = c.getString(TAG_TANGGAL);
                        Log.d("tanggal", tanggal);
                        String jam_datang = c.getString(TAG_JAM_DATANG);
                        Log.d("jam_datang", jam_datang);
                        String istirahat = c.getString(TAG_ISTIRAHAT);
                        Log.d("istirahat", istirahat);
                        String masuk = c.getString(TAG_MASUK);
                        Log.d("masuk", masuk);
                        String jam_pulang = c.getString(TAG_JAM_PULANG);
                        Log.d("jam_pulang", jam_pulang);
                        // Temporary HashMap for single data
                        HashMap<String, String> matchFixture = new HashMap<>();
                        // Adding each child node to Hashmap key -> value
                        matchFixture.put(TAG_PIN, pin);
                        matchFixture.put(TAG_NAMA, nama);
                        matchFixture.put(TAG_TANGGAL, tanggal);
                        matchFixture.put(TAG_JAM_DATANG, jam_datang);
                        matchFixture.put(TAG_ISTIRAHAT, istirahat);
                        matchFixture.put(TAG_MASUK, masuk);
                        matchFixture.put(TAG_JAM_PULANG, jam_pulang);
                        //Adding fixture to List
                        matchFixtureList.add(matchFixture);
                    }
                } catch (JSONException e) {
                    Log.d("catch", "in the catch");
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            ListAdapter adapter = new SimpleAdapter(
                    Activity_terbaru.this, matchFixtureList,
                    R.layout.list_item, new String[]{
                    TAG_PIN, TAG_NAMA, TAG_TANGGAL, TAG_JAM_DATANG, TAG_ISTIRAHAT, TAG_MASUK,  TAG_JAM_PULANG
            }
                    , new int[]{
                    R.id.pin, R.id.nama, R.id.tanggal, R.id.jam_datang, R.id.istirahat, R.id.masuk,
                    R.id.jam_pulang
            }
            );

            lv.setAdapter(adapter);

        }

    }

}