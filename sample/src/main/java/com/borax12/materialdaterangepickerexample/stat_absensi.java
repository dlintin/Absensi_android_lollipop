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
public class stat_absensi extends Fragment {

    String tglawal,tglakhir;

    //URL to get JSON
    private String URL_ITEMS = "http://192.168.1.88/absensi/android/android_table/stat.php?tglawal=";

    //JSON Node Names
    private static final String TAG_FIXTURE = "fixture";
    private static final String TAG_PIN = "pin";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_TANGGAL = "tanggal";
    private static final String TAG_KETERLAMBATAN = "keterlambatan";
    private static final String TAG_KEHADIRAN = "kehadiran";
    private static final String TAG_STAT_LEMBUR = "lembur";
    private static final String TAG_JML_LEMBUR = "jml_lembur";
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
        View rootView = inflater.inflate(R.layout.stat, container, false);
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
            Log.d("url: ", "> " + URL_ITEMS+tglawal+"&tglakhir="+tglakhir);
            String json = serviceClient.makeServiceCall(URL_ITEMS+tglawal+"&tglakhir="+tglakhir, ServiceHandler.GET);
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
                        String keterlambatan = c.getString(TAG_KETERLAMBATAN);
                        Log.d("keterlambatan", keterlambatan);
                        String kehadiran = c.getString(TAG_KEHADIRAN);
                        Log.d("kehadiran", kehadiran);
                        String lembur = c.getString(TAG_STAT_LEMBUR);
                        Log.d("lembur", lembur);
                        String jml_lembur = c.getString(TAG_JML_LEMBUR);
                        Log.d("jml_lembur", jml_lembur);
                        // Temporary HashMap for single data
                        HashMap<String, String> matchFixture = new HashMap<String, String>();
                        // Adding each child node to Hashmap key -> value
                        matchFixture.put(TAG_PIN, pin);
                        matchFixture.put(TAG_NAMA, nama);
                        matchFixture.put(TAG_TANGGAL, tanggal);
                        matchFixture.put(TAG_KETERLAMBATAN, keterlambatan);
                        matchFixture.put(TAG_KEHADIRAN, kehadiran);
                        matchFixture.put(TAG_STAT_LEMBUR, lembur);
                        matchFixture.put(TAG_JML_LEMBUR, jml_lembur);
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
                    stat_absensi.this.getActivity(), matchFixtureList,
                    R.layout.stat_list, new String[]{
                    TAG_PIN, TAG_NAMA, TAG_TANGGAL, TAG_KETERLAMBATAN, TAG_KEHADIRAN, TAG_STAT_LEMBUR,  TAG_JML_LEMBUR
            }
                    , new int[]{
                    R.id.pin, R.id.nama, R.id.tanggal, R.id.terlambat, R.id.hadir, R.id.stat_lembur,
                    R.id.jml_lembur
            }
            );

            lv.setAdapter(adapter);

        }

    }


}