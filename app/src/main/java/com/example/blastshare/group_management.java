package com.example.blastshare;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


public class group_management extends AppCompatActivity {

    private ArrayList<String> memberList;
    private ArrayAdapter<String> adapter;
    private int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvMembers = findViewById(R.id.lv_members);
        Button btnRemoveMember = findViewById(R.id.btn_remove_member);

        // For demo purposes, populate with dummy data
        memberList = new ArrayList<>();
        memberList.add("Member 1");
        memberList.add("Member 2");
        memberList.add("Member 3");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, memberList);
        lvMembers.setAdapter(adapter);
        lvMembers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvMembers.setOnItemClickListener((parent, view, position, id) -> selectedPosition = position);

        btnRemoveMember.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                memberList.remove(selectedPosition);
                adapter.notifyDataSetChanged();
                selectedPosition = -1;
            }
        });
    }

}