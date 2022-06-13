package com.example.quiz_final;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Test_Adapter extends RecyclerView.Adapter<Test_Adapter.TestViewHolder> {

    Context context;
    ArrayList<Test_Items> test_items;

    public  Test_Adapter(Context context, ArrayList<Test_Items> test_items) {
        this.context = context;
        this.test_items = test_items;
    }


    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test,null);
        return new TestViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        final Test_Items model = test_items.get(position);

        holder.textView1.setText(model.getTestName());
        holder.textView2.setText(model.getTestTime());
        holder.textView3.setText(model.getTestNumberQuestion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Quiz_layout.class);
                intent.putExtra("testId", model.getTestId());
                intent.putExtra("cat", model.getTestIdCat());
                intent.putExtra("time",model.getTestTime());
                intent.putExtra("course", model.getTestCourse());
                intent.putExtra("categoryBackground", model.getTestCategoryBackground());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return test_items.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.TestName);
            textView2 = itemView.findViewById(R.id.TestTime);
            textView3 = itemView.findViewById(R.id.TestNumberQuestion);
        }
    }
}


