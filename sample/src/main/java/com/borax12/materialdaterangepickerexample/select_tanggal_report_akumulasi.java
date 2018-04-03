package com.borax12.materialdaterangepickerexample;


import android.annotation.SuppressLint;
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
public class select_tanggal_report_akumulasi extends Fragment {

    String tglawal,tglakhir;

    //JSON Node Names
    private static final String TAG_FIXTURE = "fixture";
    private static final String TAG_PIN = "pin";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_JAM_DATANG = "jam_datang";
    private static final String TAG_KEHADIRAN = "kehadiran";
    private static final String TAG_STATUS_LEMBUR = "status_lembur";
    ListView lv;
    // Data JSONArray
    JSONArray matchFixture = null;
    //HashMap to keep your data
    ArrayList<HashMap<String, String>> matchFixtureList = new ArrayList<HashMap<String, String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//mengambil data dari tools
        Bundle bundle = getArguments();
        tglawal = bundle.getString("tglawal");
        tglakhir = bundle.getString("tglakhir");

//        return inflater.inflate(R.layout.terbarus, container, false);
        View rootView = inflater.inflate(R.layout.akumulasi, container, false);
        lv= rootView.findViewById(android.R.id.list);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Call Async task to get the match fixture
        new GetFixture().execute();
        getActivity().setTitle("Absensi Terakhir");
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
            String URL_ITEMS = Server.AKUMULASI;

            String json = serviceClient.makeServiceCall(URL_ITEMS +tglawal+"&tglakhir="+tglakhir, ServiceHandler.GET);
            // print the json response in the log

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
                        String nama = c.getString(TAG_NAMA);
                        String jam_datang = c.getString(TAG_JAM_DATANG);
                        String kehadiran = c.getString(TAG_KEHADIRAN);
                        String status_lembur = c.getString(TAG_STATUS_LEMBUR);


                        // Temporary HashMap for single data
                        HashMap<String, String> matchFixture = new HashMap<>();
                        // Adding each child node to Hashmap key -> value
                        matchFixture.put(TAG_PIN, pin);
                        matchFixture.put(TAG_NAMA, nama);
                        matchFixture.put(TAG_JAM_DATANG, jam_datang);
                        matchFixture.put(TAG_KEHADIRAN, kehadiran);
                        matchFixture.put(TAG_STATUS_LEMBUR, status_lembur);
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
                    select_tanggal_report_akumulasi.this.getActivity(), matchFixtureList,
                    R.layout.akumulasi_list, new String[]{
                    TAG_PIN, TAG_NAMA, TAG_JAM_DATANG, TAG_KEHADIRAN, TAG_STATUS_LEMBUR
            }
                    , new int[]{
                    R.id.pin, R.id.nama,  R.id.jam_datang, R.id.kehadiran, R.id.status_lembur
            }
            );

            lv.setAdapter(adapter);

        }

    }

}