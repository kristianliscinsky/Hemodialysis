package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.database.model.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchDrinkActivity extends AppCompatActivity {

    private final String EXTRA_DRINK_INFO = "drink_name";
    private final String EXTRA_ITEM_INFO = "item_name";
    private final int DELETE = 0;
    private final int DETAILS = 1;
    private DatabaseHelper db;
    private ListView listView;

    private ArrayList<Food> allOnlyDrinksEntries;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drink);
        db = new DatabaseHelper(getApplicationContext());

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.searchDrinkActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Button for adding new drink
        FloatingActionButton addNewFood = findViewById(R.id.add_own_drink);
        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddOwnDrinkActivity.class);
                startActivity(intent);
            }
        });

        //listView
        listView = findViewById(R.id.listDrink);
        listView.setOnItemClickListener(onDrinkClick);
        registerForContextMenu(listView);

        setUpAdapter();
    }

    /**
     * Update adapter after adding own new food item
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateAdapter();
    }

    /**
     *  Start new funcionality, when on food is clicked
     */
    AdapterView.OnItemClickListener onDrinkClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setBackgroundColor(Color.GRAY);
            String drinkName = parent.getItemAtPosition(position).toString();

            Log.e("Food selected", "Selected item: " + drinkName);
            Log.d("Food selected", "Selected item: " + drinkName);
            Toast.makeText(getApplicationContext(), "Selected item: " + drinkName, Toast.LENGTH_SHORT).show();

            //start new activity
            Intent intent = new Intent(view.getContext(), AddDrinkActivity.class);
            intent.putExtra(EXTRA_DRINK_INFO, drinkName);
            startActivity(intent);
            view.setBackgroundColor(Color.TRANSPARENT);
            //finish();
        }
    };

    /**
     *  Searching and filtering funcionality
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_searchview, menu);
        //searchview
        MenuItem searchItem = menu.findItem(R.id.item_searchview);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        ArrayList<String> var = getOnlyDrinksFiltered(db, newText);

                        //searching item does not exist
                        if (var == null) {
                            arrayList.clear();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), R.string.not_existing_items, Toast.LENGTH_SHORT).show();
                        } else {
                            arrayList.clear();
                            arrayList.addAll(var);
                            adapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                }
        );

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Get the list item position
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        String listItem = adapter.getItem(position);
        Food foodItem = db.getFoodByName(listItem);

        //if item is editable
        if (foodItem.getIsEditable() == Food.EDITABLE) {
            menu.setHeaderTitle(R.string.title_context_menu);
            menu.add(Menu.NONE, DELETE, Menu.NONE, R.string.delete);
            menu.add(Menu.NONE, DETAILS, Menu.NONE, R.string.showDetails);
        } else {
            menu.setHeaderTitle(R.string.title_context_menu);
            menu.add(Menu.NONE, DETAILS, Menu.NONE, R.string.showDetails);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     *  Implements functionality of editing (delete, update) items
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Object obj = listView.getItemAtPosition(info.position);
        final String nameOfItem = obj.toString();

        switch (item.getItemId()) {
            case DELETE:
                //dialog for deleting item
                new AlertDialog.Builder(this)
                        .setTitle(R.string.deleteDialogTitle)
                        .setMessage(R.string.deleteDialogMessage)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (db.deleteFood(nameOfItem) != 0) {
                                    Toast.makeText(getApplicationContext(), R.string.successful_delete, Toast.LENGTH_SHORT).show();
                                    updateAdapter();
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
                return true;

            case DETAILS:
                Intent intent = new Intent(getApplicationContext(), DetailOfFoodActivity.class);
                intent.putExtra(EXTRA_ITEM_INFO, nameOfItem);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }

    /**
     * @param db Databasehelper object
     * @return only drinks, whithout foods
     */
    ArrayList<Food> getOnlyDrinks(DatabaseHelper db) {
        ArrayList<Food> allItems = db.getAllFoodEntries();
        ArrayList<Food> onlyDrinks = new ArrayList<>();

        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getIsLiquid() == Food.LIQUID) {
                onlyDrinks.add(allItems.get(i));
            }
        }
        return onlyDrinks;
    }

    /**
     * @param db        databaseHelper.
     * @param regexName filterable name of drink.
     * @return if regexName is not in database, null is returned.
     */
    ArrayList<String> getOnlyDrinksFiltered(DatabaseHelper db, String regexName) {
        ArrayList<String> onlyDrinks = new ArrayList<>();
        if (db.getFilteredFoodByName(regexName) != null) {
            ArrayList<Food> allItems = db.getFilteredFoodByName(regexName);

            for (int i = 0; i < allItems.size(); i++) {
                if (allItems.get(i).getIsLiquid() == Food.LIQUID) {
                    onlyDrinks.add(allItems.get(i).getName());
                }
            }
        } else {
            onlyDrinks = null;
        }
        return onlyDrinks;
    }

    /**
     * Create adapter
     */
    private void setUpAdapter() {
        allOnlyDrinksEntries = getOnlyDrinks(db);
        for (int i = 0; i < allOnlyDrinksEntries.size(); i++) {
            arrayList.add(allOnlyDrinksEntries.get(i).getName());
        }

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview_adapter_for_searching, R.id.searchviev_item_cell, arrayList);
        listView.setAdapter(adapter);
    }

    /**
     * Updating adapter
     */
    private void updateAdapter() {
        arrayList.clear();
        allOnlyDrinksEntries = getOnlyDrinks(db);

        for (int i = 0; i < allOnlyDrinksEntries.size(); i++) {
            arrayList.add(allOnlyDrinksEntries.get(i).getName());
        }
        adapter.notifyDataSetChanged();
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
