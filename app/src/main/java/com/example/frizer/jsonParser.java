package com.example.frizer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class jsonParser {
    private static final String TAG = jsonParser.class.getSimpleName();
    private String jsonStr;
    private JSONObject reader;
    private URL url;

    public jsonParser(String json, JSONObject reader, URL url) {
        this.jsonStr = json;
        this.reader = reader;
        this.url = url;


    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public JSONObject getReader() {
        return reader;
    }

    public void setReader(JSONObject reader) {
        this.reader = reader;
    }

    public URL getUrl() {
        return url;
    }
    public void setUrl(URL url) {
        if(url!=null) {
            this.url = url;
        }else{
            Log.e(TAG, "URL NOT PARSED." );
        }
    }

    public String getUsluge() {
        //"https://frizer-backend.herokuapp.com/api/usluge"

        return "";
    }

    public String getUser() {
        //"https://frizer-backend.herokuapp.com/api/useri"
        return "";
    }

    public String getTermini() {
        //"https://frizer-backend.herokuapp.com/api/termini"
        return "";
    }

    public String readUrlContent(URL api) throws IOException {
        //URL api = new URL("https://frizer-backend.herokuapp.com/api/usluge");
        URLConnection yc = api.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            return inputLine;
        in.close();

        return inputLine;
    }
    public ArrayList<HashMap<String, String>> convert(String jsonStr, String searchWord, String rowName) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray arr = jsonObj.getJSONArray(searchWord);
        ArrayList<HashMap<String, String>> List = null;
        HashMap<String, String> rows = new HashMap<>();
        for(int i=0;i<arr.length();i++){
            JSONObject c = arr.getJSONObject(i);
            String row = c.getString(rowName);

//TODO Ako je api single moze ovako ako ne mjenjaj za api sa vise tipova(u ovom slucaju samo imaju uslugu
// ali moze da ima i usluga i termin isve)
            rows.put(searchWord,row);
            List.add(rows);

        }

        return List;



    }

}
