package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.database.model.Food;
import com.example.myapplication.database.model.UserStatistics;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "Food.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Food table
        db.execSQL(Food.CREATE_TABLE);
        db.execSQL(UserStatistics.CREATE_TABLE);

        /*Inserting initial data into database Food*/
        ContentValues values = new ContentValues();
        values.put(Food.COLUMN_NAME, "Acqua Panna");
        values.put(Food.COLUMN_EN_NAME, "Acqua Panna");
        values.put(Food.COLUMN_PORTION, "objem");
        values.put(Food.COLUMN_WEIGHT, "1");
        values.put(Food.COLUMN_POTASSIUM, "0.9");
        values.put(Food.COLUMN_PHOSPHORUS, "0");
        values.put(Food.COLUMN_SODIUM, "0.65");
        values.put(Food.COLUMN_WATER, "100");
        values.put(Food.COLUMN_IS_LIQUID , "1");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);

        values.clear();
        values.put(Food.COLUMN_NAME, "Ananas");
        values.put(Food.COLUMN_EN_NAME, "Pineapple");
        values.put(Food.COLUMN_PORTION, "váha");
        values.put(Food.COLUMN_WEIGHT, "1");
        values.put(Food.COLUMN_POTASSIUM, "250");
        values.put(Food.COLUMN_PHOSPHORUS, "11");
        values.put(Food.COLUMN_SODIUM, "2");
        values.put(Food.COLUMN_WATER, "87");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Ananas, kompot");
        values.put(Food.COLUMN_EN_NAME, "Pineapple, canned");
        values.put(Food.COLUMN_PORTION, "1 plechovka");
        values.put(Food.COLUMN_WEIGHT, "350");
        values.put(Food.COLUMN_POTASSIUM, "105");
        values.put(Food.COLUMN_PHOSPHORUS, "5");
        values.put(Food.COLUMN_SODIUM, "0.1");
        values.put(Food.COLUMN_WATER, "86");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Ananasová šťava, balená");
        values.put(Food.COLUMN_EN_NAME, "Pineapple juice,canned");
        values.put(Food.COLUMN_PORTION, "objem");
        values.put(Food.COLUMN_WEIGHT, "1");
        values.put(Food.COLUMN_POTASSIUM, "140");
        values.put(Food.COLUMN_PHOSPHORUS, "10");
        values.put(Food.COLUMN_SODIUM, "1");
        values.put(Food.COLUMN_WATER, "100");
        values.put(Food.COLUMN_IS_LIQUID , "1");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Angrešt");
        values.put(Food.COLUMN_EN_NAME, "Gooseberries");
        values.put(Food.COLUMN_PORTION, "1 plod");
        values.put(Food.COLUMN_WEIGHT, "7");
        values.put(Food.COLUMN_POTASSIUM, "190");
        values.put(Food.COLUMN_PHOSPHORUS, "30");
        values.put(Food.COLUMN_SODIUM, "1");
        values.put(Food.COLUMN_WATER, "90");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Arašídové máslo");
        values.put(Food.COLUMN_EN_NAME, "Peanut butter");
        values.put(Food.COLUMN_PORTION, "váha");
        values.put(Food.COLUMN_WEIGHT, "1");
        values.put(Food.COLUMN_POTASSIUM, "700");
        values.put(Food.COLUMN_PHOSPHORUS, "330");
        values.put(Food.COLUMN_SODIUM, "350");
        values.put(Food.COLUMN_WATER, "2");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Artyčok, nažka, vařený");
        values.put(Food.COLUMN_EN_NAME, "Artichoke, globe, boiled");
        values.put(Food.COLUMN_PORTION, "1 nažka");
        values.put(Food.COLUMN_WEIGHT, "250");
        values.put(Food.COLUMN_POTASSIUM, "140");
        values.put(Food.COLUMN_PHOSPHORUS, "20");
        values.put(Food.COLUMN_SODIUM, "6");
        values.put(Food.COLUMN_WATER, "84");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Avokádo");
        values.put(Food.COLUMN_EN_NAME, "Avocado");
        values.put(Food.COLUMN_PORTION, "1 plod");
        values.put(Food.COLUMN_WEIGHT, "80");
        values.put(Food.COLUMN_POTASSIUM, "400");
        values.put(Food.COLUMN_PHOSPHORUS, "40");
        values.put(Food.COLUMN_SODIUM, "7");
        values.put(Food.COLUMN_WATER, "74");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Bábovka");
        values.put(Food.COLUMN_EN_NAME, "Sponge pudding, steamed");
        values.put(Food.COLUMN_PORTION, "váha");
        values.put(Food.COLUMN_WEIGHT, "1");
        values.put(Food.COLUMN_POTASSIUM, "90");
        values.put(Food.COLUMN_PHOSPHORUS, "190");
        values.put(Food.COLUMN_SODIUM, "310");
        values.put(Food.COLUMN_WATER, "0");
        values.put(Food.COLUMN_IS_LIQUID , "1");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Bageta");
        values.put(Food.COLUMN_EN_NAME, "Baguette");
        values.put(Food.COLUMN_PORTION, "1 kus");
        values.put(Food.COLUMN_WEIGHT, "500");
        values.put(Food.COLUMN_POTASSIUM, "120");
        values.put(Food.COLUMN_PHOSPHORUS, "110");
        values.put(Food.COLUMN_SODIUM, "570");
        values.put(Food.COLUMN_WATER, "0");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "0");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

        values.put(Food.COLUMN_NAME, "Baklažán, pečený");
        values.put(Food.COLUMN_EN_NAME, "Eggplant, baked");
        values.put(Food.COLUMN_PORTION, "1 plod");
        values.put(Food.COLUMN_WEIGHT, "350");
        values.put(Food.COLUMN_POTASSIUM, "190");
        values.put(Food.COLUMN_PHOSPHORUS, "25");
        values.put(Food.COLUMN_SODIUM, "5");
        values.put(Food.COLUMN_WATER, "92");
        values.put(Food.COLUMN_IS_LIQUID , "0");
        values.put(Food.COLUMN_IS_EDITABLE , "1");
        db.insert(Food.TABLE_NAME, null, values);
        values.clear();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete and create table again
        db.execSQL(Food.DELETE_TABLE);
        db.execSQL(UserStatistics.DELETE_TABLE);
        onCreate(db);
    }

    public ArrayList<Food> getFilteredFoodByName(String regexFoodName) {
        ArrayList<Food> obtainedFoods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query( Food.TABLE_NAME, null,
                Food.COLUMN_NAME + " LIKE '%" + regexFoodName + "%'", null, null, null, null);

        //move to first entry
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                Food item = new Food();
                item.setId(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(Food.COLUMN_NAME)));
                item.setEnName(cursor.getString(cursor.getColumnIndex(Food.COLUMN_EN_NAME)));
                item.setPortion(cursor.getString(cursor.getColumnIndex(Food.COLUMN_PORTION)));
                item.setWeight(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_WEIGHT)));
                item.setPotassium(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_POTASSIUM)));
                item.setPhosphorus(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_PHOSPHORUS)));
                item.setSodium(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_SODIUM)));
                item.setWater(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_WATER)));
                item.setIsLiquid(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_IS_LIQUID)));
                item.setIsEditable(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_IS_EDITABLE)));
                obtainedFoods.add(item);
            } while (cursor.moveToNext());
        } else {
            obtainedFoods = null;
        }

        db.close();
        if (cursor != null) {
            cursor.close();
        }

        return obtainedFoods;
    }

    public Food getFoodByName(String foodName) {
        Food obtainedFood = new Food();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query( Food.TABLE_NAME, null,
                Food.COLUMN_NAME + "='" + foodName + "'", null, null, null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            obtainedFood.setId(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_ID)));
            obtainedFood.setName(cursor.getString(cursor.getColumnIndex(Food.COLUMN_NAME)));
            obtainedFood.setEnName(cursor.getString(cursor.getColumnIndex(Food.COLUMN_EN_NAME)));
            obtainedFood.setPortion(cursor.getString(cursor.getColumnIndex(Food.COLUMN_PORTION)));
            obtainedFood.setWeight(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_WEIGHT)));
            obtainedFood.setPotassium(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_POTASSIUM)));
            obtainedFood.setPhosphorus(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_PHOSPHORUS)));
            obtainedFood.setSodium(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_SODIUM)));
            obtainedFood.setWater(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_WATER)));
            obtainedFood.setIsLiquid(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_IS_LIQUID)));
            obtainedFood.setIsEditable(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_IS_EDITABLE)));
        } else {
            obtainedFood = null;
        }

        db.close();
        if (cursor != null) {
            cursor.close();
        }

        return obtainedFood;
    }


    public ArrayList<Food> getAllFoodEntries() {
        ArrayList<Food> obtainedFoods = new ArrayList<>();
        String sSQL = "SELECT * FROM " + Food.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        //move to first entry
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                Food item = new Food();
                item.setId(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(Food.COLUMN_NAME)));
                item.setEnName(cursor.getString(cursor.getColumnIndex(Food.COLUMN_EN_NAME)));
                item.setPortion(cursor.getString(cursor.getColumnIndex(Food.COLUMN_PORTION)));
                item.setWeight(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_WEIGHT)));
                item.setPotassium(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_POTASSIUM)));
                item.setPhosphorus(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_PHOSPHORUS)));
                item.setSodium(cursor.getDouble(cursor.getColumnIndex(Food.COLUMN_SODIUM)));
                item.setWater(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_WATER)));
                item.setIsLiquid(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_IS_LIQUID)));
                item.setIsEditable(cursor.getInt(cursor.getColumnIndex(Food.COLUMN_IS_EDITABLE)));
                obtainedFoods.add(item);
            } while (cursor.moveToNext());
        }

        db.close();
        if (cursor != null) {
            cursor.close();
        }

        return obtainedFoods;
    }

    /**
     *
     * @param foodItem instance of UserStatistics class
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertIntoUserStatistics(UserStatistics foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserStatistics.COLUMN_NAME, foodItem.getName());
        values.put(UserStatistics.COLUMN_EN_NAME, foodItem.getEnName());
        values.put(UserStatistics.COLUMN_PORTION_SIZE, foodItem.getPortionSize());
        values.put(UserStatistics.COLUMN_PORTION_NUMBER, foodItem.getPortionNumber());
        values.put(UserStatistics.COLUMN_AMOUNT, foodItem.getAmount());
        values.put(UserStatistics.COLUMN_TOTAL_AMOUNT, foodItem.getTotalAmount());
        values.put(UserStatistics.COLUMN_POTASSIUM, foodItem.getPotassium());
        values.put(UserStatistics.COLUMN_PHOSPHORUS, foodItem.getPhosphorus());
        values.put(UserStatistics.COLUMN_SODIUM, foodItem.getSodium());
        values.put(UserStatistics.COLUMN_WATER, foodItem.getWater());
        values.put(UserStatistics.COLUMN_DATE, foodItem.getDate());

        long id = db.insert(UserStatistics.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    /**
     *
     * @param foodItem instance of Food class
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertIntoFood(Food foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Food.COLUMN_NAME, foodItem.getName());
        values.put(Food.COLUMN_EN_NAME, foodItem.getEnName());
        values.put(Food.COLUMN_PORTION, foodItem.getPortion());
        values.put(Food.COLUMN_WEIGHT, foodItem.getWeight());
        values.put(Food.COLUMN_POTASSIUM, foodItem.getPotassium());
        values.put(Food.COLUMN_PHOSPHORUS, foodItem.getPhosphorus());
        values.put(Food.COLUMN_SODIUM, foodItem.getSodium());
        values.put(Food.COLUMN_WATER, foodItem.getWater());
        values.put(Food.COLUMN_IS_LIQUID, foodItem.getIsLiquid());
        values.put(Food.COLUMN_IS_EDITABLE, foodItem.getIsEditable());

        long id = db.insert(Food.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    /**
     *
     * @return 0 if nothing was deleted
     */
    public int deleteFood(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = db.delete(Food.TABLE_NAME, Food.COLUMN_NAME + " = ?", new String[] {name});
        db.close();
        return id;
    }

    public ArrayList<UserStatistics> getAllUserStatisticsEntries() {
        ArrayList<UserStatistics> obtainedStats = new ArrayList<>();
        String sSQL = "SELECT * FROM " + UserStatistics.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        //move to first entry
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                UserStatistics item = new UserStatistics();

                item.setId(cursor.getInt(cursor.getColumnIndex(UserStatistics.COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(UserStatistics.COLUMN_NAME)));
                item.setEnName(cursor.getString(cursor.getColumnIndex(UserStatistics.COLUMN_EN_NAME)));
                item.setPortionSize(cursor.getString(cursor.getColumnIndex(UserStatistics.COLUMN_PORTION_SIZE)));
                item.setPortionNumber(cursor.getInt(cursor.getColumnIndex(UserStatistics.COLUMN_PORTION_NUMBER)));
                item.setAmount(cursor.getInt(cursor.getColumnIndex(UserStatistics.COLUMN_AMOUNT)));
                item.setTotalAmount(cursor.getInt(cursor.getColumnIndex(UserStatistics.COLUMN_TOTAL_AMOUNT)));
                item.setPotassium(cursor.getDouble(cursor.getColumnIndex(UserStatistics.COLUMN_POTASSIUM)));
                item.setPhosphorus(cursor.getDouble(cursor.getColumnIndex(UserStatistics.COLUMN_PHOSPHORUS)));
                item.setSodium(cursor.getDouble(cursor.getColumnIndex(UserStatistics.COLUMN_SODIUM)));
                item.setWater(cursor.getInt(cursor.getColumnIndex(UserStatistics.COLUMN_WATER)));
                item.setDate(cursor.getString(cursor.getColumnIndex(UserStatistics.COLUMN_DATE)));
                obtainedStats.add(item);
            } while (cursor.moveToNext());
        }

        db.close();
        if (cursor != null) {
            cursor.close();
        }

        return obtainedStats;
    }
}
