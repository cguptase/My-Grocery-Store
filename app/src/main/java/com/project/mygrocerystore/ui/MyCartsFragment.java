package com.project.mygrocerystore.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mygrocerystore.databinding.FragmentMyCartsBinding;

public class MyCartsFragment extends Fragment {
    private FragmentMyCartsBinding binding;


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

    return root;
    }
}