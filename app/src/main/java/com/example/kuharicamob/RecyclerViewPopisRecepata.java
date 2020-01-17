package com.example.kuharicamob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewPopisRecepata extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Recept> popisRecepata = new ArrayList<>();

    public RecyclerViewPopisRecepata (ArrayList<Recept> sviRecepti) {
        popisRecepata = sviRecepti;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popis_recepata, parent, false);
        ViewHolderPopisRecepata viewHolderPopisRecepata = new ViewHolderPopisRecepata(view);
        return viewHolderPopisRecepata;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolderPopisRecepata viewHolderPopisRecepata = (ViewHolderPopisRecepata) holder;
        viewHolderPopisRecepata.tvImeRecepta.setText(popisRecepata.get(position).getNazivJela());
        viewHolderPopisRecepata.tvSastojciRecepta.setText(popisRecepata.get(position).getListaSastojci().toString());
    }

    @Override
    public int getItemCount() {
        return popisRecepata.size();
    }

    public class ViewHolderPopisRecepata extends RecyclerView.ViewHolder {
        TextView tvImeRecepta;
        TextView tvSastojciRecepta;

        public ViewHolderPopisRecepata(@NonNull View itemView) {
            super(itemView);
            tvImeRecepta = itemView.findViewById(R.id.receptPrikazImena);
            tvSastojciRecepta = itemView.findViewById(R.id.receptPrikazSastojaka);
        }
    }
}
