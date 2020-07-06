package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class OverviewOfEatenFoodsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_of_eaten_foods);
        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.overviewOfEatenFoodsActivity);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     * Method for getting out of this activity. Method is called after click on back button
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
