package com.project.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.mygrocerystore.R;
import com.project.mygrocerystore.adapters.NavCategoryDetailedAdapter;
import com.project.mygrocerystore.databinding.ActivityNavCategoryBinding;
import com.project.mygrocerystore.models.HomeCategory;
import com.project.mygrocerystore.models.NavCategoryDetailedModel;
import com.project.mygrocerystore.models.PopularModel;
import com.project.mygrocerystore.models.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {
    ActivityNavCategoryBinding binding;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.navCartDetRec.setVisibility(View.GONE);
        String type = getIntent().getStringExtra("type");
        binding.navCartDetRec.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this,list);
        binding.navCartDetRec.setAdapter(adapter);

        if (type != null && type.equalsIgnoreCase("Drink")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "Drink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                        binding.progressbar.setVisibility(View.GONE);
                        binding.navCartDetRec.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}