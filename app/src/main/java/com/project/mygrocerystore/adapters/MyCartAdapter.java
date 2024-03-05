package com.project.mygrocerystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mygrocerystore.databinding.MyCartItemBinding;
import com.project.mygrocerystore.models.MyCartModel;

import java.util.List;


public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyCartItemBinding binding = MyCartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.productName.setText(cartModelList.get(position).getProductName());
        holder.binding.productPrice.setText(cartModelList.get(position).getProductPrice());
        holder.binding.currentDate.setText(cartModelList.get(position).getCurrentDate());
        holder.binding.currentTime.setText(cartModelList.get(position).getCurrentTime());
        holder.binding.totalQuantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.binding.totalPrice.setText(cartModelList.get(position).getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MyCartItemBinding binding;

        public ViewHolder(@NonNull MyCartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
