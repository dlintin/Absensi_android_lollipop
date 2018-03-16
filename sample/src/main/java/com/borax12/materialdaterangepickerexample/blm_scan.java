package com.borax12.materialdaterangepickerexample;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Kuncoro on 22/03/2016.
 */
public class blm_scan extends Fragment {
    //URL to get JSON
    private String URL_ITEMS = "http://10.0.2.2/absensi/android/android_table/laporan_blmhadir.php";
    //JSON Node Names
    private static final String TAG_FIXTURE = "fixture";
    private static final String TAG_NO = "no";
    private static final String TAG_PIN = "pin";
    private static final String TAG_NAMA = "nama";
    ListView lv;
    // Data JSONArray
    JSONArray matchFixture = null;
    //HashMap to keep your data
    ArrayList<HashMap<String, String>> matchFixtureList = new ArrayList<HashMap<String, String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.terbarus, container, false);
        View rootView = inflater.inflate(R.layout.blm_scan, container, false);
        lv= (ListView)rootView.findViewById(android.R.id.list);
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Call Async task to get the match fixture
        new GetFixture().execute();
        getActivity().setTitle("Absensi Terakhir");
    }


    private class GetFixture extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg) {
            //Create service handler class instance
            ServiceHandler serviceClient = new ServiceHandler();
            //Url request and response
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
                        HashMap<String, String> matchFixture = new HashMap<String, String>();
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
                    blm_scan.this.getActivity(), matchFixtureList,
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