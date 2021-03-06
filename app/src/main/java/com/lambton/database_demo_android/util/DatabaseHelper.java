package com.lambton.database_demo_android.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "employee_databse";
    private static final String TABLE_NAME = "employee";
    public static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name" ;
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_JOIN_DATE = "joining_date";
    private static final String COLUMN_SALARY = "salary";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME + " VARCHAR(20) NOT NULL, "+
                COLUMN_DEPARTMENT +" VARCHAR(20) NOT NULL, "+
                COLUMN_JOIN_DATE +" DATETIME NOT NULL, "+
                COLUMN_SALARY +" DOUBLE NOT NULL); ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table and then create it
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);

    }
    //insert

    /**
     * add employee - insert employee into employee table
     * @param name
     * @param department
     * @param joiningDate
     * @param salary
     * @return boolean value - true (inserted) false (not inserted)
     */
    public boolean addEmployee(String name, String department, String joiningDate, double salary){

        // we need a writable instance of SQLite database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //we need to define a content value to insert into our database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPARTMENT, department);
        contentValues.put(COLUMN_JOIN_DATE, joiningDate);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        // the insert method associated to SQLite database instance returns -1 if nothing is inserted
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    /**
     * Query database - select all employees
     * @return cursor
     */
    public Cursor getAllEmployees() {
        // we need a readable instance of SQLite database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
    }

    /**
     * update employee in datbase
     * @param id
     * @param name
     * @param department
     * @param salary
     * @return boolean value - true (updated) false (not updated)
     */
    public boolean updateEmployee(int id, String name, String department, double salary){
        // we need a writable instance of SQLite database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //we need to define a content value to insert into our database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPARTMENT, department);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        //update method associated to SQLite database instance returns number of row affected
        return sqLiteDatabase.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    /**
     * delete employee from database
     * @param id
     * @return boolean value - true (if successfully deleted)
     */

    public boolean deleteEmployee(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //the delete method associate to SQLite database instance returns the number of rows affected
        return sqLiteDatabase.delete(TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

}
