package com.example.azamm.rizkychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private TextView tvemail,tvpassword;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvemail = findViewById(R.id.tvProfileEmail);
        tvpassword = findViewById(R.id.tvProfilePassword);
        mDatabase = FirebaseDatabase.getInstance();
        mRef =  mDatabase.getReference("users");
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        Read();
    }

    private void Read(){
        // Read from the database
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String email = dataSnapshot.child(mUser.getUid()).child("email").getValue(String.class);
                String password = dataSnapshot.child(mUser.getUid()).child("password").getValue(String.class);
                Log.d(TAG, "onDataChange: " + email + password);
                tvemail.setText(email);
                tvpassword.setText(password);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
