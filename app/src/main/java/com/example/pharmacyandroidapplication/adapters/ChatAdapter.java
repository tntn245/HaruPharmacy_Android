package com.example.pharmacyandroidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.databinding.ItemContainerReceivedMessageBinding;
import com.example.pharmacyandroidapplication.databinding.ItemContainerSentMessageBinding;
import com.example.pharmacyandroidapplication.models.ChatMessage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ChatMessage> chatMessages;
    private final String receiverProfileImage;
    private final String senderId;
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages, String receiverProfileImage, String senderId) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_container_sent_message, parent, false);
            return new SendMessViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_container_received_message, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SendMessViewHolder) holder).txtmess.setText(chatMessages.get(position).message);
            ((SendMessViewHolder) holder).txttime.setText(chatMessages.get(position).dateTime);
        }else {
            ((ReceivedViewHolder) holder).txtmess.setText(chatMessages.get(position).message);
            ((ReceivedViewHolder) holder).txttime.setText(chatMessages.get(position).dateTime);
        }
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
    class SendMessViewHolder extends RecyclerView.ViewHolder{
        TextView txtmess, txttime;
        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmess = itemView.findViewById(R.id.textSndMessage);
            txttime = itemView.findViewById(R.id.textSndDateTime);
        }

    }
    class ReceivedViewHolder extends RecyclerView.ViewHolder{
        TextView txtmess, txttime;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmess = itemView.findViewById(R.id.textRcvMessage);
            txttime = itemView.findViewById(R.id.textRcvDateTime);

        }

    }

}
