package com.example.gogreengoogle.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gogreengoogle.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchPageActivity extends AppCompatActivity {

    ArrayList<String> TextList = new ArrayList<>(1);
    ArrayList<String> UrlList = new ArrayList<>(1);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        Intent receiveIntent = getIntent();
        TextList = receiveIntent.getStringArrayListExtra("TextArrayList");
        UrlList = receiveIntent.getStringArrayListExtra("UrlArrayList");

        recyclerView = findViewById(R.id.recyclerview);
        ListAdapter adapter = new ListAdapter(this, TextList, UrlList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
