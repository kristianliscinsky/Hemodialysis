package com.example.myapplication.database.model;

public class Food {
    public static final int EDITABLE = 1;
    public static final int NON_EDITABLE = 0;
    public static final int LIQUID = 1;
    public static final int NON_LIQUID = 0;

    public static final String TABLE_NAME = "foods";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EN_NAME = "enName";
    public static final String COLUMN_PORTION = "portion";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_POTASSIUM = "potassium";
    public static final String COLUMN_PHOSPHORUS = "phosphorus";
    public static final String COLUMN_SODIUM = "sodium";
    public static final String COLUMN_WATER = "water";
    public static final String COLUMN_IS_LIQUID = "isLiquid";
    public static final String COLUMN_IS_EDITABLE = "editable";

    private int id;
    private int weight;
    private int isLiquid;
    private int isEditable;
    private int water;
    private double potassium;
    private double phosphorus;
    private double sodium;
    private String name;
    private String enName;
    private String portion;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_EN_NAME + " TEXT,"
                    + COLUMN_PORTION + " TEXT,"
                    + COLUMN_WEIGHT + " INTEGER,"
                    + COLUMN_POTASSIUM + " REAL,"
                    + COLUMN_PHOSPHORUS + " REAL,"
                    + COLUMN_SODIUM + " REAL,"
                    + COLUMN_WATER + " INTEGER,"
                    + COLUMN_IS_LIQUID + " INTEGER,"
                    + COLUMN_IS_EDITABLE + " INTEGER"
                    + ")";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public int getIsLiquid() {
        return isLiquid;
    }

    public void setIsLiquid(int isLiquid) {
        this.isLiquid = isLiquid;
    }

    public int getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(int isEditable) {
        this.isEditable = isEditable;
    }
}
