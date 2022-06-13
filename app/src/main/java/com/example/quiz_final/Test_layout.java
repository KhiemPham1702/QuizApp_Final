package com.example.quiz_final;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.quiz_final.databinding.ActivityTestLayoutBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class Test_layout extends AppCompatActivity {
    ActivityTestLayoutBinding binding;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_layout.this, MainActivity.class);
                startActivity(intent);
            }
        });
        binding.barr.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.content, new home());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content, new Profile());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content, new rank());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content, new Setting());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });
        database = FirebaseFirestore.getInstance();
        final ArrayList<Test_Items> test_items = new ArrayList<>();
        final String catId = getIntent().getStringExtra("catId");
        final String course = getIntent().getStringExtra("course");
        final String categoryBackground = getIntent().getStringExtra("categoryBackground");
        final Test_Adapter adapter = new Test_Adapter(this, test_items);

        database.collection("Courses")
                .document(catId)
                .collection("Test")
                .orderBy("index")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            test_items.clear();
                            Test_Items model = snapshot.toObject(Test_Items.class);
                            model.setTestId(snapshot.getId());
                            model.setTestIdCat(catId);
                            model.setTestCourse(course);
                            model.setTestCategoryBackground(categoryBackground);
                            test_items.add(model);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });


//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        for (DocumentSnapshot snapshot : value.getDocuments()) {
//                            Test_Items model = snapshot.toObject(Test_Items.class);
//                            model.setTestId(snapshot.getId());
//                            test_items.add(model);
//                            Intent intent=new Intent(Test_layout.this,Quiz_layout.class);
//                            intent.putExtra("catID", catId);
//                            startActivity(intent);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });

//        database.collection("Courses")
//                .document("Cyd8j7w9TBOlQVp999kP")
//                .collection("Tests")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        test_items.clear();
//                        for (DocumentSnapshot snapshot : value.getDocuments()) {
//                            Test_Items model = snapshot.toObject(Test_Items.class);
//                            model.setTestId(snapshot.getId());
//                            test_items.add(model);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });


        binding.b.setLayoutManager(new GridLayoutManager(this, 1));
        binding.b.setAdapter(adapter);

    }
}