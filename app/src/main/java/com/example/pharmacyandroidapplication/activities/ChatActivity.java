package com.example.pharmacyandroidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ChatAdapter;
import com.example.pharmacyandroidapplication.databinding.ActivityChatBinding;
import com.example.pharmacyandroidapplication.models.Account;
import com.example.pharmacyandroidapplication.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private Account receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
//    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        binding = ActivityChatBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        setListeners();
//        loadReceiverDetails();

        RecyclerView chatRV = findViewById(R.id.chatRecyclerView);
        ArrayList<ChatMessage> ChatMessageArrayList = new ArrayList<ChatMessage>();

        ChatMessageArrayList.add(new ChatMessage("1", "2", "Hello", "10/4/2024"));

        ChatAdapter adapter = new ChatAdapter(ChatMessageArrayList);
        chatRV.setAdapter(adapter);
    }
    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory. decodeByteArray(bytes, 0, bytes.length);
    }
    private void loadReceiverDetails() {
        receiverUser = (Account) getIntent().getSerializableExtra("user");
        binding.textName.setText(receiverUser.getUsername());
    }
}