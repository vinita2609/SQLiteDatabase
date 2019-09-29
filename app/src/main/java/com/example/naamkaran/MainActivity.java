package com.example.naamkaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.naamkaran.Interface.NameDataService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerviewAdapter nameAdaptor;
    NameDataService nameDataService;
    private SearchView searchView;
    Toolbar toolbar;
    // Context context=this.getActivity();
    RecyclerviewAdapter adapter;
    private List<NameDisplay> nameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*recyclerView = findViewById(R.id.fav_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            dbManager = new DatabaseHelper(MainActivity.this);
            dbManager.open();
            nameList = dbManager.getFavourites();
        }catch (ArrayIndexOutOfBoundsException e){

        }
        adapter = new RecyclerviewAdapter(nameList,MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }
}
