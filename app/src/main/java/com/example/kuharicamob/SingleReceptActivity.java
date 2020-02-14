package com.example.kuharicamob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SingleReceptActivity extends AppCompatActivity {

    DatabaseReference reff;
    DatabaseReference favoritesReff;
    final String MY_PREFERENCES = "USER_DATA";
    SharedPreferences sharedPreferences;
    Recept recept = new Recept();

    TextView textNazivJela;
    ViewSwitcher favoriteViewSwitcher;
    ImageView ivNotFavorite;
    ImageView ivFavorite;
    TextView tvOcjena;
    RecyclerView rvSastojci;
    RecyclerView rvKoraci;

    ArrayList<String> sastojci = new ArrayList<>();
    ArrayList<String> koraci = new ArrayList<>();

    LinearLayout llDijeli;
    LinearLayout llOcijeni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recept);

        textNazivJela = findViewById(R.id.nazivJela);
        favoriteViewSwitcher = findViewById(R.id.switcher);
        ivNotFavorite = findViewById(R.id.emptyStar);
        ivFavorite = findViewById(R.id.favoritStar);
        tvOcjena = findViewById(R.id.tvOcjena);
        rvSastojci = findViewById(R.id.rvSastojci);
        rvKoraci = findViewById(R.id.rvKoraci);

        Intent intent = getIntent();
        final String id = intent.getExtras().get("id").toString();

        reff = FirebaseDatabase.getInstance().getReference().child("Recepti").child(id);
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "emptyString");
        favoritesReff = FirebaseDatabase.getInstance().getReference().child("Favoriti").child(userId);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sastojci.clear();
                koraci.clear();
                recept.setnId(dataSnapshot.child("nId").getValue().toString());
                recept.setNazivJela(dataSnapshot.child("nazivJela").getValue().toString());
                if(dataSnapshot.child("brojOcjena").getValue(Integer.class) != null && dataSnapshot.child("brojOcjena").getValue(Integer.class) != 0) {
                    recept.setBrojOcjena(dataSnapshot.child("brojOcjena").getValue(Integer.class));
                    recept.setOcjena(dataSnapshot.child("ocjena").getValue(Float.class));
                    tvOcjena.setText(String.valueOf((double)Math.round(recept.getOcjena()*100)/100));
                }
                for(DataSnapshot snap: dataSnapshot.child("listaSastojci").getChildren())
                {
                    sastojci.add(snap.getValue().toString());
                }
                recept.setListaSastojci(sastojci);
                for(DataSnapshot snap: dataSnapshot.child("listaKoraci").getChildren())
                {
                    koraci.add(snap.getValue().toString());
                }
                recept.setListaKoraci(koraci);

                textNazivJela.setText(recept.getNazivJela());

                ReceptRecyclerViewAdapter adapterSastojci = new ReceptRecyclerViewAdapter(recept.getListaSastojci(), R.layout.single_list_item);
                rvSastojci.setAdapter(adapterSastojci);
                rvSastojci.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                ReceptRecyclerViewAdapter adapterKoraci = new ReceptRecyclerViewAdapter(recept.getListaKoraci(), R.layout.single_list_item);
                rvKoraci.setAdapter(adapterKoraci);
                rvKoraci.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        favoritesReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    if(snap.getValue().toString().matches(recept.getnId())){
                        favoriteViewSwitcher.showNext();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        favoriteViewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favoritId;
                if(favoriteViewSwitcher.getCurrentView() == ivNotFavorite){
                    favoriteViewSwitcher.showNext();

                    favoritId = favoritesReff.push().getKey();
                    favoritesReff.child(favoritId).setValue(recept.getnId());
                }
                else{
                    favoritesReff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snap: dataSnapshot.getChildren()){
                                if(snap.getValue().toString().matches(recept.getnId())){
                                    favoritesReff.child(snap.getKey()).removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    favoriteViewSwitcher.showPrevious();
                }
            }
        });

        llDijeli = findViewById(R.id.linLayDijeli);
        llDijeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sastojci = "";
                for(String sastojak: recept.getListaSastojci()) {
                    sastojci += "- " + sastojak + "\n";
                }

                String koraci = "";
                for(String korak: recept.getListaKoraci()) {
                    koraci += "- " + korak + "\n";
                }

                String shareData = recept.getNazivJela().toUpperCase() + "\n\nSastojci: \n" + sastojci + "\n\nKoraci: \n" + koraci;
                Intent intentDijeljenje = new Intent();
                intentDijeljenje.setAction(Intent.ACTION_SEND);

                intentDijeljenje.setType("text/plain");
                intentDijeljenje.putExtra(Intent.EXTRA_TEXT, shareData);
                v.getContext().startActivity(intentDijeljenje);
                Log.d("rišaba", "onClick: " + recept.getNazivJela());
            }
        });

        llOcijeni = findViewById(R.id.linLayOcijeni);
        llOcijeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOcjenaFragment dialogOcjenaFragment = new DialogOcjenaFragment(id);
                dialogOcjenaFragment.show(getSupportFragmentManager(), "Ocjenafragment");
            }
        });
    }
}
