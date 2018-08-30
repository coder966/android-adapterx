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
public class MyAdapter extends AdapterX<Person> {
    private final int VIEW_TYPE_MALE = 1;
    private final int VIEW_TYPE_FEMALE = 2;

    /**
     * Define your normal ViewHolder. No changes on this.
     */
    public static class MaleViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public MaleViewHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }
    }

    /**
     * Define your normal ViewHolder. No changes on this.
     */
    public static class FemaleViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public FemaleViewHolder(View itemView){
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
     * Just to test multiple view types
     * @param item
     * @return
     */
    @Override
    public int getItemViewType(Person item) {
        if(item.gender == Gender.MALE){
            return VIEW_TYPE_MALE;
        }else{
            return VIEW_TYPE_FEMALE;
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_MALE){
            View itemView = inflater.inflate(R.layout.item_male, parent, false);
            return new MaleViewHolder(itemView);
        }else{
            View itemView = inflater.inflate(R.layout.item_female, parent, false);
            return new FemaleViewHolder(itemView);
        }
    }

    /**
     * Normally, you will override:
     * onBindViewHolder(RecyclerView.ViewHolder holder, int position)
     *
     * But here you should implement THIS method:
     * onBindViewHolder(ViewHolder holder, Person item)
     *
     * Notice the second argument was an integer representing the item position in the list
     * here it is an object fetched from the list for you, this is nice add.
     *
     * @param holder
     * @param item
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Person item) {
        if(holder instanceof MaleViewHolder){
            ((MaleViewHolder) holder).nameTextView.setText(item.name);
        }else{
            ((FemaleViewHolder) holder).nameTextView.setText(item.name);
        }
    }
}
