package com.example.kuharicamob;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogOcjenaFragment extends DialogFragment {

    ImageView imageViewZatvori;
    RatingBar ratingBar;
    Button btnOcijeni;
    String idJela;
    DatabaseReference reff;
    Recept recept;
    Float novaOcjena;

    public DialogOcjenaFragment (String idJela) {
        this.idJela = idJela;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dialog_ocjena, container, false);

        imageViewZatvori = view.findViewById(R.id.ivZatvori);
        ratingBar = view.findViewById(R.id.rating_bar);
        btnOcijeni = view.findViewById(R.id.btnOcijeni);

        imageViewZatvori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Recepti").child(idJela);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recept = dataSnapshot.getValue(Recept.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnOcijeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("rating", "onClick: " + ratingBar.getRating());
                if(recept.getBrojOcjena() != 0)
                {
                    novaOcjena = ((recept.getOcjena()*recept.getBrojOcjena())+ratingBar.getRating())/(recept.getBrojOcjena()+1);
                    recept.setBrojOcjena(recept.getBrojOcjena()+1);
                    recept.setOcjena(novaOcjena);
                }
                else{
                    recept.setBrojOcjena(1);
                    recept.setOcjena(ratingBar.getRating());
                }

                reff.setValue(recept);
                getDialog().dismiss();
            }
        });

        return view;
    }
}
