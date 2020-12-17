package com.example.nvhuy.navdrawer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvhuy.navdrawer.R;
import com.example.nvhuy.navdrawer.models.Category;


import java.util.ArrayList;

public class RecyclerAdapter_category extends RecyclerView.Adapter<RecyclerAdapter_category.ViewHolder> {
    public RecyclerAdapter_category(ArrayList<Category> mCategory) {
        this.mCategory = mCategory;
    }

    private ArrayList<Category> mCategory;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View categoryView = inflater.inflate(R.layout.category_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category currentItem =mCategory.get(position);
       holder.img.setImageResource(currentItem.getImg());
       holder.textView.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView    =  itemView.findViewById(R.id.category_text);
            img         =  itemView.findViewById(R.id.category_image);
        }
    }
}

