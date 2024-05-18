package com.example.pharmacyandroidapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    Account receiverUser;
    List<ChatMessage> chatMessages;
    ChatAdapter chatAdapter;
//    PreferenceManager preferenceManager;
//    FirebaseFirestore database;
    String senderID;
    String receiverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        senderID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
        init();

//        RecyclerView chatRV = findViewById(R.id.chatRecyclerView);
//        ArrayList<ChatMessage> ChatMessageArrayList = new ArrayList<ChatMessage>();
//
//        ChatMessageArrayList.add(new ChatMessage("1", "2", "Hello", "10/4/2024"));
//
//        ChatAdapter adapter = new ChatAdapter(ChatMessageArrayList);
//        chatRV.setAdapter(adapter);
    }
    private void init() {
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
//                getBitmapFromEncodedString(receiverUser.getImg()),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_ava),
                senderID);
        binding.chatRecyclerView.setAdapter(chatAdapter);
    }
    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory. decodeByteArray(bytes, 0, bytes.length);
    }
//    private void sendMessage() {
//        HashMap<String, Object> message = new HashMap<>();
//        message.put("senderID", senderID);
//        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
//        message.put(Constants.KEY_MESSAGE, binding.inputMessage.getText().toString());
//        message.put(Constants.KEY_TIMESTAMP, new Date());
//        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
//        binding.inputMessage.setText(null);
//    }
    private void setListeners() {
        binding.imageBack.setOnClickListener(v->onBackPressed());
    }
    private void loadReceiverDetails() {
        receiverID = "zDVjeEon70POnmT25BdJbEmB5jG3";
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts").child(receiverID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("username").getValue(String.class);
                    String userImg = dataSnapshot.child("img").getValue(String.class);
                    String userRole = dataSnapshot.child("role").getValue(String.class);
                    String userSex = dataSnapshot.child("sex").getValue(String.class);
                    String userBirthDay = dataSnapshot.child("birth_day").getValue(String.class);

                    receiverUser = new Account(receiverID, "", userRole, "", "", "");
                    TextView receiverName = findViewById(R.id.textName);
                    receiverName.setText(receiverUser.getRole());

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