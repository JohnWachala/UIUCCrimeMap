package com.example.uiuccrimemap;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoCoder {
    public static void getAddress(String address, Context context, Handler handler) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                String realResult;
                try {
                    List addressList = geocoder.getFromLocationName(address, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address2 = (Address) addressList.get(0);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(address2.getLatitude()).append(" ");
                        stringBuilder.append(address2.getLongitude());
                        result = stringBuilder.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        realResult = result;
                        bundle.putString("address", realResult);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();

    }
}
