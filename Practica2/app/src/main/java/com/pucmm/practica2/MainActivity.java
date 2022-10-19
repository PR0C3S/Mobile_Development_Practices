package com.pucmm.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.pucmm.practica2.databinding.ActivityMainBinding;

import java.io.Console;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Switch swStorage, swLocation, swCamara, swPhone, swContacts;
    private Button btnCancell, btnContinue;

    public static final String STORAGE_PERMISION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String CAMARA_PERMISION = Manifest.permission.CAMERA;
    public static final String LOCATION_PERMISION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PHONE_PERMISION = Manifest.permission.CALL_PHONE;
    public static final String CONTACTS_PERMISION = Manifest.permission.READ_CONTACTS;
    //public static final String LOCATION_PERMISION2 = Manifest.permission.ACCESS_FINE_LOCATION;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swStorage = (Switch) findViewById(R.id.swStorage);
        swLocation = (Switch) findViewById(R.id.swLocation);
        swCamara = (Switch) findViewById(R.id.swCamara);
        swPhone = (Switch) findViewById(R.id.swPhone);
        swContacts = (Switch) findViewById(R.id.swContacts);
        btnCancell = (Button) findViewById(R.id.btnCancell);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        loadToggles();

        swStorage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkPermission(STORAGE_PERMISION, getApplicationContext())) {
                    swStorage.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                }
            }
        });
        swLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkPermission(LOCATION_PERMISION, getApplicationContext())) {
                    swLocation.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                }
            }
        });
        swCamara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkPermission(CAMARA_PERMISION, getApplicationContext())) {
                    swCamara.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                }
            }
        });
        swPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkPermission(PHONE_PERMISION, getApplicationContext())) {
                    swPhone.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                }
            }
        });
        swContacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkPermission(CONTACTS_PERMISION, getApplicationContext())) {
                    swContacts.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToggles();
    }

    public static Boolean checkPermission(String permiso, Context context) {
        if (ContextCompat.checkSelfPermission(context, permiso) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public void loadToggles() {
        swStorage.setChecked(checkPermission(STORAGE_PERMISION, getApplicationContext()));
        swLocation.setChecked(checkPermission(LOCATION_PERMISION, getApplicationContext()));
        swCamara.setChecked(checkPermission(CAMARA_PERMISION, getApplicationContext()));
        swPhone.setChecked(checkPermission(PHONE_PERMISION, getApplicationContext()));
        swContacts.setChecked(checkPermission(CONTACTS_PERMISION, getApplicationContext()));
    }

    public void Cancelar(View view) {
        loadToggles();
    }

    public void Continuar(View view) {
        ArrayList<String> listaPermisos = new ArrayList<>();
        if (swStorage.isChecked() && !checkPermission(STORAGE_PERMISION, getApplicationContext())) {
            listaPermisos.add(STORAGE_PERMISION);
        }
        if (swLocation.isChecked() && !checkPermission(LOCATION_PERMISION, getApplicationContext())) {
            listaPermisos.add(LOCATION_PERMISION);
        }
        if (swCamara.isChecked() && !checkPermission(CAMARA_PERMISION, getApplicationContext())) {
            listaPermisos.add(CAMARA_PERMISION);
        }
        if (swPhone.isChecked() && !checkPermission(PHONE_PERMISION, getApplicationContext())) {
            listaPermisos.add(PHONE_PERMISION);
        }
        if (swContacts.isChecked() && !checkPermission(CONTACTS_PERMISION, getApplicationContext())) {
            listaPermisos.add(CONTACTS_PERMISION);
        }
        if(listaPermisos.isEmpty()){
            Intent intent = new Intent(this, SegundaVista.class);
            startActivity(intent);
        }else{
            String[] permisos = listaPermisos.toArray(new String[listaPermisos.size()]);
            requestPermissions(permisos, 200);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(this, SegundaVista.class);
        startActivity(intent);
    }
}