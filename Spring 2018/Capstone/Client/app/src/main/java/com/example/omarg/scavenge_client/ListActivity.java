package com.example.omarg.scavenge_client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Database updated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                EditText text = findViewById(R.id.editText);
                create(text.getText().toString());

            }
        });
    }
public void create(String URL)
{

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ListView list = findViewById(R.id.Dynamic);
    String[] ListElements = new String[]{
    };

    ArrayList<Building> buildList = new Handler().main(URL);

    if(buildList != null) {
        final List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (ListActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        list.setAdapter(adapter);
        for (int i = 0; i < buildList.size(); i++) {
            ListElementsArrayList.add("id : " + buildList.get(i).get_id() + "\nbuilding id : " + buildList.get(i).getBuilding_id() + "\nDescription : " +
                    buildList.get(i).getDescription() + "\nLocation type : " + buildList.get(i).getLocation_type() + "\nRoom Number : " + buildList.get(i).getRoom());
        }

        adapter.notifyDataSetChanged();
    }
    else
    {

    }
}
}
