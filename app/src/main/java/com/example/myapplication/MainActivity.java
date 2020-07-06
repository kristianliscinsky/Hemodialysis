package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.example.myapplication.calendar.CalendarHelper;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.database.model.Food;
import com.example.myapplication.database.model.UserStatistics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
PopupMenu.OnMenuItemClickListener/*, SharedPreferences.OnSharedPreferenceChangeListener */{

    private DatabaseHelper db;

    /*
    * Tyka sa to len menu, nema nic spolocne s navigationView
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
        //this is default
        //return super.onCreateOptionsMenu(menu);
    }

    /*
    * Pouzivane pri navigationview
    * */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
        }

        return true;
    }

    /*
    * Pouzivane pri navigationview
    * */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_add_food, popupMenu.getMenu());
        setForceShowIcon(popupMenu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.food:
                Intent intentFood = new Intent(this, SearchFoodActivity.class);
                startActivity(intentFood);
                return true;
            case R.id.drink:
                Intent intentWater = new Intent(this, SearchDrinkActivity.class);
                startActivity(intentWater);
                return true;
            case R.id.meal:
                Intent intentMeal = new Intent(this, AddMealActivity.class);
                startActivity(intentMeal);
                return true;
            default:
                return false;
        }
    }

    View.OnClickListener addFoodListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
            //Intent intent = new Intent(v.getContext(), OverviewOfEatenFoodsActivity.class);
            //startActivity(intent);
            //Toast.makeText(getApplicationContext(), "kokotko", Toast.LENGTH_SHORT).show();
        }
    };

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //NavigationView button
        DrawerLayout drawerLayout = findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDraw, R.string.closeNavDraw);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Button for adding new food
        FloatingActionButton addNewFood = findViewById(R.id.add_food);
        addNewFood.setOnClickListener(addFoodListener);

        /*
        SharedPreferences pref = getApplicationContext().getSharedPreferences("signature", 0); // 0 - for private mode
        String getSP = pref.getString("signature", null);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        sharedPreferences.getString("height", "neni zadana vyska");
        Toast.makeText(this, sharedPreferences.getString("height", "neni zadana vyska"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, sharedPreferences.getString("signature", "napicu"), Toast.LENGTH_SHORT).show();
        /*
    }
/*
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Toast.makeText(this, sharedPreferences.getString("signature", "napicu signature"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, sharedPreferences.getString("height", "napicu vyska"), Toast.LENGTH_SHORT).show();
*/    }

    public void getDates() {
        String preferenceName = getResources().getString(R.string.hemodialysis1_day);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String getSP = pref.getString("height", "9999999");
        Toast.makeText(this, getSP + "---" + preferenceName, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getDates();
        CalendarHelper c = new CalendarHelper(this);
        c.findPrevAndNextHD();
        /*
        SharedPreferences pref = getApplicationContext().getSharedPreferences("signature", 0); // 0 - for private mode
        String getSP = pref.getString("signature", null);*/
        //Toast.makeText(this, getSP, Toast.LENGTH_SHORT).show();
    }
}
