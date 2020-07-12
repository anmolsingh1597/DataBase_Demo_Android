package com.lambton.database_demo_android.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "department")
    private String department;

    @NonNull
    @ColumnInfo(name = "joiningDate")
    private String joiningDate;


    @ColumnInfo(name = "salary")
    private double salary;

    public Employee(@NonNull String name, @NonNull String department, @NonNull String joiningDate, double salary) {
        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    /**
     * setId() is mandatory
     * @param id
     */

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    public void setDepartment(@NonNull String department) {
        this.department = department;
    }

    @NonNull
    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(@NonNull String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
