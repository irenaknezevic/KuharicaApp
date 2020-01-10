package com.example.kuharicamob;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {
    Context c;
    ArrayList<String> mList;
    public ListAdapter (Context context, ArrayList<String> list) {
        super(context, R.layout.list_item, list);
        this.c = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
//        if(view == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//        }
//        View list_item = layoutInflater.inflate(R.layout.list_item, parent, false);
        TextView tvItemListe = view.findViewById(R.id.tvItem);
        ImageView btnObrisi = view.findViewById(R.id.btnObrisi);

        tvItemListe.setText(mList.get(position));
        btnObrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(mList.get(position));
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
