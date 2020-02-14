package com.example.kuharicamob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    androidx.appcompat.widget.SearchView search;
    Recept recept = new Recept();
    ArrayList<Recept> receptiList = new ArrayList<>();
    ArrayList<Recept> receptiQueryList = new ArrayList<>();
    ArrayList<String> listaId = new ArrayList<>();
    ArrayList<String> queryListaId = new ArrayList<>();

    FloatingActionButton btnDodaj;

    ImageView ivFavoriti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String MY_PREFERENCES = "USER_DATA";
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        if(sharedPreferences.getString("user_id", "emptyString").matches("emptyString"))
        {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit();
            String uniqueID = UUID.randomUUID().toString();
            editor.putString("user_id", uniqueID);
            editor.apply();
        }

        search = findViewById(R.id.trazilica);

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
                Log.d("Eroor",databaseError.toString());
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                receptiQueryList.clear();
                for(Recept recept: receptiList){
                    if(recept.getNazivJela().toLowerCase().contains(query) || recept.getListaSastojci().toString().toLowerCase().contains(query)){
                        Log.d("receptprovjera", "onQueryTextSubmit: " + recept.getListaSastojci());
                        receptiQueryList.add(recept);
                    }
                }
                setRecyclerViewPopisRecepata(receptiQueryList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.matches("")){
                    setRecyclerViewPopisRecepata(receptiList);
                    return true;
                }
                receptiQueryList.clear();
                for(Recept recept: receptiList){
                    if(recept.getNazivJela().toLowerCase().contains(newText) || recept.getListaSastojci().toString().toLowerCase().contains(newText)){
                        Log.d("receptprovjera", "onQueryTextSubmit: " + recept.getListaSastojci());
                        receptiQueryList.add(recept);
                    }
                }
                setRecyclerViewPopisRecepata(receptiQueryList);
                return true;
            }
        });

        btnDodaj = findViewById(R.id.dodaj);
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoviReceptActivity.class);
                startActivity(intent);

            }
        });

        ivFavoriti = findViewById(R.id.favoriti);
        ivFavoriti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFavoriti = new Intent(MainActivity.this, FavoritiActivity.class);
                startActivity(intentFavoriti);
            }
        });
    }

    public void setRecyclerViewPopisRecepata(ArrayList<Recept> recepti){
        RecyclerView recyclerView = findViewById(R.id.mojRecycler);
        RecyclerViewAdapterPopisRecepata recyclerViewAdapterPopisRecepata = new RecyclerViewAdapterPopisRecepata(recepti, getApplicationContext());
        recyclerView.setAdapter(recyclerViewAdapterPopisRecepata);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
