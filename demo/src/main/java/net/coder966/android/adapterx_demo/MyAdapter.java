package net.coder966.android.adapterx_demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.coder966.android.adapterx.AdapterX;

import java.util.List;

public class MyAdapter extends AdapterX<Person, MyAdapter.MyViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public MyViewHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }
    }

    public MyAdapter(List<Person> list){
        super(list);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @NonNull Person item) {
        holder.nameTextView.setText(item.name);
    }
}
