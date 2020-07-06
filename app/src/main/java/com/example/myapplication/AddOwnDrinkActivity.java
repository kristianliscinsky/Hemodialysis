package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.database.model.Food;

public class AddOwnDrinkActivity extends AppCompatActivity {

    private int columnWater;
    private double columnPhosphorus;
    private double columnSodium;
    private double columnPotassium;
    private String columnName;

    private Food createdDrink = new Food();
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_own_drink);

        db = new DatabaseHelper(this);

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.addOwnDrinkActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //edittext with name of food
        final EditText drinkName = findViewById(R.id.get_name_edittext);

        //edittext with water per 100g
        final EditText water = findViewById(R.id.get_water_edittext);

        //edittext with potassium per 100g
        final EditText potassium = findViewById(R.id.get_potassium_edittext);

        //edittext with phosphorus per 100g
        final EditText phosphorus = findViewById(R.id.get_phosphorus_edittext);

        //edittext with sodium per 100g
        final EditText sodium = findViewById(R.id.get_sodium_edittext);

        Button submit = findViewById(R.id.submit_button_ownDrinkAc);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText can not be empty
                if (TextUtils.isEmpty(drinkName.getText().toString())) {
                    drinkName.setError(getResources().getText(R.string.edit_text_empty));
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
                    columnName = drinkName.getText().toString();

                    //Food with this name already exists
                    if (db.getFoodByName(columnName) != null) {
                        drinkName.setError(getResources().getText(R.string.item_already_exists));
                    } else {
                        createdDrink.setName(columnName);
                        createdDrink.setEnName("N/A");
                        createdDrink.setPortion("objem");
                        createdDrink.setWeight(1);
                        createdDrink.setPotassium(columnPotassium);
                        createdDrink.setPhosphorus(columnPhosphorus);
                        createdDrink.setSodium(columnSodium);
                        createdDrink.setWater(columnWater);
                        createdDrink.setIsEditable(Food.EDITABLE);
                        createdDrink.setIsLiquid(Food.LIQUID);

                        if (db.insertIntoFood(createdDrink) != -1) {
                            Log.e("Added ", createdDrink.getName());
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
