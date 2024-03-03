package com.project.mygrocerystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.mygrocerystore.databinding.NavCatItemBinding;
import com.project.mygrocerystore.models.NavCategoryModel;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {

    Context context;
    List<NavCategoryModel> navCategoryModelList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> navCategoryModelList) {
        this.context = context;
        this.navCategoryModelList = navCategoryModelList;
    }

    @NonNull
    @Override
    public NavCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NavCatItemBinding binding = NavCatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NavCategoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(navCategoryModelList.get(position).getImg_url()).into(holder.binding.catNavImg);
        holder.binding.navCatName.setText(navCategoryModelList.get(position).getName());
        holder.binding.navCatDescription.setText(navCategoryModelList.get(position).getDescription());
        holder.binding.navCatDiscount.setText(navCategoryModelList.get(position).getDiscount());

    }

    @Override
    public int getItemCount() {
        return navCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private NavCatItemBinding binding;

        public ViewHolder(@NonNull NavCatItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
