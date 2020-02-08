package com.example.kuharicamob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoviReceptActivity extends AppCompatActivity {

    ReceptRecyclerViewAdapter adapterSastojci;
    ReceptRecyclerViewAdapter adapterKoraci;
    ArrayList<String> lSastojci;
    ArrayList<String> lKoraci;
    ImageButton btnDodajSastojak;
    ImageButton btnDodajKorak;
    EditText etSastojak;
    EditText etKorak;
    EditText etNazivJela;
    Button btnSpremiRecept;
    DatabaseReference reff;
    DatabaseReference receptReference;
    Recept recept = new Recept();
    String userId;
    String receptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_recept);

        reff = FirebaseDatabase.getInstance().getReference().child("Recepti");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        etNazivJela = findViewById(R.id.etNazivJela);

        btnDodajSastojak = findViewById(R.id.btnDodajSastojak);
        etSastojak = findViewById(R.id.etSastojak);

        btnDodajKorak = findViewById(R.id.btnDodajKorak);
        etKorak = findViewById(R.id.etKorak);

        lSastojci = new ArrayList<>();
        lKoraci = new ArrayList<>();

        Intent intent = getIntent();
        if(intent.getExtras() != null && !intent.getExtras().get("idRecepta").toString().matches("")){
//            Toast.makeText(this, intent.getExtras().get("idRecepta").toString(), Toast.LENGTH_SHORT).show();
            receptId = intent.getExtras().get("idRecepta").toString();
            receptReference = reff.child(receptId);
            receptReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recept = dataSnapshot.getValue(Recept.class);

                    etNazivJela.setText(recept.getNazivJela());

                    lSastojci.addAll(recept.getListaSastojci());
                    lKoraci.addAll(recept.getListaKoraci());

                    adapterKoraci.notifyDataSetChanged();
                    adapterSastojci.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        SetupRecyclerView(R.id.recyclerViewSastojci, lSastojci);
        SetupRecyclerView(R.id.recyclerViewKoraci, lKoraci);

        btnDodajSastojak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sastojak = etSastojak.getText().toString();

                if(!sastojak.matches("")) {
                    lSastojci.add(sastojak);
                    adapterSastojci.notifyDataSetChanged();

                    etSastojak.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Upišite sastojak!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDodajKorak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String korak = etKorak.getText().toString();

                if(!korak.matches("")) {
                    lKoraci.add(korak);
                    adapterKoraci.notifyDataSetChanged();

                    etKorak.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Upišite korak!", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        recept = new Recept();


        btnSpremiRecept = (Button) findViewById(R.id.btnSpremiRecept);

        btnSpremiRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNazivJela.getText().toString().matches("")) {
                    recept.setNazivJela(etNazivJela.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unesite naziv jela!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!lSastojci.isEmpty() && !lKoraci.isEmpty()) {
                    recept.setListaSastojci(lSastojci);
                    recept.setListaKoraci(lKoraci);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Niste unijeli sastojke ili korake!", Toast.LENGTH_SHORT).show();
                    return;
                }

                userId = UserData.GetUserID(getApplicationContext());

                if(!userId.matches("")) {
                    recept.setUserID(userId);
                }

                String id;
                if(receptId.isEmpty()){
                    id = reff.push().getKey();
                }
                else{
                    id = receptId;
                }
                recept.setnId(id);

                reff.child(id).setValue(recept);

                finish();
            }
        });

    }

    public void SetupRecyclerView(int resource, ArrayList<String> lista) {
        RecyclerView rv = findViewById(resource);
        ReceptRecyclerViewAdapter adapter = new ReceptRecyclerViewAdapter(lista, R.layout.list_item);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(resource == R.id.recyclerViewSastojci){
            adapterSastojci = adapter;
        }
        else {
            adapterKoraci = adapter;
        }
    }
}
