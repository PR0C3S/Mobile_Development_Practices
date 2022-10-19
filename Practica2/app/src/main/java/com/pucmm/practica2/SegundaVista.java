package com.pucmm.practica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Console;
import java.net.URI;

public class SegundaVista extends AppCompatActivity {

    private Button btnStorage, btnPhone, btnLocation, btnCamara, btnContacts;
    LocationManager locationManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_vista);
        btnCamara = (Button) findViewById(R.id.btnCamara);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnPhone = (Button) findViewById(R.id.btnPhone);
        btnStorage = (Button) findViewById(R.id.btnStorage);

    }

    public void StorageClick(View view) {
        if (MainActivity.checkPermission(MainActivity.STORAGE_PERMISION, getApplicationContext())) {
            Snackbar snackbar = Snackbar.make(view, "Permission already granted", Snackbar.LENGTH_LONG);
            snackbar.show();
            snackbar.setAction("Open", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("file/*");
                    startActivity(intent);
                }
            });

        } else {
            Snackbar.make(view, "You dont have STORAGE permission", Snackbar.LENGTH_LONG).show();
        }
    }

    public void LocationClick(View view) {
        if (MainActivity.checkPermission(MainActivity.LOCATION_PERMISION, getApplicationContext())) {
            Snackbar snackbar = Snackbar.make(view, "Permission already granted", Snackbar.LENGTH_LONG);
            snackbar.show();
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            snackbar.setAction("Open", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (locationGPS != null) {
                        double latitude = locationGPS.getLatitude();
                        double longitude = locationGPS.getLongitude();
                        Uri gmmIntentUri = Uri.parse("geo:" + longitude + "," + latitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        } else {
                            Snackbar.make(view, "You dont have a app to open this", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(view, "Unable to find location.", Snackbar.LENGTH_LONG).show();
                    }

                }

                });

            } else{
                Snackbar.make(view, "You dont have LOCATION permission", Snackbar.LENGTH_LONG).show();
            }

        }

        public void CallClick (View view){
            if (MainActivity.checkPermission(MainActivity.PHONE_PERMISION, getApplicationContext())) {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = "+1 809-222-3333";
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                        startActivity(intent);
                    }
                });

            } else {
                Snackbar.make(view, "You dont have PHONE permission", Snackbar.LENGTH_LONG).show();
            }

        }

        public void CamaraClick (View view){
            if (MainActivity.checkPermission(MainActivity.CAMARA_PERMISION, getApplicationContext())) {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivity(intent);
                    }
                });

            } else {
                Snackbar.make(view, "You dont have CAMARA permission", Snackbar.LENGTH_LONG).show();
            }
        }

        public void ContactClick (View view){
            if (MainActivity.checkPermission(MainActivity.CONTACTS_PERMISION, getApplicationContext())) {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivity(intent);
                    }
                });

            } else {
                Snackbar.make(view, "You dont have CONTACTS permission", Snackbar.LENGTH_LONG).show();
            }

        }

    }