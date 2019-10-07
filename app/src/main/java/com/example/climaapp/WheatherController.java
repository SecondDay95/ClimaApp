package com.example.climaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WheatherController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE = 123;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "d624cde8770b88ea86b6a9c846412f68";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    //Set location provider:
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    TextView mCityLabel, mTemperatureLabel;
    ImageView mCityImage, weatherSymbol;
    ImageButton changeCityButton;

    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather_controller);

        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        weatherSymbol = (ImageView) findViewById(R.id.weatherSymbol);
        changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);

        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WheatherController.this, ChangeCityController.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Intent intent = getIntent();
        String cityName = intent.getStringExtra("City");

        if (cityName != null) {
            getWeatherForTheCityName(cityName);
        }
        else {
            getWeatherForCurrentLocation();
        }
    }

    private void getWeatherForTheCityName(String city) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("q", city);
        requestParams.put("appid", APP_ID);
        doNetworking(requestParams);
    }

    private void getWeatherForCurrentLocation() {
        //Create an object of locationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Create a new object of LocationListener:
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());
                Log.d("ClimaApp", "longitude is " + longitude);
                Log.d("ClimaApp", "latitude is " + latitude);
                //Preparing a request to get lat and long of the server:
                RequestParams requestParams = new RequestParams();
                requestParams.put("lat", latitude);
                requestParams.put("lon", longitude);
                requestParams.put("appid", APP_ID);
                doNetworking(requestParams);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        //Updates location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(WheatherController.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    //check if user allow or deny permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            //if allow
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("climaApp", "Permisiion granted");
                getWeatherForCurrentLocation();
            }
            //if deny
            else {
                Log.d("climaApp", "Permission Denied");
            }
        }
    }
    private void doNetworking (RequestParams params) {
        //Create Async Http client:
        //Client can do get/post/delete etc.
        AsyncHttpClient client = new AsyncHttpClient();
        //get request:
        //params do a networking methods. params is used to make an api call
        //new Json... object is used to notify of response of the get request
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            //Json object will receive one of two messages onSuccess or onFailure depending if the get request was succesful or not

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ClimaApp", "Success JSON ! " + response.toString());

                //Creating a weather data model object fromJason:
                //In response variable are JSON object with data
                WheatherDataModel wheatherDataModel = WheatherDataModel.fromJSON(response);
                updateUI(wheatherDataModel);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //if get request was failure:
                Log.e("ClimaApp", "Fail " + throwable.toString());
                Log.d("ClimaApp","Status Code = " + statusCode);
                Toast toast = Toast.makeText(WheatherController.this, "Request Failed", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }
    private void updateUI (WheatherDataModel wheatherDataModel) {
        mTemperatureLabel.setText(wheatherDataModel.getmTemperature());
        mCityLabel.setText(wheatherDataModel.getmCity());
        int resourceID = getResources().getIdentifier(wheatherDataModel.getmIconName(),
                "drawable", getPackageName());
        weatherSymbol.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //When the app was exited, quit of the locationListener
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
