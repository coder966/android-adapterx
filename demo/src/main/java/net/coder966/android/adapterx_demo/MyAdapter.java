package net.coder966.android.adapterx_demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.coder966.android.adapterx.AdapterX;

import java.util.List;

/**
 * Note that the class is generic. It requires two types.
 *
 * Person: is the individual item type
 * MyAdapter.MyViewHolder: is the view holder type
 */
public class MyAdapter extends AdapterX<Person, MyAdapter.MyViewHolder> {

    /**
     * Define your normal ViewHolder. No changes on this.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public MyViewHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }
    }

    /**
     * This is your constructor. It must call super(initialList)
     * @param initialList The initial list of items
     */
    public MyAdapter(List<Person> initialList){
        super(initialList);
    }

    /**
     * Normally, you will override:
     * onCreateViewHolder(viewGroup parent, int viewType)
     *
     * But here you must implement THIS method:
     * onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
     *
     * Notice the additional argument "inflater" which is useful to inflate your item layout.
     * This should make your code even cleaner.
     *
     * @param inflater
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Normally, you will override:
     * onBindViewHolder(RecyclerView.ViewHolder holder, int position)
     *
     * But here you should implement THIS method:
     * onBindViewHolder(MyViewHolder holder, Person item)
     *
     * Notice the second argument was an integer representing the item position in the list
     * here it is an object fetched from the list for you, this is nice add.
     *
     * @param holder
     * @param item
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @NonNull Person item) {
        holder.nameTextView.setText(item.name);
    }
}
