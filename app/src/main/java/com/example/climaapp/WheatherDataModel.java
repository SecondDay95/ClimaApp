package com.example.climaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

//There we get the data from the jason object and adding this data to a Java object
//In the MVC design pattern data management and manipulation is the job of the model.

public class WheatherDataModel extends AppCompatActivity {

    private String mTemperature;
    private int mCondition;
    private String mCity;
    private String mIconName;

    //Creating a method by which, the wheather data can be extracted from the Jason object and added into Java object
    //This method will return a WheatherDataModel
    public static WheatherDataModel fromJSON (JSONObject jsonObject) {

        try {
            WheatherDataModel wheatherDataModel = new WheatherDataModel();

            wheatherDataModel.mCity = jsonObject.getString("name");
            wheatherDataModel.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            wheatherDataModel.mIconName = updateWheatherIcon(wheatherDataModel.mCondition);

            double temp = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundTempValue = (int) Math.rint(temp);
            wheatherDataModel.mTemperature = Integer.toString(roundTempValue);
            return wheatherDataModel;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String updateWheatherIcon (int condition) {
        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        }
        else if (condition >= 300 && condition < 500) {
            return "light_rain";
        }
        else if (condition >= 500 && condition < 600) {
            return "shower3";
        }
        else if (condition >= 600 && condition < 700) {
            return "snow4";
        }
        else if (condition >= 701 && condition < 771) {
            return "fog";
        }
        else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        }
        else if (condition == 800) {
            return "sunny";
        }
        else if (condition >= 801 && condition < 804) {
            return "cloudy2";
        }
        else if (condition >= 900 && condition < 902) {
            return "tstorm3";
        }
        else if (condition == 903) {
            return "snow5";
        }
        else if (condition == 904) {
            return "sunny";
        }
        else if (condition >= 905 && condition < 1000) {
            return "tstorm3";
        }
        return "dunno";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather_data_model);
    }

    public String getmTemperature() {
        return mTemperature + "°";
    }

    public String getmCity() {
        return mCity;
    }

    public String getmIconName() {
        return mIconName;
    }
}
