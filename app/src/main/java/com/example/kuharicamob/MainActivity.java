package com.example.kuharicamob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnDodaj;

    EditText etUnos;
    Button btnSave;
    DatabaseReference reff;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUnos = (EditText) findViewById(R.id.etIme);
        btnSave = (Button) findViewById(R.id.btnSave);
        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setIme(etUnos.getText().toString().trim());
                reff.push().setValue(member);
            }
        });

        btnDodaj = (Button)findViewById(R.id.dodaj);
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoviRecept.class);
                startActivity(intent);
            }
        });
    }
}
