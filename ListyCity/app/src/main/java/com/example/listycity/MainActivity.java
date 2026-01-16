package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    //references
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    //store which user last tapped, will be used for delete button
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //references for ui elements from activity_main
        cityList = findViewById(R.id.city_list);

        // string array of cities
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        //array into arraylist and add cities
        dataList = new ArrayList<>();

        dataList.addAll(Arrays.asList(cities));


        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);


        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
        }

        );
        //references for ui elements from activity_main
        Button addButton = findViewById(R.id.add_city_button);
        Button deleteButton = findViewById(R.id.delete_city_button);

        //add city button
        //dialog text box to add city
        //on confirm, add city to data list
        //tell adapter the data changed and ui needs to change
        addButton.setOnClickListener(v -> {
            EditText input = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setView(input)
                    .setPositiveButton("CONFIRM", (dialog, which) -> {
                        String city = input.getText().toString();

                        if (!city.isEmpty()) { //ignore empty inputs
                            dataList.add(city); //add city to data list and refresh ListView
                            cityAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("CANCEL", null) //cancel button if user does not want to add city
                    .create()
                    .show();
        });

        //delete city button
        //delete only if user has selected a city
        //remove city from data list
        //tell adapter the data changed and ui needs to change
        //removes highlight after deletion
        deleteButton.setOnClickListener(v -> {
            if (selectedIndex >= 0 && selectedIndex < dataList.size()) { //do nothing if no valid selection
                dataList.remove(selectedIndex);
                cityAdapter.notifyDataSetChanged(); //clear selection state and reset index
                selectedIndex = -1;
            }
        });
    }
}