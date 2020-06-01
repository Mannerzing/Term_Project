package com.example.testbed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class List_storage extends AppCompatActivity {

    Button button;
    ListView listview;
    ArrayList<String> items;
    ArrayAdapter<String> Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_storage);

        button = findViewById(R.id.back);
        listview = findViewById(R.id.list);

        Bundle bundle = getIntent().getExtras();

        String make_us_whole = bundle.getString("address") + " " + bundle.getString("latitude") + ", " + bundle.getString("longitude");

        items = new ArrayList<String>();
        items.add(make_us_whole);

        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);

        listview.setAdapter(Adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        Adapter.notifyDataSetChanged();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
