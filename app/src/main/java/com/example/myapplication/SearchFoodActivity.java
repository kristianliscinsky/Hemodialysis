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

public class SearchFoodActivity extends AppCompatActivity {

    private final String EXTRA_FOOD_INFO = "food_name";
    private final String EXTRA_ITEM_INFO = "item_name";
    private final int DELETE = 0;
    private final int DETAILS = 1;
    private DatabaseHelper db;
    private ListView listView;

    private ArrayList<Food> allOnlyFoodsEntries;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        db = new DatabaseHelper(getApplicationContext());

        //Toolbar settings
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.searchFoodActivity);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Button for adding new food
        FloatingActionButton addNewFood = findViewById(R.id.add_own_food);
        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddOwnFoodActivity.class);
                startActivity(intent);
            }
        });

        //listView
        listView = findViewById(R.id.listFood);
        listView.setOnItemClickListener(onFoodClick);
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
    AdapterView.OnItemClickListener onFoodClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setBackgroundColor(Color.GRAY);
            String foodName = parent.getItemAtPosition(position).toString();

            Log.d("Food selected", "Selected item: " + foodName);
            Toast.makeText(getApplicationContext(), "Selected item: " + foodName, Toast.LENGTH_SHORT).show();

            //start new activity
            Intent intent = new Intent(view.getContext(), AddFoodActivity.class);
            intent.putExtra(EXTRA_FOOD_INFO, foodName);
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
                        ArrayList<String> var = getOnlyFoodsFiltered(db, newText);

                        //searching item does not exist
                        if (var == null) {
                            arrayList.clear();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), R.string.not_existing_items, Toast.LENGTH_SHORT).show();
                        } else {
                            arrayList.clear();
                            for (int i = 0; i < var.size(); i++) {
                                arrayList.add(var.get(i));
                            }
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
     * @return only foods, whithout drinks
     */
    ArrayList<Food> getOnlyFoods(DatabaseHelper db) {
        ArrayList<Food> allItems = db.getAllFoodEntries();
        ArrayList<Food> onlyFoods = new ArrayList<>();

        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getIsLiquid() == Food.NON_LIQUID) {
                onlyFoods.add(allItems.get(i));
            }
        }
        return onlyFoods;
    }

    /**
     * @param db        databaseHelper.
     * @param regexName filterable name of food.
     * @return if regexName is not in database, null is returned.
     */
    ArrayList<String> getOnlyFoodsFiltered(DatabaseHelper db, String regexName) {
        ArrayList<String> onlyFoods = new ArrayList<>();
        if (db.getFilteredFoodByName(regexName) != null) {
            ArrayList<Food> allItems = db.getFilteredFoodByName(regexName);

            for (int i = 0; i < allItems.size(); i++) {
                if (allItems.get(i).getIsLiquid() == Food.NON_LIQUID) {
                    onlyFoods.add(allItems.get(i).getName());
                }
            }
        } else {
            onlyFoods = null;
        }
        return onlyFoods;
    }

    /**
     * Create adapter
     */
    private void setUpAdapter() {
        allOnlyFoodsEntries = getOnlyFoods(db);
        for (int i = 0; i < allOnlyFoodsEntries.size(); i++) {
            arrayList.add(allOnlyFoodsEntries.get(i).getName());
        }

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview_adapter_for_searching, R.id.searchviev_item_cell, arrayList);
        listView.setAdapter(adapter);
    }

    /**
     * Updating adapter
     */
    private void updateAdapter() {
        arrayList.clear();
        allOnlyFoodsEntries = getOnlyFoods(db);

        for (int i = 0; i < allOnlyFoodsEntries.size(); i++) {
            arrayList.add(allOnlyFoodsEntries.get(i).getName());
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
