package com.pucmm.practicando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,new Fragment1()).commit();
    }

    public void openFirstFrame(View view)
    {
        replaceFragment(new Fragment1());
    }

    public  void openSecondFrame(View vie)
    {
        replaceFragment(new Fragment2());
    }

    private void replaceFragment(Fragment fragment) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}