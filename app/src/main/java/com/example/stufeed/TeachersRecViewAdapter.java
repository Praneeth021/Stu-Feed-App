package com.example.stufeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TeachersRecViewAdapter extends RecyclerView.Adapter<TeachersRecViewAdapter.ViewHolder> {


    private ArrayList<Teachers> teachers;
    private Context mContext;

    public TeachersRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_teachers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Teachers teacher = teachers.get(position);

        holder.textView.setText(teacher.getUsername());

        Glide.with(mContext).asBitmap().load(teacher.getImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public void setTeachers(ArrayList<Teachers> teachers) {
        this.teachers = teachers;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imageView;
        private TextView textView;
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.parent);
            imageView = itemView.findViewById(R.id.t_img);
            textView = itemView.findViewById(R.id.t_name);
            button = itemView.findViewById(R.id.t_feedback);
        }
    }


}
