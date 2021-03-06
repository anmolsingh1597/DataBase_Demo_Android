package com.lambton.database_demo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lambton.database_demo_android.room.Employee;
import com.lambton.database_demo_android.room.EmployeeRoomDb;
import com.lambton.database_demo_android.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Database name
   /* public static final String DATABASE_NAME = "myDatabase";
    SQLiteDatabase sqLiteDatabase;*/
    //SQLite openHelper
    DatabaseHelper sqLiteDatabase;

    private EmployeeRoomDb employeeRoomDb;

    EditText etName, etSalary;
    Spinner spinnerDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etSalary = findViewById(R.id.et_salary);
        spinnerDepartment = findViewById(R.id.spinner_dept);

        findViewById(R.id.btn_add_employee).setOnClickListener(this);
        findViewById(R.id.tv_view_employees).setOnClickListener(this);

        // initialize our database
     /*   sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        createTable();*/

        // initializing  the instance of sqlITE openhelper class
//        sqLiteDatabase = new DatabaseHelper(this);

        //Room Database
        employeeRoomDb = EmployeeRoomDb.getINSTANCE(this);
    }

    /*
        private void createTable() {
            String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, "+
                    "name VARCHAR(20) NOT NULL, " +
                    "department VARCHAR(20) NOT NULL, " +
                    "joining_date DATETIME NOT NULL, " +
                    "salary DOUBLE NOT NULL);";

            sqLiteDatabase.execSQL(sql);
        }
    */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add_employee:
                addEmployee();
                break;
            case R.id.tv_view_employees:
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }

    }

    private void addEmployee() {
        String name = etName.getText().toString().trim();
        String salary = etSalary.getText().toString().trim();
        String department = spinnerDepartment.getSelectedItem().toString();

        //getting current date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = simpleDateFormat.format(cal.getTime());

        if (name.isEmpty()) {
            etName.setError("Name field cannot be empty");
            etName.requestFocus();
            return;
        }
        if (salary.isEmpty()) {
            etSalary.setError("Name field cannot be empty");
            etSalary.requestFocus();
            return;
        }
/*
        String sql = "INSERT INTO employee (name, department, joining_date, salary)" +
                "VALUES (?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql, new String[]{name, department, joiningDate, salary});
        */

// insert employee into database table with help of database helper class
      /*  if(sqLiteDatabase.addEmployee(name, department, joiningDate, Double.valueOf(salary))){

        }else{
            Toast.makeText(this, "Employee is not inserted", Toast.LENGTH_SHORT).show();
        }
*/
        //insert into room db
        Employee employee = new Employee(name, department, joiningDate, Double.parseDouble(salary));
        employeeRoomDb.employeeDao().insertEmployee(employee);
        Toast.makeText(this, "Employee is inserted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        etName.setText("");
        etSalary.setText("");
        spinnerDepartment.setSelection(0, true);

        etSalary.clearFocus();
        etName.requestFocus();
    }
}