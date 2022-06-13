package com.example.quiz_final;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quiz_final.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;


public class home extends Fragment {


    public home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentHomeBinding binding;
    FirebaseFirestore database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        final ArrayList<Courses_Item> categories = new ArrayList<>();
        final ArrayList<String> categoriesName = new ArrayList<>();
        final CoursesAdapter adapter = new CoursesAdapter(getContext(), categories);
        if(!Start_layout.mediaPlayer.isPlaying())
        {
            Start_layout.mediaPlayer.start();
        }
        database.collection("Courses")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Courses_Item model = snapshot.toObject(Courses_Item.class);
                            model.setCategoryId(snapshot.getId());
                            categories.add(model);
                            categoriesName.add(model.getCategoryName());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        binding.a.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.a.setAdapter(adapter);



        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}