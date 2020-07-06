package com.example.myapplication.database.model;

public class UserStatistics {
    public static final String TABLE_NAME = "userStatistics";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EN_NAME = "enName";
    public static final String COLUMN_PORTION_SIZE = "portionSize";
    public static final String COLUMN_PORTION_NUMBER = "portionNumber";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TOTAL_AMOUNT = "totalAmount";
    public static final String COLUMN_POTASSIUM = "potassium";
    public static final String COLUMN_PHOSPHORUS = "phosphorus";
    public static final String COLUMN_SODIUM = "sodium";
    public static final String COLUMN_WATER = "water";
    public static final String COLUMN_DATE = "date";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_EN_NAME + " TEXT,"
                    + COLUMN_PORTION_SIZE + " TEXT,"
                    + COLUMN_PORTION_NUMBER + " INTEGER,"
                    + COLUMN_AMOUNT + " INTEGER,"
                    + COLUMN_TOTAL_AMOUNT + " INTEGER,"
                    + COLUMN_POTASSIUM + " REAL,"
                    + COLUMN_PHOSPHORUS + " REAL,"
                    + COLUMN_SODIUM + " REAL,"
                    + COLUMN_WATER + " INTEGER,"
                    + COLUMN_DATE + " TEXT"
                    + ")";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private int id;
    private int amount;
    private int totalAmount;
    private int portionNumber;
    private int water;
    private double potassium;
    private double phosphorus;
    private double sodium;
    private String name;
    private String enName;
    private String portionSize;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(double phosphorus) {
        this.phosphorus = phosphorus;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(String portionSize) {
        this.portionSize = portionSize;
    }

    public int getPortionNumber() {
        return portionNumber;
    }

    public void setPortionNumber(int portionNumber) {
        this.portionNumber = portionNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
