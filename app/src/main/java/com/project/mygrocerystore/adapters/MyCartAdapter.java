package com.project.mygrocerystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.mygrocerystore.databinding.MyCartItemBinding;
import com.project.mygrocerystore.models.MyCartModel;

import java.util.List;


public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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
        holder.binding.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));

        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").document(cartModelList.get(position).getDocumentId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartModelList.remove(cartModelList.get(position));
                            notifyDataSetChanged();
                            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//        // pass total amount to My Cart Fragment
//        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
//        Intent intent = new Intent("MyTotalAmount");
//        intent.putExtra("totalAmount", totalPrice);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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
