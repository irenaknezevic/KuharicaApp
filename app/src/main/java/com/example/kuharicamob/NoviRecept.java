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
    ArrayList<String> lista;
    Button btnDodajSastojak;
    EditText etSastojak;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_recept);

        listaSastojaka = (ListView)findViewById(R.id.listaSastojaka);
        btnDodajSastojak = (Button) findViewById(R.id.btnDodajSastojak);
        etSastojak = (EditText) findViewById(R.id.etSastojak);

//        listaKoraka = (ListView) findViewById(R.id.listaKoraka);
//        btnDodajKorak = (Button) findViewById(R.id.btnDodajKorak);
//        etKorak = (EditText) findViewById(R.id.etKorak);

        lista = new ArrayList<String>();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista);

        btnDodajSastojak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sastojak = etSastojak.getText().toString();

                lista.add(sastojak);
                listaSastojaka.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
