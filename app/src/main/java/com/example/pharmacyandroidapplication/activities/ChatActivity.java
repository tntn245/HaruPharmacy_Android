package com.example.pharmacyandroidapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    Account receiverUser;
    List<ChatMessage> chatMessages;
    ChatAdapter chatAdapter;
    //    PreferenceManager preferenceManager;
    DatabaseReference database;
    String senderID;
    String receiverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance().getReference();
        senderID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
        init();
        listenMessages();
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
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put("senderID", senderID);
        message.put("receiverID", receiverID);
        message.put("message", binding.inputMessage.getText().toString());
        message.put("timestamp", (new Date()).toString());

//         Thêm dữ liệu vào Realtime Database
        database.child("chatMessages").push().setValue(message, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    String messageId = ref.getKey();
                    Toast.makeText(ChatActivity.this, messageId, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Lỗi khi thêm dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ChatMessage chatMessage = new ChatMessage(senderID, receiverID, binding.inputMessage.getText().toString(), (new Date()).toString());
        chatMessages.add(chatMessage);
        chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
        binding.inputMessage.setText(null);
    }

    private void listenMessages() {
        database.child("chatMessages").orderByChild("senderID").equalTo(senderID).addListenerForSingleValueEvent(eventListener);
        database.child("chatMessages").orderByChild("senderID").equalTo(receiverID).addListenerForSingleValueEvent(eventListener);
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
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

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }
    private final ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.senderId = snapshot.child("senderID").getValue(String.class);
                chatMessage.receiverId = snapshot.child("receiverID").getValue(String.class);
                chatMessage.message = snapshot.child("message").getValue(String.class);
                chatMessage.dateTime = snapshot.child("timestamp").getValue(String.class);

                String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
                try {
                    chatMessage.dateObject = dateFormat.parse(chatMessage.dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                chatMessages.add(chatMessage);
            }

            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));

            if (chatMessages.size() == 0) {
                Log.d("AAAAA00000", "Message");
                chatAdapter.notifyDataSetChanged();
            } else {
                Log.d("AAAAA11111", String.valueOf(chatMessages.size()));
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                binding.chatRecyclerView.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Xử lý khi có lỗi xảy ra
        }
    };
}