package com.example.seg2505.StoreKeeper;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Table and columns
    public static final String TABLE_NAME = "stock";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SUBTYPE = "subtype";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_COMMENT = "comment";
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_REQUESTER_ID = "requester_id";
    public static final String COLUMN_ORDER_STATUS = "status";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE + " TEXT NOT NULL,"
                + COLUMN_SUBTYPE + " TEXT NOT NULL,"
                + COLUMN_TITLE + " TEXT NOT NULL UNIQUE,"
                + COLUMN_QUANTITY + " INTEGER NOT NULL,"
                + COLUMN_COMMENT + " TEXT,"
                + COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " ("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_REQUESTER_ID + " INTEGER NOT NULL,"
                + COLUMN_ORDER_STATUS + " TEXT NOT NULL,"
                + COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Méthode pour ajouter un élément au stock
    public boolean addStockItem(String type, String subtype, String title, int quantity, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_SUBTYPE, subtype);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_COMMENT, comment);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1; // Renvoie true si l'insertion a réussi
    }

    // Méthode pour mettre à jour un élément de stock
    public boolean updateStockItem(int id, int quantity, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_COMMENT, comment);
        values.put(COLUMN_UPDATED_AT, "datetime('now')");

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{valueOf(id)});
        db.close();
        return result > 0; // Renvoie true si la mise à jour a réussi
    }

    // Méthode pour supprimer un élément de stock
    public boolean deleteStockItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{valueOf(id)});
        db.close();
        return result > 0; // Renvoie true si la suppression a réussi
    }

    // Méthode pour récupérer tous les éléments du stock
    public Cursor getAllStockItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TITLE + " ASC");
    }

    public void addUser(String name, String email, String password, String role) {
    }

    public void deleteUser(String email) {
    }

    public boolean addOrder(int requesterId , String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_REQUESTER_ID , requesterId);
        values.put(COLUMN_ORDER_STATUS , status);
        values.put(COLUMN_CREATED_AT, "datetime('now')");
        values.put(COLUMN_UPDATED_AT, "datetime('now')");

        long result = db.insert(TABLE_ORDERS, null , values);
        db.close();
        return result != -1;
    }

    public Cursor getOrdersByRequester(int requesterId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS , null , COLUMN_REQUESTER_ID + "=?", new String[]{String.valueOf(requesterId)}, null,null,COLUMN_CREATED_AT + "DESC");
    }


    public boolean deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ORDERS, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(orderId)});
        db.close();
        return result > 0;  // Returns true if deletion was successful, otherwise false
    }

    public boolean authenticateUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email,password});

        if (cursor != null && cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }
}
