package com.example.quiz_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quiz_final.databinding.ItemStateAnswerBinding;

import java.util.ArrayList;

public class stateAdapter extends RecyclerView.Adapter<stateAdapter.stateViewHolder> {

    Context context;
    ArrayList<state_answer> state_answers;

    public stateAdapter(Context context, ArrayList<state_answer> state_answers){
        this.context = context;
        this.state_answers = state_answers;
    }

    @NonNull
    @Override
    public stateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_state_answer, parent, false);
        return new stateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull stateViewHolder holder, int position) {
        state_answer state_answer = state_answers.get(position);

        holder.binding.id.setText(state_answer.getId());
//        holder.binding.index.setText(String.format("#%d", position+1));
//
    }

    @Override
    public int getItemCount() {
        return state_answers.size();
    }

    public class stateViewHolder extends RecyclerView.ViewHolder {

        ItemStateAnswerBinding binding;
        public stateViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStateAnswerBinding.bind(itemView);
        }
    }
}