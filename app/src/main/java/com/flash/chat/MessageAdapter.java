package com.flash.chat;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flash.R;
import com.flash.person.Person;
import com.flash.person.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    List<Message> messagesList;
    FirebaseAuth mAuth;
    DatabaseReference mUserDatabase;
    public MessageAdapter(List<Message> messages){
        this.messagesList = messages;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout,parent,false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {
        String curr_user_id =mAuth.getCurrentUser().getUid();

        Message msg = messagesList.get(position);
        String from = msg.getFrom();
        String type = msg.getType();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Flash").child("Users").child(from);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("name").getValue().toString();


          //      String image = snapshot.child("thumb_image").getValue().toString();

              //  Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(holder.profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(from.equals(curr_user_id)){
             holder.messageText.setBackgroundColor(Color.WHITE);
             holder.messageText.setTextColor(Color.BLACK);

        }else {
            holder.messageText.setBackgroundColor(R.drawable.message_background);
            holder.messageText.setTextColor(Color.WHITE);
        }
        holder.messageText.setText(msg.getMessage());
        if(type.equals("text")){
            holder.messageText.setText(msg.getMessage());
            holder.messageImage.setVisibility(View.INVISIBLE);
        }else {
            holder.messageText.setVisibility(View.INVISIBLE);
            Picasso.get().load(msg.getMessage()).placeholder(R.drawable.default_avatar).into(holder.messageImage);
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public CircleImageView profileImage;

        public ImageView messageImage;
        public MessageViewHolder (View view) {
            super(view);
            messageText = (TextView) view.findViewById(R.id.message_body_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.messgae_profile_pic_layout);

            messageImage= (ImageView) view.findViewById(R.id.imageMessage);
        }
    }
}
