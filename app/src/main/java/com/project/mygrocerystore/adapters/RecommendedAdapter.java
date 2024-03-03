package com.project.mygrocerystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.mygrocerystore.databinding.RecommendedItemBinding;
import com.project.mygrocerystore.models.RecommendedModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    Context context;
    List<RecommendedModel> recommendedModelList;

    public RecommendedAdapter(Context context, List<RecommendedModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecommendedItemBinding binding = RecommendedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(recommendedModelList.get(position).getImg_url()).into(holder.binding.recImg);
        holder.binding.recName.setText(recommendedModelList.get(position).getName());
        holder.binding.recDec.setText(recommendedModelList.get(position).getDescription());
        holder.binding.recRating.setText(recommendedModelList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return recommendedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecommendedItemBinding binding;

        public ViewHolder(@NonNull RecommendedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
