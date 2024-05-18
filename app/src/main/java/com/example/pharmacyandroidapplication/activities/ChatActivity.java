package com.example.pharmacyandroidapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.adapters.ChatAdapter;
import com.example.pharmacyandroidapplication.databinding.ActivityChatBinding;
import com.example.pharmacyandroidapplication.models.Account;
import com.example.pharmacyandroidapplication.models.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();

//        RecyclerView chatRV = findViewById(R.id.chatRecyclerView);
//        ArrayList<ChatMessage> ChatMessageArrayList = new ArrayList<ChatMessage>();
//
//        ChatMessageArrayList.add(new ChatMessage("1", "2", "Hello", "10/4/2024"));
//
//        ChatAdapter adapter = new ChatAdapter(ChatMessageArrayList);
//        chatRV.setAdapter(adapter);
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v->onBackPressed());
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory. decodeByteArray(bytes, 0, bytes.length);
    }
    private void loadReceiverDetails() {
//        receiverUser = (Account) getIntent().getSerializableExtra("user");
//        binding.textName.setText(receiverUser.getUsername());

        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts").child(userID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    String userName = dataSnapshot.child("username").getValue(String.class);
//                    TextView username = findViewById(R.id.textName);
//                    username.setText(userName);


                } else {
                    // Không có dữ liệu về người dùng
                    Toast.makeText(ChatActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
                Toast.makeText(ChatActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}