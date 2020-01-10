package com.example.kuharicamob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class NoviRecept extends AppCompatActivity {

    ListView listaSastojaka;
    ListView listaKoraka;
    ArrayList<String> lista;
    ArrayList<String> lKoraci;
    Button btnDodajSastojak;
    Button btnDodajKorak;
    EditText etSastojak;
    EditText etKorak;
    ListAdapter arrayAdapter;
    ArrayAdapter<String> arrayAdapterKoraci;
    EditText etNazivJela;
    Button btnSpremiRecept;
    DatabaseReference reff;
    DatabaseReference sastojci;
    DatabaseReference koraci;
    Recept recept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_recept);

        etNazivJela = (EditText) findViewById(R.id.etNazivJela);

        listaSastojaka = (ListView)findViewById(R.id.listaSastojaka);
        btnDodajSastojak = (Button) findViewById(R.id.btnDodajSastojak);
        etSastojak = (EditText) findViewById(R.id.etSastojak);

        listaKoraka = (ListView) findViewById(R.id.listaKoraka);
        btnDodajKorak = (Button) findViewById(R.id.btnDodajKorak);
        etKorak = (EditText) findViewById(R.id.etKorak);

        lista = new ArrayList<String>();
        lKoraci = new ArrayList<String>();

        arrayAdapter = new ListAdapter(getApplicationContext(), lista);
//        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        arrayAdapterKoraci = new ListAdapter(getApplicationContext(), lKoraci);

        btnDodajSastojak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sastojak = etSastojak.getText().toString();

                if(!sastojak.matches("")) {
                    lista.add(sastojak);
                    arrayAdapter.notifyDataSetChanged();
                    listaSastojaka.setAdapter(arrayAdapter);

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
                    listaKoraka.setAdapter(arrayAdapterKoraci);
                    arrayAdapterKoraci.notifyDataSetChanged();

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
                    recept.setNazivJela(etNazivJela.getText().toString().trim());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unesite naziv jela!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!lista.isEmpty() && !lKoraci.isEmpty()) {
                    recept.setListaSastojci(lista);
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
}
