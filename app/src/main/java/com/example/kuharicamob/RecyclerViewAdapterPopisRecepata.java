package com.example.kuharicamob;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapterPopisRecepata extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Recept> popisRecepata;
    String id;
    Context context;
    String user_id;

    public RecyclerViewAdapterPopisRecepata(ArrayList<Recept> sviRecepti, Context c) {
        popisRecepata = sviRecepti;
        context = c;
        user_id = UserData.GetUserID(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recepti_list_item, parent, false);
        ViewHolderPopisRecepata viewHolderPopisRecepata = new ViewHolderPopisRecepata(view);

        return viewHolderPopisRecepata;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderPopisRecepata viewHolderPopisRecepata = (ViewHolderPopisRecepata) holder;

        String sastojciRecepta = popisRecepata.get(position).getListaSastojci().toString().replace("[", "").replace("]", "");

        viewHolderPopisRecepata.tvImeRecepta.setText(popisRecepata.get(position).getNazivJela());
        viewHolderPopisRecepata.tvSastojciRecepta.setText(sastojciRecepta);

        if(user_id.matches(popisRecepata.get(position).getUserID())){
            viewHolderPopisRecepata.optionsBtn.setVisibility(View.VISIBLE);


            viewHolderPopisRecepata.optionsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, viewHolderPopisRecepata.optionsBtn);
                    popupMenu.inflate(R.menu.recept_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.urediRecept:
                                    Intent intent = new Intent(context, NoviReceptActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("idRecepta", popisRecepata.get(position).getnId());
                                    context.startActivity(intent);
                                    return true;

                                case R.id.obrisiRecept:
                                    FirebaseDatabase.getInstance().getReference("Recepti").child(popisRecepata.get(position).getnId()).removeValue();
                                    return true;
                            }
                            return false;
                        }
                    });

                    popupMenu.show();
                }
            });
        }
        else{
            viewHolderPopisRecepata.optionsBtn.setVisibility(View.INVISIBLE);
        }



        viewHolderPopisRecepata.clRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = popisRecepata.get(position).getnId();
                Log.d("oznake", "onClick: " + id);
                Intent intent = new Intent(v.getContext(), SingleReceptActivity.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);
            }
        });

       /* if(viewHolderPopisRecepata. == (getItemCount()-1)) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolderPopisRecepata.clRecept.getLayoutParams();
            params.setMargins(10, 10, 10, 150);
            viewHolderPopisRecepata.clRecept.setLayoutParams(params);
        }*/
    }

    @Override
    public int getItemCount() {
        return popisRecepata.size();
    }

    public class ViewHolderPopisRecepata extends RecyclerView.ViewHolder {
        ConstraintLayout clRecept;
        ImageView optionsBtn;
        TextView tvImeRecepta;
        TextView tvSastojciRecepta;


        public ViewHolderPopisRecepata(@NonNull View itemView) {
            super(itemView);
            optionsBtn = itemView.findViewById(R.id.optionsBtn);
            tvImeRecepta = itemView.findViewById(R.id.receptPrikazImena);
            tvSastojciRecepta = itemView.findViewById(R.id.receptPrikazSastojaka);
            clRecept = itemView.findViewById(R.id.clRecept);
        }
    }
}
