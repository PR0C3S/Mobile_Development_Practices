package com.pucmm.examen1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Data> listaDatos = new ArrayList<>();
    static RecyclerView recyclerView;
    public static int show = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            show = 2;
        }
        listaDatos.add(new Data("hola","Buena",10));
        loadDatos();
    }

    public void ChangeToADD(View view)
    {
        Intent intent = new Intent(this, SegundaVista.class);
        startActivity(intent);
    }
    public static void loadDatos()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.recyclerView.getContext(), show));
        AdapterData adapterData = new AdapterData();
        recyclerView.setAdapter(adapterData);
    }
}