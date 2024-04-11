package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.ChatMessage;
import com.example.pharmacyandroidapplication.models.Product;

import java.util.ArrayList;
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<ChatMessage> messageArrayList;

    public ChatAdapter(ArrayList<ChatMessage> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_sent_message, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = messageArrayList.get(position);
        holder.textMessage.setText(message.getMessage());
        holder.textDateTime.setText(message.getDateTime());
    }

    public int getItemCount() {
        return messageArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;
        TextView textDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }
    }
}
