package com.example.pharmacyandroidapplication.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatMessage> chatMessages;
    private final Bitmap receiverProfileImage;
    private final String senderId;
    public static final int VIEW_TYPE_SENT=1;
    public static final int VIEW_TYPE_RECEIVED=2;

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_sent_message, parent, false);
        return new ViewHolder(itemView);
//        if (viewType == VIEW_TYPE_SENT) {
//            return new SentMessageViewHolder(
//                    ItemContainerSentMessaageBinding.inflate(
//                            LayoutInflater. from(parent.getContext()),
//                            parent,
//                            false
//                    )
//            );
//        } else {
//            return new ReceivedMessageViewHolder(
//                    ItemContainerReceivedMessageBinding.inflate(
//                            LayoutInflater. from(parent.getContext()),
//                            parent,
//                            false
//                    )
//            );
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        holder.textMessage.setText(message.getMessage());
        holder.textDateTime.setText(message.getDateTime());
    }

    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
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

//    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
//        private final ItemContainerSentMessaageBinding binding;
//
//        SentMessageViewHolder(ItemContainerSentMessaageBinding itemContainerSentMessaageBinding) {
//            super(itemContainerSentMessaageBinding.getRoot());
//            binding = itemContainerSentMessaageBinding;
//
//        }
//
//        void setData(ChatMessage chatMessage) {
//            binding.textMessage.setText(chatMessage.message);
//            binding.textDateTime.setText(chatMessage.dateTime);
//        }
//    }
}
