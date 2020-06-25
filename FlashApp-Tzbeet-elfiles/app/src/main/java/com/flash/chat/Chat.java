package com.flash.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.flash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;
    private StorageReference mImageStorage;
    private String currentUserID;
    private String targetUserID;
    private String targetUserName;
    private ImageButton sendButton;
    private ImageButton addBtn;
    private EditText chatMsgText;
    private TextView targetNameText;
    private CircleImageView profileImg;
    private RecyclerView mMessagesList;
    private final List<Message> messageList= new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter messageAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private static final int  TOTAL_ITEM_PER_PAGE = 10;
    private static final int GALLERY_PICK = 1;
    private int current_page =1;
    private int current_pos = 0;
    private String mLasKey = "";
    private String mPrevKey = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extra = getIntent().getExtras();
        mImageStorage = FirebaseStorage.getInstance().getReference();
        /////////
        messageAdapter = new MessageAdapter(messageList);
        targetNameText = (TextView) findViewById(R.id.char_name);
        addBtn = (ImageButton) findViewById(R.id.add_button);
        chatMsgText = (EditText) findViewById(R.id.messageTextView);
        profileImg = (CircleImageView) findViewById(R.id.custom_bar_image);
        mMessagesList = (RecyclerView) findViewById(R.id.messagesList);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe_refresh);
        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);


        ////////
        if(extra != null){
            targetUserID = extra.getString("TARGET_USER_ID");
            targetUserName = extra.getString("TARGET_USER_NAME");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID=firebaseAuth.getCurrentUser().getUid();
        databaseReference =  FirebaseDatabase.getInstance().getReference("Flash");
        loadMessages();
        databaseReference.child("Chat").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(targetUserID)){
                    Map chatAddMap = new HashMap();
                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/"+currentUserID+"/"+targetUserID,chatAddMap);
                    chatUserMap.put("Chat/"+targetUserID+"/"+currentUserID,chatAddMap);
                    databaseReference.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error != null){
                                Log.d("CHAT_LOG",error.getMessage());
                            }
                        }
                    })

;                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendMsg();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                current_page++;
                current_pos=0;
                loadMoreMessages();

            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent();
                galleryintent.setType("image/*");
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryintent,"Select Image"),GALLERY_PICK);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GALLERY_PICK && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            final String current_user_ref = "messages/"+currentUserID+"/"+targetUserID;
            final String target_uset_ref= "messages/"+targetUserID+"/"+currentUserID;
            DatabaseReference user_message_ref = databaseReference.child("messages").child(currentUserID).child(targetUserID).push();
            final String push_id = user_message_ref.getKey();
            StorageReference filepath = mImageStorage.child("message_images").child(push_id+".jpg");
            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        String download_uri = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                        Map msgMap = new HashMap();
                        msgMap.put("message",download_uri);
                        msgMap.put("seen",false);
                        msgMap.put("type","image");
                        msgMap.put("time", ServerValue.TIMESTAMP);
                        msgMap.put("from",currentUserID);
                        Map messageUserMap = new HashMap();
                        messageUserMap.put(current_user_ref+"/"+push_id,msgMap);
                        messageUserMap.put(target_uset_ref+"/"+push_id,msgMap);
                        databaseReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error != null){
                                    Log.d("CHAT_LOG",error.getMessage());
                                }
                            }
                        });
                    }
                }
            });


        }
    }

    private void loadMoreMessages() {
        DatabaseReference msgRef = databaseReference.child("messages").child(currentUserID).child(targetUserID);
        Query messageQuery = msgRef.orderByKey().endAt(mLasKey).limitToLast(TOTAL_ITEM_PER_PAGE);
        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);

                messageList.add(current_pos++,message);
                if(current_pos == 1){
                    mLasKey = snapshot.getKey();

                }
                Log.d("TotalKeys", "Last key : "+mLasKey +" | Prev Key : "+mPrevKey);
                messageAdapter.notifyDataSetChanged();
                mMessagesList.scrollToPosition(messageList.size()-1);
                mRefreshLayout.setRefreshing(false);
                mLinearLayout.scrollToPositionWithOffset(TOTAL_ITEM_PER_PAGE,0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadMessages() {
        DatabaseReference msgRef = databaseReference.child("messages").child(currentUserID).child(targetUserID);
        Query messagesQuery = msgRef.limitToLast(TOTAL_ITEM_PER_PAGE*current_page);
        messagesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                current_pos++;
                if(current_pos == 1){
                    mLasKey = snapshot.getKey();
                    mPrevKey=mLasKey;
                }
                messageList.add(message);
                messageAdapter.notifyDataSetChanged();
                mMessagesList.scrollToPosition(messageList.size()-1);
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMsg() {
        String msg = chatMsgText.getText().toString();
        if(!TextUtils.isEmpty(msg)){
            DatabaseReference ref = databaseReference.child("messages").child(currentUserID).child(targetUserID).push();
            String push_id = ref.getKey();
            String cur_user_ref = "messages/"+currentUserID+"/"+targetUserID+"/";
            String target_uset_ref= "messages/"+targetUserID+"/"+currentUserID;
            Map msgMap = new HashMap();
            msgMap.put("message",msg);
            msgMap.put("seen",false);
            msgMap.put("type","text");
            msgMap.put("time", ServerValue.TIMESTAMP);
            msgMap.put("from",currentUserID);
            Map messageUserMap = new HashMap();
            messageUserMap.put(cur_user_ref+"/"+push_id,msgMap);
            messageUserMap.put(target_uset_ref+"/"+push_id,msgMap);
            chatMsgText.setText("");
            databaseReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if(error != null){
                        Log.d("CHAT_LOG",error.getMessage());
                    }
                }
            });

        }
    }


}
