package com.example.pharmacyandroidapplication.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.activities.ChatActivity;
import com.example.pharmacyandroidapplication.adapters.AdminChatAdapter;
import com.example.pharmacyandroidapplication.databinding.ActivityChatHomepageBinding;
import com.example.pharmacyandroidapplication.listeners.UserListener;
import com.example.pharmacyandroidapplication.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatHomepageActivity extends AppCompatActivity implements UserListener {
    private ActivityChatHomepageBinding binding;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        getUsers();
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getUsers() {
        loading(true);

        String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("accounts");
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("chatMessages");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loading(false);
                users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey();
                    String role = snapshot.child("role").getValue(String.class);
                    if (!currentUserId.equals(userId) && role != null && role.equals("customer")) {
                        User user = new User();
                        user.name = snapshot.child("username").getValue(String.class);
                        user.image = snapshot.child("img").getValue(String.class);
                        user.id = snapshot.child("id").getValue(String.class);
                        users.add(user);
                    }
                }

                if (!users.isEmpty()) {
                    AdminChatAdapter usersAdapter = new AdminChatAdapter(users, ChatHomepageActivity.this);
                    binding.usersRecyclerView.setAdapter(usersAdapter);
                } else {
                    showErrorMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading(false);
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("userID", user.id);
        startActivity(intent);
        finish();
    }
}