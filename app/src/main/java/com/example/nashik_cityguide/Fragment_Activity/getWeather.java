package com.example.nashik_cityguide.Fragment_Activity;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getWeather extends AsyncTask<String, Void, String> {

    private TextView weather_result;

    public getWeather(TextView weather_result) {
        this.weather_result = weather_result;
    }

    protected String doInBackground(String... urls) {

        StringBuilder result = new StringBuilder();

        try{
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            // read the input
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line = reader.readLine())!=null){
                result.append(line).append("\n");
            }
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
        try{
            JSONObject jsonObject = new JSONObject(result);
            // Extracting specific data from the "main"
            JSONObject main = jsonObject.getJSONObject("main");
            double temp = main.getDouble("temp")-273.15;  // Kelvin to Celsius

            String weatherInfo = (String.format("%.2f", temp) + "°C\n");

            weather_result.setText(weatherInfo);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
