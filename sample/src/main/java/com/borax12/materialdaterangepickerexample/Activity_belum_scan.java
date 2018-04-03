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


/**
 * Created by Kuncoro on 22/03/2016.
 */
public class Activity_belum_scan extends AppCompatActivity {
    //JSON Node Names
    private static final String TAG_FIXTURE = "fixture";
    private static final String TAG_NO = "no";
    private static final String TAG_PIN = "pin";
    private static final String TAG_NAMA = "nama";
    ListView lv;
    // Data JSONArray
    JSONArray matchFixture = null;
    //HashMap to keep your data
    ArrayList<HashMap<String, String>> matchFixtureList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blm_scan);
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
            String URL_ITEMS = Server.BLUM_SCAN;
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
                        String no = c.getString(TAG_NO);
                        Log.d("no", no);
                        String pin = c.getString(TAG_PIN);
                        Log.d("pin", pin);
                        String nama = c.getString(TAG_NAMA);
                        Log.d("nama", nama);

                        // Temporary HashMap for single data
                        HashMap<String, String> matchFixture = new HashMap<>();
                        // Adding each child node to Hashmap key -> value
                        matchFixture.put(TAG_NO, no);
                        matchFixture.put(TAG_PIN, pin);
                        matchFixture.put(TAG_NAMA, nama);

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
                    Activity_belum_scan.this, matchFixtureList,
                    R.layout.blm_scan_list, new String[]{
                    TAG_NO, TAG_PIN, TAG_NAMA
            }
                    , new int[]{
                    R.id.no, R.id.pin, R.id.nama
            }
            );

            lv.setAdapter(adapter);

        }

    }


}