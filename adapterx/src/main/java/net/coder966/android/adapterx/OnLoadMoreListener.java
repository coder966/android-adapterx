package net.coder966.android.adapterx;

public interface OnLoadMoreListener<T>{
    void onLoadMore(AdapterX<T> adapter, T lastItem);
}
