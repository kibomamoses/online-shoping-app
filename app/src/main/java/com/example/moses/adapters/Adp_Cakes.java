package com.example.moses.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moses.R;
import com.example.moses.model.Mod_Cakes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adp_Cakes extends RecyclerView.Adapter<Adp_Cakes.ViewHolder> {

    private Context mContext;
        List<Mod_Cakes> mLinks;

    TextView popclos;
    ImageView popimg;
    Dialog myDialog;

    public Adp_Cakes(Context mContext, List<Mod_Cakes> mLinks) {
        this.mContext = mContext;
        this.mLinks = mLinks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_post_product, parent, false);
        myDialog = new Dialog(mContext);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mContext).load(mLinks.get(position).getpImage()).into(holder.image);

        holder.desc.setText(mLinks.get(position).getpDesc());
        holder.price.setText(mLinks.get(position).getpPrice()+" KShs");
        holder.type.setText(mLinks.get(position).getpType());

        holder.deliva.setText(mLinks.get(position).getpDeliver());
        holder.weight.setText(mLinks.get(position).getpWeight()+" Grams");
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public void SetNotes(List<Mod_Cakes> mod_products){
        this.mLinks = mod_products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView desc,price,type, weight, deliva;

        RelativeLayout rel;

        public ViewHolder(View itemView) {
            super(itemView);

            rel = itemView.findViewById(R.id.disp_post);

            image = itemView.findViewById(R.id.disp_imgs);

            desc = itemView.findViewById(R.id.disp_desc);
            weight = itemView.findViewById(R.id.disp_weight);
            deliva = itemView.findViewById(R.id.disp_delivery);
            price = itemView.findViewById(R.id.disp_price);
            type = itemView.findViewById(R.id.disp_type);
        }
    }

}