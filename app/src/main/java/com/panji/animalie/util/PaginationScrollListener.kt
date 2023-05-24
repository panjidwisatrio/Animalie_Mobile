package com.panji.animalie.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy) // dx = horizontal, dy = vertical

        if (dy <= 0) return // if dy <= 0, then it's scrolling up, so we don't need to do anything

        if (layoutManager.itemCount == 0) return // if there's no item, then we don't need to do anything

        val totalItemCount = layoutManager.itemCount // total number of item in the list
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() // index of the last item that is currently visible

        // if the total number of item in the list is less than the total page count, then we don't need to do anything
        if (!isLoading() && lastVisibleItemPosition == totalItemCount - 1 && !isLastPage()) {
            loadMoreItems()
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount(): Int?
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}