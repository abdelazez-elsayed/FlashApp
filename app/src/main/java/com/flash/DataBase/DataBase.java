package com.flash.DataBase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.flash.person.User;
import com.flash.person.Worker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DataBase {

    //Singleton object of DataBase
    private static DatabaseReference databaseReference;
    private boolean success; // success of any add operation to avoid duplicates
    private static class Singleton {
        private static final  DataBase DATABASE = new DataBase();
    }

    private DataBase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Flash");
        success = true;
    }

    public static DataBase getInstance() {
        return Singleton.DATABASE;
    }

    public boolean isSuccess() {
        boolean ans = success;
        success = true;
        return ans;
    }

    // add info of users (customers)
    public void addUser(User user)
    {
        if (user == null)
            return;
        //userExist(user); // put a listener for checking if there's a duplicate

        // add the new user to the firebase
        databaseReference.child("Users").child(user.getUserId()).setValue(user);
       // user.setUserId(userRef.getKey());
       // userRef.setValue(user);
    }


    // same as Customers but for workers
    public void addWorker(Worker worker)
    {
        if (worker == null)
            return;
       // workerExist(worker);
        databaseReference.child("Workers").child(worker.getWorkerId()).setValue(worker);
       // worker.setWorkerId(workerRef.getKey());
       // workerRef.setValue(worker);
    }


    // attach a Value Listener to a Query for the given user
    public User userExist(String userID)
    {
        if (userID == null)
            return null;

        // if there's multiple records with the same email, remove them all except the first one
        // and make success is false to imply that the sign in didn't complete
        final User[] user = {null};
        databaseReference.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    user[0] = snapshot.getValue(User.class);

                else
                    user[0] = null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return user[0];
    }

    // same as Users but for Workers
    public Worker workerExist(String workerID)
    {
        if (workerID == null)
            return null;

        // if there's multiple records with the same email, remove them all except the first one
        // and make success is false to imply that the sign in didn't complete
        final Worker[] worker = {null};
        databaseReference.child("Workers").child(workerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    worker[0] = snapshot.getValue(Worker.class);

                else
                    worker[0] = null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return worker[0];
    }
}
