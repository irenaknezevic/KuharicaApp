package com.example.kuharicamob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_recept);

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

                lista.add(sastojak);
                arrayAdapter.notifyDataSetChanged();
                listaSastojaka.setAdapter(arrayAdapter);


                etSastojak.setText("");
            }
        });

        btnDodajKorak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String korak = etKorak.getText().toString();

                lKoraci.add(korak);
                listaKoraka.setAdapter(arrayAdapterKoraci);
                arrayAdapterKoraci.notifyDataSetChanged();

                etKorak.setText("");
            }
        });
    }
}
