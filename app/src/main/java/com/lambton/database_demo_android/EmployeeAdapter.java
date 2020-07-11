package com.lambton.database_demo_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lambton.database_demo_android.model.Employee;

import java.util.Arrays;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {

    Context context;
    int layoutRes;
    List<Employee> employeeList;
    SQLiteDatabase sqLiteDatabase;

    public EmployeeAdapter(@NonNull Context context, int resource, List<Employee> employeeList, SQLiteDatabase sqLiteDatabase) {
        super(context, resource, employeeList);
        this.employeeList = employeeList;
        this.sqLiteDatabase = sqLiteDatabase;
        this.context = context;
        this.layoutRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutRes, null);
        TextView nameTV = v.findViewById(R.id.tv_name);
        TextView salaryTV = v.findViewById(R.id.tv_salary);
        TextView departmentTV = v.findViewById(R.id.tv_department);
        TextView joiningDateTV = v.findViewById(R.id.tv_joining_date);

        final Employee employee = employeeList.get(position);
        nameTV.setText(employee.getName());
        salaryTV.setText(String.valueOf(employee.getSalary()));
        departmentTV.setText(employee.getDepartment());
        joiningDateTV.setText(employee.getJoiningDate());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employee);
            }

            private void updateEmployee(Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.dialog_update_employee, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                EditText name = view.findViewById(R.id.et_name);
                EditText salary = view.findViewById(R.id.et_salary);
                Spinner department = view.findViewById(R.id.spinner_dept);

                String[] deptArray = context.getResources().getStringArray(R.array.departments);
                int position = Arrays.asList(deptArray).indexOf(employee.getDepartment());

                name.setText(employee.getName());
                salary.setText(String.valueOf(employee.getSalary()));
                department.setSelection(position);

            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);
            }

            private void deleteEmployee(final Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM employee WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{employee.getId()});
                        loadEmployees();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The employee ("+ employee.getName() +") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return v;
    }

    private void loadEmployees() {
        String sql = "SELECT * FROM employee";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        employeeList.clear();
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
        }
        notifyDataSetChanged();
    }
}
