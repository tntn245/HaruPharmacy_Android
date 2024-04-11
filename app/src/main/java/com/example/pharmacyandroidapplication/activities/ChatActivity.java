package com.example.pharmacyandroidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ChatAdapter;
import com.example.pharmacyandroidapplication.models.ChatMessage;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView chatRV = findViewById(R.id.chatRecyclerView);
        ArrayList<ChatMessage> ChatMessageArrayList = new ArrayList<ChatMessage>();

        ChatMessageArrayList.add(new ChatMessage("1", "2", "Hello", "10/4/2024"));

        ChatAdapter adapter = new ChatAdapter(ChatMessageArrayList);
        chatRV.setAdapter(adapter);
    }
}