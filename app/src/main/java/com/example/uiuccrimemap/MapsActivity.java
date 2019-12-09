package com.example.uiuccrimemap;

import androidx.fragment.app.FragmentActivity;

import android.animation.TypeConverter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crimes);
        getJSON();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

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
        System.out.println("hello");
        String Json;
        try {
            InputStream stream = getAssets().open("test1JSON.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            Json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(Json);
            JSONArray array = obj.getJSONArray("data");
            JSONArray first = array.getJSONArray(0);
            JSONArray second = first.getJSONArray(38);
            String lat = second.getString(1);
            String lng = second.getString(2);
            double latitude = second.getDouble(1);
            double longitude = second.getDouble(2);
            System.out.println("AAAAAAAAAAAA"+lat+"XXXXXXXXXXXXXXXX");
            LatLng point = new LatLng(latitude, longitude);

            //JSONArray json = new JSONArray(Json);

            //JSONArray first = json.getJSONArray(0);
            String one = first.getString(1);
            System.out.println("BBBBBBBBBBB"+one+"XXXXXXXXXXXXXXXX");
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
