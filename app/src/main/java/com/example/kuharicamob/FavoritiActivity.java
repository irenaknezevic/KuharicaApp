package com.example.kuharicamob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritiActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceRecepti;
    ArrayList <String> receptiId = new ArrayList<>();
    ArrayList <Recept> recepti = new ArrayList<>();
    Recept recept = new Recept();
    SharedPreferences sharedPreferences;
    final String MY_PREFERENCES = "USER_DATA";
    String userId;
    RecyclerView recyclerViewFavoriti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriti);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", "emptyString");

        recyclerViewFavoriti = findViewById(R.id.mojRecyclerFavoriti);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Favoriti").child(userId);
        databaseReferenceRecepti = FirebaseDatabase.getInstance().getReference().child("Recepti");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receptiId.clear();
                for(DataSnapshot snap: dataSnapshot.getChildren() ) {
                    receptiId.add(String.valueOf(snap.getValue()));
                }
                databaseReferenceRecepti.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recepti.clear();
                        for(DataSnapshot snap: dataSnapshot.getChildren()) {
                            if(receptiId.contains(snap.getKey())) {
                                recept = snap.getValue(Recept.class);
                                recepti.add(recept);
                            }
                        }
                        initRecyclerView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initRecyclerView () {
        RecyclerViewAdapterPopisRecepata recyclerViewAdapterPopisRecepata = new RecyclerViewAdapterPopisRecepata(recepti, getApplicationContext());
        recyclerViewFavoriti.setAdapter(recyclerViewAdapterPopisRecepata);
        recyclerViewFavoriti.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
