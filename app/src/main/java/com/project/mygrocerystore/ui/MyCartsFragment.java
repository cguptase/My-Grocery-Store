package com.project.mygrocerystore.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.mygrocerystore.activities.PlacedOrderActivity;
import com.project.mygrocerystore.adapters.MyCartAdapter;
import com.project.mygrocerystore.databinding.FragmentMyCartsBinding;
import com.project.mygrocerystore.models.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartsFragment extends Fragment {
    private FragmentMyCartsBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;
    int totalBill;

    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;

    public MyCartsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyCartsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.recyclerview.setVisibility(View.GONE);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(nMessageReceiver, new IntentFilter("MyTotalAmount"));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        binding.recyclerview.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        String documentId = documentSnapshot.getId();

                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                        cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                        binding.progressbar.setVisibility(View.GONE);
                        binding.recyclerview.setVisibility(View.VISIBLE);
                    }
                    calculateTotalAmount(cartModelList);
                }
            }
        });

        binding.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);
            }
        });

        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> cartModelList){
        double totalAmount=0.0;
        for(MyCartModel myCartModel:cartModelList){
            totalAmount+=myCartModel.getTotalPrice();
        }
        binding.totalPrice.setText("Total Amount: "+totalAmount);
    }

//    public BroadcastReceiver nMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            totalBill = intent.getIntExtra("totalAmount", 0);
//            binding.totalPrice.setText("Total Bill: " + totalBill + "$");
//        }
//    };
}