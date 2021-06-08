package com.example.postpc_2021_ex8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    RootsApplication app = RootsApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define of the add calculation fragment
        FragmentContainerView calcFrag = findViewById(R.id.calcFrag);
        AddCalculationFragment acFrag = new AddCalculationFragment();
        getSupportFragmentManager().beginTransaction().replace(calcFrag.getId(), acFrag).commit();

        EditText numberView = findViewById(R.id.numberView);
        Button calcButton = findViewById(R.id.calcButton);

    }
}