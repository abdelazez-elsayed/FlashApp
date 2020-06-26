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
    private static int cnt;
    private boolean success; // success of any add operation to avoid duplicates
    private static class Singleton {
        private static final  DataBase DATABASE = new DataBase();
    }

    private DataBase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Flash");
        success = true;
        cnt = 0;
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
        databaseReference.child("Workers").child(worker.getWorkerId()).setValue(worker);
       // worker.setWorkerId(workerRef.getKey());
       // workerRef.setValue(worker);
    }

}
