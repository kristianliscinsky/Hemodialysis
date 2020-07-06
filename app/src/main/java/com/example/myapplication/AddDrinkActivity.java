package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.database.model.Food;
import com.example.myapplication.database.model.UserStatistics;
import com.example.myapplication.enums.LiquidStandards;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddDrinkActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private final String EXTRA_DRINK_INFO = "drink_name";
    private final String EXTRA_ITEM_INFO = "item_name";


    private Calendar myCalendar = Calendar.getInstance();
    private Date currentDate = myCalendar.getTime();
    private DatabaseHelper db;
    private Food selectedDrink;

    //attributes for UserStatistics object
    private String columnName;
    private String columnEnName;
    private String columnPortionSize;
    private int columnPortionNumber;

    private int columnAmount;
    private int columnTotalAmount;
    private double columnPotassium;
    private double columnPhosphorus;
    private double columnSodium;
    private int columnWater;
    private String columnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(currentDate);

    private int totalWater;
    private double totalPotassium;
    private double totalSodium;
    private double totalPhosphorus;

    UserStatistics userStatistics = new UserStatistics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        //get instance of selected food
        db = new DatabaseHelper(this);
        selectedDrink = db.getFoodByName(getStringFromPreviousActivity(savedInstanceState));
        String predefinedItem = selectedDrink.getPortion() + " (" + selectedDrink.getWeight() + " ml)";

        columnName = selectedDrink.getName();
        columnEnName = selectedDrink.getEnName();
        columnPortionSize = selectedDrink.getPortion();
        columnPortionNumber = 1;//default
        columnAmount = selectedDrink.getWeight();
        columnPotassium = selectedDrink.getPotassium();
        columnPhosphorus = selectedDrink.getPhosphorus();
        columnSodium = selectedDrink.getSodium();
        columnWater = selectedDrink.getWater();

        /*  WORKING WITH UI */

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.addDrinkActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //textview with food name
        TextView drinkNameTextview = findViewById(R.id.drink_name_textview);
        drinkNameTextview.setText(getStringFromPreviousActivity(savedInstanceState));
        //show details
        drinkNameTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailOfFoodActivity.class);
                intent.putExtra(EXTRA_ITEM_INFO, selectedDrink.getName());
                startActivity(intent);
            }
        });

        //editText with dosage information
        final EditText quantityEditText = findViewById(R.id.dosage_drink);

        //quantity textView
        final TextView quantityTextView = findViewById(R.id.quantity_textview_drink);
        quantityTextView.setText(predefinedItem);
        quantityTextView.setOnClickListener(popupMenuListener);

        //calendar textView
        final TextView dateTextView = findViewById(R.id.date_drink);
        //set current date to textview
        setDateViewValue();
        dateTextView.setOnClickListener(showDataPicker);

        //time textView
        final TextView timeTextView = findViewById(R.id.time_drink);
        //set current time to textview
        setTimeViewValue();
        timeTextView.setOnClickListener(showTimePicker);

        //submit button
        final Button submitButton = findViewById(R.id.submit_button_drink);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText can not be empty
                if (TextUtils.isEmpty(quantityEditText.getText().toString())) {
                    quantityEditText.setError(getResources().getText(R.string.edit_text_empty));
                } else {
                    columnPortionNumber = Integer.parseInt(quantityEditText.getText().toString());
                    columnTotalAmount = columnPortionNumber * columnAmount;

                    totalWater = columnTotalAmount * columnWater / 100;
                    totalPhosphorus = columnTotalAmount * columnPhosphorus / 100;
                    totalPotassium = columnTotalAmount * columnPotassium / 100;
                    totalSodium = columnTotalAmount * columnSodium / 100;

                    fillUserStatistics();
                    if (db.insertIntoUserStatistics(userStatistics) != -1) {
                        Log.d("Added ",+ userStatistics.getTotalAmount() + " ml/g " + userStatistics.getName());
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.successful_submit), Toast.LENGTH_SHORT).show();
                        //finish();
                        Intent returnToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        returnToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(returnToMainActivity);
                    }
                }
            }
        });
    }

    //called after new date is chosen
    View.OnClickListener showDataPicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(AddDrinkActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
    };

    private final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            currentDate = myCalendar.getTime();

            columnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(currentDate);
            //only for update UI element
            setDateViewValue();
        }
    };

    /**
     * Method only for set date in textview
     */
    private void setDateViewValue() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        TextView textView = findViewById(R.id.date_drink);
        textView.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    View.OnClickListener showTimePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TimePickerDialog(AddDrinkActivity.this, time,
                    myCalendar.get(Calendar.HOUR),
                    myCalendar.get(Calendar.MINUTE),
                    true)
                    .show();
        }
    };

    private final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            currentDate = myCalendar.getTime();

            columnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(currentDate);
            //only for udpate UI
            setTimeViewValue();
        }
    };

    /**
     * Method only for set time in textview
     */
    private void setTimeViewValue() {
        String myFormat = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        TextView textView = findViewById(R.id.time_drink);
        textView.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    View.OnClickListener popupMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenuForLiquid(v);
        }
    };

    public void showPopupMenuForLiquid(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v, Gravity.NO_GRAVITY, R.attr.actionOverflowMenuStyle, 0);
        popupMenu.setOnMenuItemClickListener(this);

        String predefinedItem = selectedDrink.getPortion() + " (" + selectedDrink.getWeight() + " ml)";

        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, predefinedItem);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, R.string.ml_1);
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, R.string.cup);
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, R.string.cup_small);
        popupMenu.getMenu().add(Menu.NONE, 4, Menu.NONE, R.string.mug_large);
        popupMenu.getMenu().add(Menu.NONE, 5, Menu.NONE, R.string.mug_small);
        popupMenu.getMenu().add(Menu.NONE, 6, Menu.NONE, R.string.glass_small);
        popupMenu.getMenu().add(Menu.NONE, 7, Menu.NONE, R.string.glass_large);
        popupMenu.getMenu().add(Menu.NONE, 8, Menu.NONE, R.string.glass_whiskey);
        popupMenu.getMenu().add(Menu.NONE, 9, Menu.NONE, R.string.glass_wine);
        popupMenu.getMenu().add(Menu.NONE, 10, Menu.NONE, R.string.shot_small);
        popupMenu.getMenu().add(Menu.NONE, 11, Menu.NONE, R.string.shot_large);
        popupMenu.getMenu().add(Menu.NONE, 12, Menu.NONE, R.string.bowl_small);
        popupMenu.getMenu().add(Menu.NONE, 13, Menu.NONE, R.string.bowl_large);
        popupMenu.getMenu().add(Menu.NONE, 14, Menu.NONE, R.string.plate);
        popupMenu.getMenu().add(Menu.NONE, 15, Menu.NONE, R.string.krigl);
        popupMenu.getMenu().add(Menu.NONE, 16, Menu.NONE, R.string.sip);

        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView textView = (TextView) findViewById(R.id.quantity_textview_drink);

        switch (item.getItemId()) {
            case 0:
                columnPortionSize = selectedDrink.getPortion();
                textView.setText(columnPortionSize);
                columnAmount = selectedDrink.getWeight();
                return true;

            case 1:
                columnPortionSize = getResources().getString(R.string.ml_1);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.MILILITER.getLiquidStandards();
                return true;

            case 2:
                columnPortionSize = getResources().getString(R.string.cup);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.CUP.getLiquidStandards();
                return true;

            case 3:
                columnPortionSize = getResources().getString(R.string.cup_small);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.CUP_SMALL.getLiquidStandards();
                return true;

            case 4:
                columnPortionSize = getResources().getString(R.string.mug_large);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.MUG_LARGE.getLiquidStandards();
                return true;

            case 5:
                columnPortionSize = getResources().getString(R.string.mug_small);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.MUG_SMALL.getLiquidStandards();
                return true;

            case 6:
                columnPortionSize = getResources().getString(R.string.glass_small);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.GLASS_SMALL.getLiquidStandards();
                return true;

            case 7:
                columnPortionSize = getResources().getString(R.string.glass_large);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.GLASS_LARGE.getLiquidStandards();
                return true;

            case 8:
                columnPortionSize = getResources().getString(R.string.glass_whiskey);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.GLASS_WHISKEY.getLiquidStandards();
                return true;

            case 9:
                columnPortionSize = getResources().getString(R.string.glass_wine);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.GLASS_WINE.getLiquidStandards();
                return true;

            case 10:
                columnPortionSize = getResources().getString(R.string.shot_small);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.SHOT_SMALL.getLiquidStandards();
                return true;

            case 11:
                columnPortionSize = getResources().getString(R.string.shot_large);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.SHOT_LARGE.getLiquidStandards();
                return true;

            case 12:
                columnPortionSize = getResources().getString(R.string.bowl_small);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.BOWL_SMALL.getLiquidStandards();
                return true;

            case 13:
                columnPortionSize = getResources().getString(R.string.bowl_large);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.BOWL_LARGE.getLiquidStandards();
                return true;

            case 14:
                columnPortionSize = getResources().getString(R.string.plate);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.PLATE.getLiquidStandards();
                return true;

            case 15:
                columnPortionSize = getResources().getString(R.string.krigl);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.KRIGL.getLiquidStandards();
                return true;

            case 16:
                columnPortionSize = getResources().getString(R.string.sip);
                textView.setText(columnPortionSize);
                columnAmount = LiquidStandards.SIP.getLiquidStandards();
                return true;

            default:
                return false;
        }
    }

    void fillUserStatistics (){
        userStatistics.setName(columnName);
        userStatistics.setEnName(columnEnName);
        userStatistics.setPortionSize(columnPortionSize);
        userStatistics.setPortionNumber(columnPortionNumber);
        userStatistics.setAmount(columnAmount);
        userStatistics.setTotalAmount(columnTotalAmount);

        userStatistics.setPotassium(totalPotassium);
        userStatistics.setPhosphorus(totalPhosphorus);
        userStatistics.setSodium(totalSodium);
        userStatistics.setWater(totalWater);
        userStatistics.setDate(columnDate);
    }

    /**
     * Method for getting out of this activity. Method is called after click on back button
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    String getStringFromPreviousActivity(Bundle savedInstanceState) {
        String result;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                result = null;
            } else {
                result = extras.getString(EXTRA_DRINK_INFO);
            }
        } else {
            result = (String) savedInstanceState.getSerializable(EXTRA_DRINK_INFO);
        }
        return result;
    }
}
