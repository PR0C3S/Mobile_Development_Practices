package com.pucmm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText nombre, apellido, nacimiento;
    private Spinner genero;
    private RadioButton isLikeProgrammer, isNotLikeProgrammer;
    private CheckBox isJava, isGo, isJS, isCShared, isC, isPython;
    private final String[] listaGeneros = {"Masculino", "Femenino"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        nacimiento = findViewById(R.id.txtNacimiento);
        isLikeProgrammer = findViewById(R.id.rbSi);
        isNotLikeProgrammer = findViewById(R.id.rbNo);
        isLikeProgrammer.setChecked(true);

        nacimiento = findViewById(R.id.txtNacimiento);
        genero = findViewById(R.id.spnGenero);
        isJava = findViewById(R.id.cbJava);
        isGo = findViewById(R.id.cbGo);
        isC = findViewById(R.id.cbC);
        isJS = findViewById(R.id.cbJS);
        isCShared = findViewById(R.id.cbCShared);
        isPython = findViewById(R.id.cbPython);

        genero = findViewById(R.id.spnGenero);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaGeneros);
        genero.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day= calendar.get(Calendar.DAY_OF_MONTH);


        nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        nacimiento.setText(date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    public void Limpiar(View view) {
        nombre.setText("");
        apellido.setText("");
        nacimiento.setText("");
        isLikeProgrammer.setChecked(true);
        genero.setSelection(0);
        limpiarLenguajes();
        isJava.setEnabled(true);
        isGo.setEnabled(true);
        isC.setEnabled(true);
        isJS.setEnabled(true);
        isCShared.setEnabled(true);
        isPython.setEnabled(true);
    }

    public void limpiarLenguajes() {
        isJava.setChecked(false);
        isGo.setChecked(false);
        isC.setChecked(false);
        isJS.setChecked(false);
        isCShared.setChecked(false);
        isPython.setChecked(false);
    }

    public void desahabilitarLenguajes(View view) {
        limpiarLenguajes();
        isJava.setEnabled(false);
        isGo.setEnabled(false);
        isC.setEnabled(false);
        isJS.setEnabled(false);
        isCShared.setEnabled(false);
        isPython.setEnabled(false);
    }



    public void habilitarLenguajes(View view) {
        isJava.setEnabled(true);
        isGo.setEnabled(true);
        isC.setEnabled(true);
        isJS.setEnabled(true);
        isCShared.setEnabled(true);
        isPython.setEnabled(true);
    }

    public void Enviar(View view) {
        if (error()) {
           Intent intent = new Intent(this, secondActivity.class);

           String msg1 = "Hola!, mi nombre es: "+nombre.getText().toString()+" "+apellido.getText().toString()+".";
           String msg2 = "Soy "+genero.getSelectedItem().toString()+", y naci en la fecha: "+nacimiento.getText().toString()+".";

           String msg3="";
           if(isLikeProgrammer.isChecked()){
                msg3= "Me gusta programar. Mis lenguajes favoritos son:";
                if(isJava.isChecked()){
                    msg3+=" Java,";
                }
                if(isPython.isChecked())
                {
                    msg3+= " Python,";
                }
                if(isJS.isChecked())
                {
                    msg3+= " JS,";
                }
                if(isGo.isChecked())
                {
                    msg3+= " Go lang,";
                }
                if(isC.isChecked())
                {
                    msg3+= " C/C++,";
                }
                if(isCShared.isChecked())
                {
                    msg3+= " C#,";
                }
               if (msg3.endsWith(",")) {
                   msg3 = msg3.substring(0, msg3.length() - 1) + ".";
               }
           }else{
               msg3= "No me gusta programar.";
           }
           intent.putExtra("texto1", msg1);
            intent.putExtra("texto2", msg2);
            intent.putExtra("texto3", msg3);
           startActivity(intent);
        }

    }


    public Boolean error() {
        if (nombre.getText().toString().equals("")) {
            Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_LONG).show();
            return false;
        }
        if (apellido.getText().toString().equals("")) {
            Toast.makeText(this, "Ingresa tu apellido", Toast.LENGTH_LONG).show();
            return false;
        }
        if (nacimiento.getText().toString().equals("")) {
            Toast.makeText(this, "Seleccione una fecha de nacimiento", Toast.LENGTH_LONG).show();
            return false;
        }
        if (isLikeProgrammer.isChecked() && !isJava.isChecked() && !isJS.isChecked() && !isC.isChecked()
                && !isPython.isChecked() && !isGo.isChecked() && !isCShared.isChecked()) {
            Toast.makeText(this, "Debe seleccionar al menos un lenguaje de programacion", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }
}