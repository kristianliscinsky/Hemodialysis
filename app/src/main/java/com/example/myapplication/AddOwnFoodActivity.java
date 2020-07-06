package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.database.model.Food;

public class AddOwnFoodActivity extends AppCompatActivity {

    private int columnWater;
    private double columnPhosphorus;
    private double columnSodium;
    private double columnPotassium;
    private String columnName;

    private Food createdFood = new Food();
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_own_food);

        db = new DatabaseHelper(this);

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.addOwnFoodActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //edittext with name of food
        final EditText foodName = findViewById(R.id.get_name_edittext);

        //edittext with water per 100g
        final EditText water = findViewById(R.id.get_water_edittext);

        //edittext with potassium per 100g
        final EditText potassium = findViewById(R.id.get_potassium_edittext);

        //edittext with phosphorus per 100g
        final EditText phosphorus = findViewById(R.id.get_phosphorus_edittext);

        //edittext with sodium per 100g
        final EditText sodium = findViewById(R.id.get_sodium_edittext);

        Button submit = findViewById(R.id.submit_button_ownFoodAc);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText can not be empty
                if (TextUtils.isEmpty(foodName.getText().toString())) {
                    foodName.setError(getResources().getText(R.string.edit_text_empty));
                } else if (TextUtils.isEmpty(water.getText().toString())) {
                    water.setError(getResources().getText(R.string.edit_text_empty));
                } else if (TextUtils.isEmpty(potassium.getText().toString()) || potassium.getText().toString().equals(".")) {
                    potassium.setError(getResources().getText(R.string.edit_text_empty));
                } else if (TextUtils.isEmpty(phosphorus.getText().toString()) || phosphorus.getText().toString().equals(".")) {
                    phosphorus.setError(getResources().getText(R.string.edit_text_empty));
                } else if (TextUtils.isEmpty(sodium.getText().toString()) || sodium.getText().toString().equals(".")) {
                    sodium.setError(getResources().getText(R.string.edit_text_empty));
                } else {
                    columnWater = Integer.parseInt(water.getText().toString());
                    columnPotassium = Double.parseDouble(potassium.getText().toString());
                    columnPhosphorus = Double.parseDouble(phosphorus.getText().toString());
                    columnSodium = Double.parseDouble(sodium.getText().toString());
                    columnName = foodName.getText().toString();

                    //Food with this name already exists
                    if (db.getFoodByName(columnName) != null) {
                        foodName.setError(getResources().getText(R.string.item_already_exists));
                    } else {
                        createdFood.setName(columnName);
                        createdFood.setEnName("N/A");
                        createdFood.setPortion("váha");
                        createdFood.setWeight(1);
                        createdFood.setPotassium(columnPotassium);
                        createdFood.setPhosphorus(columnPhosphorus);
                        createdFood.setSodium(columnSodium);
                        createdFood.setWater(columnWater);
                        createdFood.setIsEditable(Food.EDITABLE);
                        createdFood.setIsLiquid(Food.NON_LIQUID);

                        if (db.insertIntoFood(createdFood) != -1) {
                            Log.e("Added ", createdFood.getName());
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.successful_submit), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });

    }

    /**
     * Method for getting out of this activity. Method is called after click on back button
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
