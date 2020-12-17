package com.example.moses.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moses.R;
import com.example.moses.activities.Prod_info;
import com.example.moses.model.Mod_Img_Prducts;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adp_Img_Products extends RecyclerView.Adapter<Adp_Img_Products.ViewHolder> {

    private Context mContext;
    List<Mod_Img_Prducts> mLinks;

    TextView popclos;
    ImageView popimg;
    Dialog myDialog;

    public Adp_Img_Products(Context mContext, List<Mod_Img_Prducts> mLinks) {
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

        holder.cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.lik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.lk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent viw = new Intent(mContext, Prod_info.class);
                viw.putExtra("nambari",mLinks.get(position).getpId());
                activity.startActivity(viw);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public void SetNotes(List<Mod_Img_Prducts> mod_products){
        this.mLinks = mod_products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView desc,price,type, weight, deliva;

        LinearLayout ct, lk;
        ImageView cat, lik;

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

            ct = itemView.findViewById(R.id.adp_cart);
            lk = itemView.findViewById(R.id.adp_like);

            cat = itemView.findViewById(R.id.adp_cat);
            lik = itemView.findViewById(R.id.adp_lik);

        }
    }

}