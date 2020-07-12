package com.lambton.database_demo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.lambton.database_demo_android.room.Employee;
import com.lambton.database_demo_android.room.EmployeeRoomDb;
import com.lambton.database_demo_android.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

//    SQLiteDatabase sqLiteDatabase;

    // instance of databaseOpenHelper class
    DatabaseHelper sqLiteDatabase;

    // Room Db instance
    private EmployeeRoomDb employeeRoomDb;

    List<Employee> employeeList;
    ListView employeesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        employeesListView =  findViewById(R.id.lv_employee);

        employeeList = new ArrayList<>();

       // sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);

//        sqLiteDatabase = new DatabaseHelper(this);

        employeeRoomDb = EmployeeRoomDb.getINSTANCE(this);
        loadEmployees();
    }

    private void loadEmployees() {

//        String sql = "SELECT * FROM employee";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
      /*  Cursor cursor = sqLiteDatabase.getAllEmployees();
        if(cursor.moveToFirst()){
            do{
                // create an employee instance
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }*/

      employeeList = employeeRoomDb.employeeDao().getAllEmployees();

        // create an adapter to display the employees
      /*  EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,
                R.layout.list_layout_employee,
                employeeList,
                sqLiteDatabase);
        employeesListView.setAdapter(employeeAdapter);*/

      EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,R.layout.list_layout_employee,
              employeeList);
      employeesListView.setAdapter(employeeAdapter);

    }
}