package com.example.nashik_cityguide.Fragment_Activity;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class getWeather {

    private final TextView weather_result;
    private final ExecutorService executorService;
    private final Handler mainHandler;
    private String resultData;

    public getWeather(TextView weather_result) {
        this.weather_result = weather_result;
        this.executorService = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Execute the weather fetch in a background thread.
     * Returns itself for chaining. Use get() to retrieve the result after completion.
     */
    public getWeather execute(String url) {
        executorService.execute(() -> {
            String result = doInBackground(url);
            resultData = result;
            mainHandler.post(() -> onPostExecute(result));
        });
        return this;
    }

    /**
     * Returns the result data. Note: May be null if called before background work completes.
     */
    public String get() {
        return resultData;
    }

    private String doInBackground(String urlString) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
            inputStream.close();
            urlConnection.disconnect();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void onPostExecute(String result) {
        try {
            if (result == null) {
                weather_result.setText("No Data Found");
                return;
            }
            JSONObject jsonObject = new JSONObject(result);
            JSONObject main = jsonObject.getJSONObject("main");
            double temp = main.getDouble("temp") - 273.15;  // Kelvin to Celsius

            String weatherInfo = (String.format("%.2f", temp) + "°C\n");
            weather_result.setText(weatherInfo);
        } catch (Exception e) {
            e.printStackTrace();
            weather_result.setText("No Data Found");
        }
    }
}
