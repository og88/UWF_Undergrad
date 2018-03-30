package com.example.omarg.scavenge_client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        create("building/004");

    }
public void create(String URL)
{

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ListView list = findViewById(R.id.Dynamic);
    String[] ListElements = new String[]{

    };

    ArrayList<Building> buildList = new Handler().main(URL);

    final List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));


    final ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (ListActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

    list.setAdapter(adapter);
    for (int i = 0; i < buildList.size(); i++) {
        ListElementsArrayList.add(buildList.get(i).get_id());
    }

    adapter.notifyDataSetChanged();

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    });
}
}
