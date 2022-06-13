package com.example.quiz_final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.quiz_final.databinding.FragmentRankBinding;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class rank extends Fragment {
    public rank() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentRankBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRankBinding.inflate(inflater, container, false);

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        final ArrayList<User> users = new ArrayList<>();
        final ArrayList<String> tops = new ArrayList<String>();
        final ArrayList<String> topImage = new ArrayList<String>();

        final RankAdapter adapter = new RankAdapter(getContext(), users);
        if(!Start_layout.mediaPlayer.isPlaying())
        {
            Start_layout.mediaPlayer.start();
        }
        binding.ranktable.setAdapter(adapter);
        binding.ranktable.setLayoutManager(new LinearLayoutManager(getContext()));
     database.collection("users")
                .orderBy("score", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                    tops.add(user.nickname);
                    topImage.add(user.avatar);
                }
                binding.top1.setText(tops.get(0));
                binding.top2.setText(tops.get(1));
                binding.top3.setText(tops.get(2));
                Glide.with(rank.this)
                        .load(topImage.get(0))
                        .into(binding.top1Image);
                Glide.with(rank.this)
                        .load(topImage.get(1))
                        .into(binding.top2Image);
                Glide.with(rank.this)
                        .load(topImage.get(2))
                        .into(binding.top3Image);
            adapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }
}
