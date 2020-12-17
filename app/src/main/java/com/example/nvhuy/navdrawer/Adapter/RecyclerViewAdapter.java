package com.example.nvhuy.navdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nvhuy.navdrawer.R;
import com.example.nvhuy.navdrawer.home.ProductActivity;
import com.example.nvhuy.navdrawer.models.Fruit;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public RecyclerViewAdapter(ArrayList<Fruit> mFruits) {
        this.mFruits = mFruits;
    }

    private ArrayList<Fruit> mFruits;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View categoryView = inflater.inflate(R.layout.recyclerview_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Fruit currentItem =mFruits.get(position);
        holder.product_img.setImageResource(currentItem.getImg());
        holder.product_cost.setText(currentItem.getCost());
        holder.product_name.setText(currentItem.getName());
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setRating(currentItem.getNumStar());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), ProductActivity.class);
                intent.putExtra("ID",currentItem.getId());
                intent.putExtra("IMG",currentItem.getImg());
                intent.putExtra("COUNT",currentItem.getCount());
                intent.putExtra("COST",currentItem.getCost());
                intent.putExtra("COMPANY",currentItem.getCompany());
                intent.putExtra("NAME",currentItem.getName());
                intent.putExtra("DESCRIPTION",currentItem.getDescription());
                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mFruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        ImageView product_img;
        TextView product_cost,product_name;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
             product_img  = itemView.findViewById(R.id.rv_allProduct_img);
             product_cost = itemView.findViewById(R.id.rv_allProduct_cost);
             product_name = itemView.findViewById(R.id.rv_allProduct_name);
             ratingBar    = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
    interface ItemClickListener {
        void onClick(View view, int position);
    }
}
