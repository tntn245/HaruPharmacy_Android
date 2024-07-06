package com.example.pharmacyandroidapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.pharmacyandroidapplication.activities.customer.CustomerHomepageActivity;
import com.example.pharmacyandroidapplication.adapters.ChatAdapter;
import com.example.pharmacyandroidapplication.databinding.ActivityChatBinding;
import com.example.pharmacyandroidapplication.models.Account;
import com.example.pharmacyandroidapplication.models.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    String receiverImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance().getReference();
        senderID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setListeners();
        loadReceiverDetails();
        listenMessages();
    }

    private void init() {
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                ChatActivity.this,
                chatMessages,
                receiverImg,
//                BitmapFactory.decodeResource(getResources(), R.drawable.ic_ava),
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
                } else {
                    Toast.makeText(ChatActivity.this, "Lỗi khi thêm dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("messchat", "sendMessage: ");
        binding.inputMessage.setText(null);
    }

    private void listenMessages() {
        DatabaseReference chatMessagesRef = FirebaseDatabase.getInstance().getReference().child("chatMessages");

        // Lắng nghe các tin nhắn có senderID là người dùng hiện tại và receiverID là người nhận
        chatMessagesRef.orderByChild("senderID").equalTo(senderID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d("messchat", "onChildAddedSend: ");
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.senderId = dataSnapshot.child("senderID").getValue(String.class);
                chatMessage.receiverId = dataSnapshot.child("receiverID").getValue(String.class);
                chatMessage.message = dataSnapshot.child("message").getValue(String.class);
                chatMessage.dateTime = dataSnapshot.child("timestamp").getValue(String.class);

                String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
                try {
                    chatMessage.dateObject = dateFormat.parse(chatMessage.dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (chatMessage.receiverId.equals(receiverID)) {
                    chatMessages.add(chatMessage);
                    Collections.sort(chatMessages, (msg1, msg2) -> msg1.dateObject.compareTo(msg2.dateObject));
                    chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                    binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                }
                binding.chatRecyclerView.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                ChatMessage updatedChatMessage = new ChatMessage();
                updatedChatMessage.senderId = dataSnapshot.child("senderID").getValue(String.class);
                updatedChatMessage.receiverId = dataSnapshot.child("receiverID").getValue(String.class);
                updatedChatMessage.message = dataSnapshot.child("message").getValue(String.class);
                updatedChatMessage.dateTime = dataSnapshot.child("timestamp").getValue(String.class);
                Log.d("messchat", "onChildChangedSend: " + updatedChatMessage.message);

                String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
                try {
                    updatedChatMessage.dateObject = dateFormat.parse(updatedChatMessage.dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < chatMessages.size(); i++) {
                    if (chatMessages.get(i).getSenderId().equals(updatedChatMessage.getSenderId())) {
                        chatMessages.set(i, updatedChatMessage);
                        chatAdapter.notifyItemChanged(i);
                        break;
                    }
                }
                // Sắp xếp lại danh sách chatMessages theo dateObject
                Collections.sort(chatMessages, (msg1, msg2) -> msg1.dateObject.compareTo(msg2.dateObject));
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ChatMessage removedChatMessage = dataSnapshot.getValue(ChatMessage.class);
                if (removedChatMessage != null) {
                    for (int i = 0; i < chatMessages.size(); i++) {
                        if (chatMessages.get(i).getSenderId().equals(removedChatMessage.getSenderId())) {
                            chatMessages.remove(i);
                            chatAdapter.notifyItemRemoved(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Xử lý khi child bị di chuyển
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatActivity", "Firebase database error: " + databaseError.getMessage());
            }
        });





        // Lắng nghe các tin nhắn có receiverID là người dùng hiện tại và senderID là người nhận
        chatMessagesRef.orderByChild("receiverID").equalTo(senderID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d("messchat", "onChildAddedRv: ");
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.senderId = dataSnapshot.child("senderID").getValue(String.class);
                chatMessage.receiverId = dataSnapshot.child("receiverID").getValue(String.class);
                chatMessage.message = dataSnapshot.child("message").getValue(String.class);
                chatMessage.dateTime = dataSnapshot.child("timestamp").getValue(String.class);

                String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
                try {
                    chatMessage.dateObject = dateFormat.parse(chatMessage.dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (chatMessage.senderId.equals(receiverID)) {
                    chatMessages.add(chatMessage);
                    Collections.sort(chatMessages, (msg1, msg2) -> msg1.dateObject.compareTo(msg2.dateObject));
                    chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                    binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                }
                binding.chatRecyclerView.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                ChatMessage updatedChatMessage = new ChatMessage();
                updatedChatMessage.senderId = dataSnapshot.child("senderID").getValue(String.class);
                updatedChatMessage.receiverId = dataSnapshot.child("receiverID").getValue(String.class);
                updatedChatMessage.message = dataSnapshot.child("message").getValue(String.class);
                updatedChatMessage.dateTime = dataSnapshot.child("timestamp").getValue(String.class);
                Log.d("messchat", "onChildChangedRv: " + updatedChatMessage.message);

                String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
                try {
                    updatedChatMessage.dateObject = dateFormat.parse(updatedChatMessage.dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < chatMessages.size(); i++) {
                    if (chatMessages.get(i).senderId.equals(updatedChatMessage.senderId)) {
                        Log.d("messchat", chatMessages.get(i).getMessage());
                        chatMessages.set(i, updatedChatMessage);
                        chatAdapter.notifyItemChanged(i);
                        break;
                    }
                }
                // Sắp xếp lại danh sách chatMessages theo dateObject
                Collections.sort(chatMessages, (msg1, msg2) -> msg1.dateObject.compareTo(msg2.dateObject));
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ChatMessage removedChatMessage = dataSnapshot.getValue(ChatMessage.class);
                if (removedChatMessage != null) {
                    for (int i = 0; i < chatMessages.size(); i++) {
                        if (chatMessages.get(i).getSenderId().equals(removedChatMessage.getSenderId())) {
                            chatMessages.remove(i);
                            chatAdapter.notifyItemRemoved(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Xử lý khi child bị di chuyển
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatActivity", "Firebase database error: " + databaseError.getMessage());
            }
        });
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, CustomerHomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }

    private void loadReceiverDetails() {
        receiverID = getIntent().getExtras().getString("userID");

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts").child(receiverID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("username").getValue(String.class);
                    receiverImg = dataSnapshot.child("img").getValue(String.class);
                    String userRole = dataSnapshot.child("role").getValue(String.class);
                    String userSex = dataSnapshot.child("sex").getValue(String.class);
                    String userBirthDay = dataSnapshot.child("birth_day").getValue(String.class);

                    receiverUser = new Account(receiverID, receiverImg, userRole, userName, userSex, userBirthDay);
                    TextView receiverName = findViewById(R.id.textName);
                    receiverName.setText(receiverUser.getUsername());
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
            chatMessages.clear();
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
                chatAdapter.notifyDataSetChanged();
            } else {
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