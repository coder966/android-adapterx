package net.coder966.android.adapterx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The is a RecyclerView adapter. It introduces "Load More" feature.
 * @param <T> The type of individual items in your list
 */
public abstract class AdapterX<T> extends RecyclerView.Adapter{
    private static class LoadingVH extends RecyclerView.ViewHolder{
        LoadingVH(View itemView){
            super(itemView);
        }
    }

    private final int VIEW_TYPE_LOADING = -1;
    private List<T> list;
    private int prefetchThreshold = 5;
    private boolean finished = false;
    private boolean isLoading = false;
    private int loadingItemIndex = -1;
    private OnLoadMoreListener<T> loadingListener;
    @LayoutRes
    private int loadingLayout;
    private View loadingView;

    @NonNull
    public abstract RecyclerView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @Nullable T item);

    public int getItemViewType(@Nullable T item){
        return 0;
    }

    public AdapterX(List<T> list){
        this.list = list;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener<T> listener){
        this.loadingListener = listener;
    }

    public void setLoadingView(@LayoutRes int layout) {
        loadingLayout = layout;
    }

    public void setPrefetchThreshold(int threshold){
        prefetchThreshold = threshold;
    }

    /**
     * Set a whole new list of items.
     * @param newList
     */
    public void setNewList(List<T> newList){
        list = newList;
        notifyDataSetChanged();
    }
    /**
     * Call this method if an error has occurred during loading more items. This will only hide the loading view.
     */
    public void error(){
        removeLoadingView();
    }

    /**
     * If you pass NULL or an empty list, then that means there is no more data.
     * @param newItems The list of new items to be added to the RecyclerView
     */
    public void load(List<T> newItems){
        removeLoadingView();
        if(newItems == null || newItems.isEmpty()){
            finished = true;
        }else{
            int oldSize = list.size();
            list.addAll(newItems);
            notifyItemRangeInserted(oldSize, newItems.size());
        }
    }


    @Override
    final public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);

        // check the requirements for the "load more" feature to work
        if(loadingListener == null || !(rv.getLayoutManager() instanceof LinearLayoutManager)){
            return;
        }

        // setup loading view
        if(loadingLayout == 0){
            loadingLayout = R.layout.default_loading;
        }
        loadingView = LayoutInflater.from(rv.getContext()).inflate(loadingLayout, rv, false);

        final LinearLayoutManager man = (LinearLayoutManager) rv.getLayoutManager();
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                // (dy > 0) means scroll down
                if(dy > 0 && !finished && !isLoading && (man.findLastVisibleItemPosition() + prefetchThreshold) >= man.getItemCount()){
                    isLoading = true;
                    loadingListener.onLoadMore(AdapterX.this, list.get(list.size()-1));
                    list.add(null);
                    loadingItemIndex = list.size()-1;
                    rv.post(() -> notifyItemInserted(loadingItemIndex));
                }
            }
        });
    }

    @Override
    final public int getItemCount() {
        return list.size();
    }

    @Override
    final public int getItemViewType(int position) {
        if(position == loadingItemIndex){
            return VIEW_TYPE_LOADING;
        }else{
            return getItemViewType(list.get(position));
        }
    }

    @NonNull
    @Override
    final public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_LOADING){
            return new LoadingVH(loadingView);
        }else{
            return onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
        }
    }

    @Override
    final public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(!(holder instanceof LoadingVH)){
            onBindViewHolder(holder, list.get(position));
        }
    }

    private void removeLoadingView(){
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
        isLoading = false;
        loadingItemIndex = -1;
    }
}