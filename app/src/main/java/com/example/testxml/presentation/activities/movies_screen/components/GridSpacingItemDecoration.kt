package com.example.testxml.presentation.activities.movies_screen.components

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//https://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if(column == 0){
            outRect.left = spacing*10
            outRect.right = spacing*5
        }
        if(column == 1){
            outRect.left = spacing*5
            outRect.right = spacing*5
        }
        if(column == 2){
            outRect.right = spacing*10
        }

        if (position < spanCount) {
            outRect.top = spacing*5
        }
        outRect.bottom = spacing*5
    }
}
