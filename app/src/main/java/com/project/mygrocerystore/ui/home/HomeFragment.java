package com.project.mygrocerystore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.mygrocerystore.adapters.HomeAdapter;
import com.project.mygrocerystore.adapters.PopularAdapter;
import com.project.mygrocerystore.adapters.RecommendedAdapter;
import com.project.mygrocerystore.databinding.FragmentHomeBinding;
import com.project.mygrocerystore.models.HomeCategory;
import com.project.mygrocerystore.models.PopularModel;
import com.project.mygrocerystore.models.RecommendedModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;

    List<PopularModel> popularModelList;
    PopularAdapter popularAdapters;

    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        binding.progressbar.setVisibility(View.VISIBLE);
        binding.scrollview.setVisibility(View.GONE);

        // Popular Items
        binding.popRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapter(getActivity(), popularModelList);
        binding.popRec.setAdapter(popularAdapters);

        db.collection("PopularProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PopularModel popularModel = document.toObject(PopularModel.class);
                        popularModelList.add(popularModel);
                        popularAdapters.notifyDataSetChanged();

                        binding.progressbar.setVisibility(View.GONE);
                        binding.scrollview.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Home Category
        binding.exploreRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), categoryList);
        binding.exploreRec.setAdapter(homeAdapter);

        db.collection("HomeCategory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        HomeCategory homeCategory = document.toObject(HomeCategory.class);
                        categoryList.add(homeCategory);
                        homeAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Recommended
        binding.recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), recommendedModelList);
        binding.recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommended").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                        recommendedModelList.add(recommendedModel);
                        recommendedAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
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