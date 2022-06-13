package com.example.quiz_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quiz_final.databinding.IteamRankBinding;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankdViewHolder> {

    Context context;
    ArrayList<User> users;

    public RankAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public RankdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_rank, parent, false);
        return new RankdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankdViewHolder holder, int position) {
        User user = users.get(position);

        holder.binding.name.setText(user.getNickname());
        holder.binding.score.setText(String.valueOf(user.getScore()));
        holder.binding.index.setText(String.format("#%d", position+1));

        Glide.with(context)
                .load(user.getAvatar())
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class RankdViewHolder extends RecyclerView.ViewHolder {

         IteamRankBinding binding;
        public RankdViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = IteamRankBinding.bind(itemView);
        }
    }
}
