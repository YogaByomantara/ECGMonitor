package com.example.ecgmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class personAdapter extends RecyclerView.Adapter<personAdapter.MyViewHolder> {
    Context context;
    ArrayList<person> list;

    public personAdapter(Context context, ArrayList<person> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.person,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        person user = list.get(position);
        holder.hasilrate.setText(user.getRate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView hasilrate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hasilrate = itemView.findViewById(R.id.rate_history);
        }
    }
}
