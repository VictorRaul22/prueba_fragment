package com.example.prueba_fragment.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.Locale;

public class SMSLocationSender {

    private FragmentActivity activity;
    private static final int PERMISSIONS_REQUEST_CODE = 123;
    private static final String[] PERMISSIONS_REQUIRED = {Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION};

    private String[] phoneNumbers;
    public SMSLocationSender(FragmentActivity activity, String[] phoneNumbers) {
        this.activity = activity;
        this.phoneNumbers=phoneNumbers;
    }

    public void sendSMS() {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE);
        } else {
            TelephonyManager tm = (TelephonyManager)  activity.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getSimState() != TelephonyManager.SIM_STATE_READY) {
                Toast.makeText(activity, "No se puede enviar SMS, no hay una tarjeta SIM disponible", Toast.LENGTH_LONG).show();
                return;
            }
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                String message = String.format(Locale.getDefault(), "Mi ubicación es: http://maps.google.com/maps?q=%.6f,%.6f", location.getLatitude(), location.getLongitude());
                SmsManager smsManager = SmsManager.getDefault();
                for (String phoneNumber : phoneNumbers) {
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                }
                Toast.makeText(activity, "SMS enviado con la ubicación actual", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "No se pudo obtener la ubicación actual", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                sendSMS();
            } else {
                Toast.makeText(activity, "Se requieren permisos para enviar SMS y acceder a la ubicación del dispositivo", Toast.LENGTH_LONG).show();
            }
        }
    }
}