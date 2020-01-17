package com.example.kuharicamob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoviRecept extends AppCompatActivity {

    RecyclerViewAdapter adapterSastojci;
    RecyclerViewAdapter adapterKoraci;
    ArrayList<String> lSastojci;
    ArrayList<String> lKoraci;
    Button btnDodajSastojak;
    Button btnDodajKorak;
    EditText etSastojak;
    EditText etKorak;
    EditText etNazivJela;
    Button btnSpremiRecept;
    DatabaseReference reff;
    Recept recept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_recept);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        etNazivJela = (EditText) findViewById(R.id.etNazivJela);

        btnDodajSastojak = (Button) findViewById(R.id.btnDodajSastojak);
        etSastojak = (EditText) findViewById(R.id.etSastojak);

        btnDodajKorak = (Button) findViewById(R.id.btnDodajKorak);
        etKorak = (EditText) findViewById(R.id.etKorak);

        lSastojci = new ArrayList<>();
        lKoraci = new ArrayList<>();

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

        recept = new Recept();
        reff = FirebaseDatabase.getInstance().getReference().child("Recepti");

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
                reff.push().setValue(recept);
            }
        });

    }

    public void SetupRecyclerView(int resource, ArrayList<String> lista) {
        RecyclerView rv = findViewById(resource);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(lista);
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
