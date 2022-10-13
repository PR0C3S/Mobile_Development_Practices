package com.pucmm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String message1 = getIntent().getStringExtra("texto1");
        String message2 = getIntent().getStringExtra("texto2");
        String message3 = getIntent().getStringExtra("texto3");

        // Capture the layout's TextView and set the string as its text
        TextView textView1 = findViewById(R.id.txtTexto1);
        textView1.setText(message1);

        TextView textView2 = findViewById(R.id.txtTexto2);
        textView2.setText(message2);

        TextView textView3 = findViewById(R.id.txtTexto3);
        textView3.setText(message3);
    }
}