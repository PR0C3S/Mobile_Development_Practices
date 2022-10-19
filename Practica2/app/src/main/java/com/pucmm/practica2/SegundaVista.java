package com.pucmm.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;

public class SegundaVista extends AppCompatActivity {

    private Button btnStorage, btnPhone, btnLocation, btnCamara, btnContacts;
    private FusedLocationProviderClient client;

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
        client = LocationServices.getFusedLocationProviderClient(this);

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

            snackbar.setAction("Open", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (MainActivity.checkPermission(MainActivity.LOCATION_PERMISION, getApplicationContext())) {

                        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {

                                Location location =task.getResult();
                                if(location != null)
                                {
                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                        Uri gmmIntentUri = Uri.parse("geo:" + addresses.get(0).getLongitude() + "," + addresses.get(0).getLatitude());
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                            startActivity(mapIntent);
                                        } else {
                                            Snackbar.make(view, "You dont have a app to open this", Snackbar.LENGTH_LONG).show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Snackbar.make(view, "Last location was null", Snackbar.LENGTH_LONG).show();

                                }

                            }
                        });

                    }

                }

            });

        } else {
            Snackbar.make(view, "You dont have LOCATION permission", Snackbar.LENGTH_LONG).show();
        }

    }

    public void CallClick(View view) {
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

    public void CamaraClick(View view) {
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

    public void ContactClick(View view) {
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