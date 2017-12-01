package com.example.hemanthanil.homescopic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hemanthanil on 2017-10-28.
 */

public class Adapter extends RecyclerView.Adapter {

    String[] items;
    Context context;

    public Adapter(String[] items,Context context) {
        this.items = items;
        
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View row =inflater.inflate(R.layout.custom_row_item,parent,false);
        return new itemHolder(row);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(R.drawable.property).resize(900,300).into(((itemHolder)holder).imageViewThumbnail);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
    private class itemHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView imageViewThumbnail;
        public itemHolder(View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.textView2);
            imageViewThumbnail=itemView.findViewById(R.id.imagevViewThumbnail);
        }
    }
}

