package com.example.uiuccrimemap;

import androidx.fragment.app.FragmentActivity;

import android.animation.TypeConverter;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LatLng p;
    private GoogleMap mMap;
    private List<Marker> markList = new ArrayList<>();
    private LatLng[] positions;
    String yearString;
    int yearInt;
    String addressString;
    String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crimes);
        yearString = getIntent().getExtras().getString("Year");
        yearInt = Integer.parseInt(yearString);
        addressString = getIntent().getExtras().getString("Date");



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(newMap -> {
            // NONLINEAR CONTROL FLOW: Code in this block is called later, after onCreate ends
            // It's a "callback" - it will be called eventually when the map is ready

            // Set the map variable so it can be used by other functions
            mMap = newMap;
            // Center it on campustown

            getJSON();
            GeoCode();
        });

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, LaunchActivity.class));
                System.out.println("User Switched back");
            }
        });
    }
    public void getJSON() {
        System.out.println("QQQQQQQQQQQQQQ" + yearString);
        System.out.println("WWWWWWWWWWWWWW" + addressString);
        System.out.println("hello");
        String Json;
        try {
            InputStream stream = getAssets().open("cannabis2001.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            Json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(Json);
            JSONArray jArray = obj.getJSONArray("data");

            for (int i = 0; i < jArray.length(); i++) {
                JSONArray first = jArray.getJSONArray(i);
                System.out.println("start");
                if ( first.isNull(38) == false) {
                    System.out.println("end");
                    JSONArray second = first.getJSONArray(38);
                    if (first.isNull(23) == false) {
                        System.out.println("1");
                        if (first.isNull(11) == false) {
                            System.out.println("2");
                            int year = first.getInt(11);
                            String crime = "Theft";
                            System.out.println(year);
                            if (year == yearInt) {
                                System.out.println("3");
                                String crimeT = first.get(23).toString();
                                System.out.println("Crime: " + first.get(23).toString());
                                //if (crimeT.equals(crime)) {
                                    System.out.println("4");
                                    if (second.isNull(1) == false) {

                                        double lat = second.getDouble(1);
                                        double lng = second.getDouble(2);
                                        LatLng point = new LatLng(lat, lng);
                                        MarkerOptions mark = new MarkerOptions().position(point);
                                        Marker marker = mMap.addMarker(mark);
                                        System.out.println("Marker at Lat: " + lat);
                                        System.out.println("Crime: " + crime);
                                        markList.add(marker);
                                    }
                                //}

                            }

                        }
                    }
                }




            }
            //JSONArray array = obj.getJSONArray("data");
            //JSONArray first = array.getJSONArray(0);
            //JSONArray second = first.getJSONArray(38);
            //String lat = second.getString(1);
            //String lng = second.getString(2);
            //double latitude = second.getDouble(1);
            //double longitude = second.getDouble(2);
            //System.out.println("AAAAAAAAAAAA"+lat+"XXXXXXXXXXXXXXXX");
            //p = new LatLng(latitude, longitude);

            //JSONArray json = new JSONArray(Json);

            //JSONArray first = json.getJSONArray(0);
            //String one = first.getString(1);
            //System.out.println("BBBBBBBBBBB"+one+"XXXXXXXXXXXXXXXX");
            //for (int i = 0; i < jsonArray.length(); i++) {
             //   JSONArray k = jsonArray.getJSONArray(i);

            //}
            //for (JsonElement k : games) {
            //   JsonObject kasObject = (JsonObject) k;
            //   JsonArray player = kasObject.get("players").getAsJsonArray();
            //   for (JsonElement i : player) {
            //      JsonObject iasObject = (JsonObject) i;


        } catch (IOException a) {
            a.printStackTrace();
        } catch (JSONException a) {
            a.printStackTrace();
        }

    }
    //final GoogleMap map
    private void GeoCode() {
        String address = addressString;

        GeoCoder tracy = new GeoCoder();
        tracy.getAddress(address, getApplicationContext(), new GeoHandler());
        System.out.println("NNN" +location);

    }

    public void receiveResult(String result){
        String[] arrOfStr = result.split(" ");
        String latitudee = arrOfStr[0];
        String longitudee = arrOfStr[1];
        double lat = Double.parseDouble(latitudee);
        double lng = Double.parseDouble(longitudee);
        System.out.println("result: " + result);
        System.out.println("HHHLat: " + latitudee);
        System.out.println("HHHLong: " + longitudee);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //We didn't use this.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(p));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
    }

    private class GeoHandler extends Handler {

        public void handleMessage(Message msg) {
        String address3;
        switch (msg.what) {
            case 1:
                Bundle bundle = msg.getData();
                address3 = bundle.getString("address");

                receiveResult(address3);
                break;
            default:
                address3 = null;
        }

        System.out.println("BBBB"+address3);
        System.out.println("BBBBB" + location);
        }

    }
}
