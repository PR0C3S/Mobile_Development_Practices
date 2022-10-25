package com.pucmm.examen1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SegundaVista extends AppCompatActivity {

    EditText articulo, descripcion, precio;
    Button btnAdd, btnClear, btnReturn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_vista);

        articulo= findViewById(R.id.etArticulo);
        descripcion= findViewById(R.id.etDescripcion);
        precio = findViewById(R.id.etPrecio);
        btnAdd = findViewById(R.id.btnADD);
        btnClear = findViewById(R.id.btnClear);
        btnReturn = findViewById(R.id.btnReturn);
    }


    public void ClearData(View view)
    {
        articulo.setText("");
        descripcion.setText("");
        precio.setText("");
    }

    public void Return(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void AddData(View view)
    {
        String art = articulo.getText().toString();
        String desc =  descripcion.getText().toString();

        int prec = precio.getText().toString().equals("") ?0 :Integer.parseInt(precio.getText().toString());

       if(validate(art, desc, prec))
       {
           MainActivity.listaDatos.add(new Data(art,desc,prec));
           articulo.setText("");
           descripcion.setText("");
           precio.setText("");
           Toast.makeText(getApplicationContext(),"Objeto creado correctamente.",Toast.LENGTH_LONG).show();
       }
    }

    public Boolean validate(String articulo, String descripcion, int precio)
    {
        if(articulo.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Favor de completar el campo articulo",Toast.LENGTH_LONG).show();
            return false;
        }else{
            ArrayList<Data> listaDatos = MainActivity.listaDatos;

            if(!listaDatos.isEmpty())
            {
                for (Data act: listaDatos) {
                    if(act.getArticulo().equals(articulo))
                    {
                        Toast.makeText(getApplicationContext(),"Articulo creado, favor ingresar otro nombre", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
        }
        if(descripcion.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Favor de completar el campo descripcion",Toast.LENGTH_LONG).show();
            return false;

        }
        if(precio <= 0)
        {
            Toast.makeText(getApplicationContext(),"El precio del articulo debe ser mayor que 0",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}