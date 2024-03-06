package com.project.mygrocerystore.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.mygrocerystore.adapters.NavCategoryAdapter;
import com.project.mygrocerystore.adapters.PopularAdapter;
import com.project.mygrocerystore.databinding.FragmentCategoryBinding;
import com.project.mygrocerystore.models.NavCategoryModel;
import com.project.mygrocerystore.models.PopularModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    FirebaseFirestore db;

    List<NavCategoryModel> navCategoryModelList;
    NavCategoryAdapter navCategoryAdapter;

    private FragmentCategoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = FirebaseFirestore.getInstance();
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.catRec.setVisibility(View.GONE);
        binding.catRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        navCategoryModelList = new ArrayList<>();
        navCategoryAdapter = new NavCategoryAdapter(getActivity(), navCategoryModelList);
        binding.catRec.setAdapter(navCategoryAdapter);

        db.collection("NavCategory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NavCategoryModel navCategoryModel = document.toObject(NavCategoryModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                        binding.progressbar.setVisibility(View.GONE);
                        binding.catRec.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.catRec.setVisibility(View.GONE);}
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}