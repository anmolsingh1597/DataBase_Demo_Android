package com.lambton.database_demo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.lambton.database_demo_android.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    List<Employee> employeeList;
    ListView employeesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        employeesListView =  findViewById(R.id.lv_employee);

        employeeList = new ArrayList<>();

        sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);
        loadEmployees();
    }

    private void loadEmployees() {

        String sql = "SELECT * FROM employee";

    }
}