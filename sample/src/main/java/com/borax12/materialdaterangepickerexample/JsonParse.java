package com.borax12.materialdaterangepickerexample;

import android.util.Log;

import com.borax12.materialdaterangepickerexample.CONF.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParse {

    JsonParse(){}

    public List<SuggestGetSet> getParseJsonWCF(String sName)
    {
        List<SuggestGetSet> ListData = new ArrayList<>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL(Server.PERORANG+temp);
            URLConnection jc = js.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            JSONArray jsonArray = jsonResponse.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject r = jsonArray.getJSONObject(i);
                ListData.add(new SuggestGetSet(r.getString("nama")));
                Log.d("Nama", String.valueOf(ListData));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }

}