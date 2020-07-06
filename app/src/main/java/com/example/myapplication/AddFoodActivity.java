package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class AddFoodActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private final String EXTRA_FOOD_INFO = "food_name";
    private final String EXTRA_ITEM_INFO = "item_name";

    private Calendar myCalendar = Calendar.getInstance();
    private Date currentDate = myCalendar.getTime();
    private DatabaseHelper db;
    private Food selectedFood;

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
    private String columnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(currentDate);

    private int totalWater;
    private double totalPotassium;
    private double totalSodium;
    private double totalPhosphorus;

    UserStatistics userStatistics = new UserStatistics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //get instance of selected food
        db = new DatabaseHelper(this);
        selectedFood = db.getFoodByName(getStringFromPreviousActivity(savedInstanceState));
        String predefinedItem = selectedFood.getPortion() + " (" + selectedFood.getWeight() + " g)";

        columnName = selectedFood.getName();
        columnEnName = selectedFood.getEnName();
        columnPortionSize = selectedFood.getPortion();
        columnPortionNumber = 1;//default
        columnAmount = selectedFood.getWeight();
        columnPotassium = selectedFood.getPotassium();
        columnPhosphorus = selectedFood.getPhosphorus();
        columnSodium = selectedFood.getSodium();
        columnWater = selectedFood.getWater();


        /*  WORKING WITH UI */

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.addFoodActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //textview with food name
        TextView foodNameTextview = findViewById(R.id.food_name_textview);
        foodNameTextview.setText(getStringFromPreviousActivity(savedInstanceState));
        //show details
        foodNameTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailOfFoodActivity.class);
                intent.putExtra(EXTRA_ITEM_INFO, selectedFood.getName());
                startActivity(intent);
            }
        });

        //editText with dosage information
        final EditText quantityEditText = findViewById(R.id.dosage_food);

        //quantity textView
        final TextView quantityTextView = findViewById(R.id.quantity_textview_food);
        quantityTextView.setText(predefinedItem);
        quantityTextView.setOnClickListener(popupMenuListener);

        //calendar textView
        final TextView dateTextView = findViewById(R.id.date_food);
        //set current time to textview
        setCalendarViewValue();
        dateTextView.setOnClickListener(showDataPicker);

        //time textView
        final TextView timeTextView = findViewById(R.id.time_food);
        //set current time to textview
        setTimeViewValue();
        timeTextView.setOnClickListener(showTimePicker);

        //submit button
        final Button submitButton = findViewById(R.id.submit_button_food);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext can not be empty
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
            new DatePickerDialog(AddFoodActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

            columnDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(currentDate);
            //only for update UI element
            setCalendarViewValue();
        }
    };

    private void setCalendarViewValue() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        TextView textView = findViewById(R.id.date_food);
        textView.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    View.OnClickListener showTimePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TimePickerDialog(AddFoodActivity.this, time,
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
        TextView textView = findViewById(R.id.time_food);
        textView.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    View.OnClickListener popupMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenuForNonLiquid(v);
        }
    };

    public void showPopupMenuForNonLiquid(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v, Gravity.NO_GRAVITY, R.attr.actionOverflowMenuStyle, 0);
        popupMenu.setOnMenuItemClickListener(this);

        String predefinedItem = selectedFood.getPortion() + " (" + selectedFood.getWeight() + " g)";

        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, predefinedItem);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "1 g");
        popupMenu.getMenu().add(Menu.NONE, 100, Menu.NONE, "100 g");

        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView textView = (TextView) findViewById(R.id.quantity_textview_food);

        switch (item.getItemId()) {
            case 0:
                columnPortionSize = selectedFood.getPortion();
                textView.setText(columnPortionSize);
                columnAmount = selectedFood.getWeight();
                return true;

            case 1:
                columnPortionSize = "1 g";
                textView.setText(columnPortionSize);
                columnAmount = 1;
                return true;

            case 100:
                columnPortionSize = "100 g";
                textView.setText(columnPortionSize);
                columnAmount = 100;
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
                result = extras.getString(EXTRA_FOOD_INFO);
            }
        } else {
            result = (String) savedInstanceState.getSerializable(EXTRA_FOOD_INFO);
        }

        return result;
    }
}
