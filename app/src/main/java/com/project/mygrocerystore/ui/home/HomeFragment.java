package com.project.mygrocerystore.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.mygrocerystore.adapters.HomeAdapter;
import com.project.mygrocerystore.adapters.PopularAdapter;
import com.project.mygrocerystore.adapters.RecommendedAdapter;
import com.project.mygrocerystore.adapters.ViewAllAdapter;
import com.project.mygrocerystore.databinding.FragmentHomeBinding;
import com.project.mygrocerystore.models.HomeCategory;
import com.project.mygrocerystore.models.PopularModel;
import com.project.mygrocerystore.models.RecommendedModel;
import com.project.mygrocerystore.models.ViewAllModel;

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
    private List<ViewAllModel> viewAllModelList;
    private ViewAllAdapter viewAllAdapter;

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

        // SearchView
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), viewAllModelList);
        binding.searchRec.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchRec.setAdapter(viewAllAdapter);
        binding.searchRec.setHasFixedSize(true);

        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    searchProduct(editable.toString());
                }
            }
        });

        return root;
    }

    private void searchProduct(String type) {
        if (!type.isEmpty()) {
            db.collection("AllProducts").whereEqualTo("type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        viewAllModelList.clear();
                        viewAllAdapter.notifyDataSetChanged();
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                            viewAllModelList.add(viewAllModel);
                            viewAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}