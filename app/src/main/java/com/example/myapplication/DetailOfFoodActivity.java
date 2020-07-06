package com.example.myapplication;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.database.model.Food;

public class DetailOfFoodActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Food selectedFood;
    private final String EXTRA_ITEM_INFO = "item_name";
    private int is_Liquid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_food);

        db = new DatabaseHelper(this);
        selectedFood = db.getFoodByName(getStringFromPreviousActivity(savedInstanceState));
        is_Liquid = selectedFood.getIsLiquid();

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.detailOfFoodActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //textview with name of food
        final TextView foodName = findViewById(R.id.detailOfFoodAc_setName);
        foodName.setText(selectedFood.getName());

        //textview with water per 100g
        final TextView water = findViewById(R.id.detailOfFoodAc_setWater);
        water.setText(String.valueOf(Double.valueOf(selectedFood.getWater())));

        //textview with potassium per 100g
        final TextView potassium = findViewById(R.id.detailOfFoodAc_setPotassium);
        potassium.setText(String.valueOf(selectedFood.getPotassium()));

        //textview with phosphorus per 100g
        final TextView phosphorus = findViewById(R.id.detailOfFoodAc_setPhosphorus);
        phosphorus.setText(String.valueOf(selectedFood.getPhosphorus()));

        //textview with sodium per 100g
        final TextView sodium = findViewById(R.id.detailOfFoodAc_setSodium);
        sodium.setText(String.valueOf(selectedFood.getSodium()));

        //linearLayout for items with high values of K and P
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(35);
        shape.setColor(Color.parseColor("#ff9999"));

        final LinearLayout potassiumLayout = findViewById(R.id.detailOfFoodAc_potassiumTextView);
        //change layout color if item has potassium over 320mg
        if (selectedFood.getPotassium() > 320) {
            potassiumLayout.setBackground(shape);
        }

        final LinearLayout phosphorusLayout = findViewById(R.id.detailOfFoodAc_phosphorusTextView);
        //change layout color if item has phosphorus over 300mg
        if (selectedFood.getPhosphorus() > 300) {
            phosphorusLayout.setBackground(shape);
        }

        final Button deleteButton = findViewById(R.id.detailOfFoodAc_deteleItem);

        //deleting item if item is editable
        if (selectedFood.getIsEditable() == Food.NON_EDITABLE) {
            deleteButton.setVisibility(View.INVISIBLE);
        } else {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(DetailOfFoodActivity.this)
                            .setTitle(R.string.deleteDialogTitle)
                            .setMessage(R.string.deleteDialogMessage)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (db.deleteFood(selectedFood.getName()) != 0) {
                                        Toast.makeText(getApplicationContext(), R.string.successful_delete, Toast.LENGTH_SHORT).show();
                                        if (is_Liquid == Food.LIQUID) {
                                            Intent intent = new Intent(getApplicationContext(), SearchDrinkActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(), SearchFoodActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            })
                            .setNegativeButton(R.string.no, null)
                            .show();
                }
            });
        }
    }

    String getStringFromPreviousActivity(Bundle savedInstanceState) {
        String result;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                result = null;
            } else {
                result = extras.getString(EXTRA_ITEM_INFO);
            }
        } else {
            result = (String) savedInstanceState.getSerializable(EXTRA_ITEM_INFO);
        }
        return result;
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
