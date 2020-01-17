package com.example.kuharicamob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText search;
    Recept recept = new Recept();
    ArrayList<Recept> receptiList = new ArrayList<>();
    ArrayList<Recept> receptiQueryList = new ArrayList<>();
    final String TAG = "MainActivityLog";

    Button btnDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.trazilica);

//        search.setSubmitButtonEnabled(true);

        /*search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for(Recept recept: receptiList){
                    if(recept.getNazivJela().contains(query)){
                        Log.d(TAG, "onQueryTextSubmit: " + recept.getNazivJela());
                        receptiQueryList.add(recept);
                    }
                }
                setRecyclerViewPopisRecepata(receptiQueryList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Recepti");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receptiList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    recept = snapshot.getValue(Recept.class);
                    receptiList.add(recept);
                }

                setRecyclerViewPopisRecepata(receptiList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnDodaj = (Button)findViewById(R.id.dodaj);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receptiQueryList.clear();
                for(Recept recept: receptiList){
                    if(recept.getNazivJela().contains(search.getText())){
                        Log.d(TAG, "onQueryTextSubmit: " + recept.getNazivJela());
                        receptiQueryList.add(recept);
                    }
                }
                setRecyclerViewPopisRecepata(receptiQueryList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoviRecept.class);
                startActivity(intent);
            }
        });
    }

    public void setRecyclerViewPopisRecepata(ArrayList<Recept> recepti){
        Log.d(TAG, "onCreate: " + recepti.size());
        RecyclerView recyclerView = findViewById(R.id.mojRecycler);
        RecyclerViewPopisRecepata recyclerViewPopisRecepata = new RecyclerViewPopisRecepata(recepti);
        recyclerView.setAdapter(recyclerViewPopisRecepata);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
