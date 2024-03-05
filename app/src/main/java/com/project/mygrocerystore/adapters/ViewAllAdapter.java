package com.project.mygrocerystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.mygrocerystore.activities.DetailedActivity;
import com.project.mygrocerystore.databinding.ViewAllItemBinding;
import com.project.mygrocerystore.models.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder>{

    Context context;
    List<ViewAllModel> viewAllModelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewAllItemBinding binding = ViewAllItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(viewAllModelList.get(position).getImg_url()).into(holder.binding.viewImg);
        holder.binding.viewName.setText(viewAllModelList.get(position).getName());
        holder.binding.viewDescription.setText(viewAllModelList.get(position).getDescription());
        holder.binding.viewRating.setText(viewAllModelList.get(position).getRating());
        holder.binding.viewPrice.setText(viewAllModelList.get(position).getPrice()+"/kg");

        if(viewAllModelList.get(position).getType().equals("Egg")){
            holder.binding.viewPrice.setText(viewAllModelList.get(position).getPrice()+"/dozen");
        }
        if(viewAllModelList.get(position).getType().equals("Milk")){
            holder.binding.viewPrice.setText(viewAllModelList.get(position).getPrice()+"/litre");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail",viewAllModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewAllItemBinding binding;

        public ViewHolder(@NonNull ViewAllItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
