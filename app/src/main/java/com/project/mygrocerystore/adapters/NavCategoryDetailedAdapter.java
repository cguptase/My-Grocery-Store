package com.project.mygrocerystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.mygrocerystore.databinding.NavCatItemBinding;
import com.project.mygrocerystore.databinding.NavCategoryDetailedItemBinding;
import com.project.mygrocerystore.models.NavCategoryDetailedModel;

import java.util.List;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.ViewHolder> {
    Context context;
    List<NavCategoryDetailedModel> list;

    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NavCategoryDetailedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NavCategoryDetailedItemBinding binding = NavCategoryDetailedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NavCategoryDetailedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.binding.catNavImg);
        holder.binding.navCatName.setText(list.get(position).getName());
        holder.binding.price.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{private NavCategoryDetailedItemBinding binding;
        public ViewHolder(@NonNull NavCategoryDetailedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
