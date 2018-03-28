package com.example.aetc.inclassassignment08_mariannab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText keyField;
    private EditText valueField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        keyField = (EditText) findViewById(R.id.key_edit_text);
        valueField = (EditText) findViewById(R.id.value_edit_text);
    }


    public void writeToCloud(View view) {
        myRef.child(keyField.getText().toString()).setValue(valueField.getText().toString());
    }

    public void readFromCloud(View view) {
        DatabaseReference childRef = myRef.child(keyField.getText().toString());
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String loadedData;
                loadedData = valueField.getText().toString();
                if (dataSnapshot.exists()) {
                    loadedData = dataSnapshot.getValue(String.class);
                    valueField.setText(loadedData);
                } else {
                    String emptyField = "";
                    valueField.setText(emptyField);
                    Toast.makeText(MainActivity.this, "Cannot find key", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

