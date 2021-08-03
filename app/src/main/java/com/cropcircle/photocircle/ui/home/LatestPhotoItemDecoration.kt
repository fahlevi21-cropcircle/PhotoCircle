package com.cropcircle.photocircle.ui.home

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.model.ItemLayout

class LatestPhotoItemDecoration(
    private val leftSize: Int,
    private val rightSize: Int,
    private val topSize: Int,
    private val bottomSize: Int,
    private val resources: Resources,
    private val layout: ItemLayout
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.apply {
            left = leftSize
            right = rightSize
            top = topSize
            bottom = bottomSize

            when(layout){
                ItemLayout.LINEAR_VERTICAL ->
                    if (parent.getChildAdapterPosition(view) == 0){
                        top = 56
                    }
                ItemLayout.STAGGERED_TWO_SPAN ->
                    when {
                        parent.getChildAdapterPosition(view) == 0 -> {
                            outRect.top = resources.getDimensionPixelSize(R.dimen.margin_8)
                            outRect.left = resources.getDimensionPixelSize(R.dimen.margin_8)
                        }
                        parent.getChildAdapterPosition(view) == 1 -> {
                            outRect.top = resources.getDimensionPixelSize(R.dimen.margin_8)
                            outRect.right = resources.getDimensionPixelSize(R.dimen.margin_8)
                        }
                        parent.getChildAdapterPosition(view) % 2 == 0 -> {
                            outRect.left = resources.getDimensionPixelSize(R.dimen.margin_8)
                        }
                        parent.getChildAdapterPosition(view) % 2 != 0 -> {
                            outRect.right = resources.getDimensionPixelSize(R.dimen.margin_8)
                        }
                    }
                ItemLayout.GRID_TWO_SPAN ->
                    when {
                        parent.getChildAdapterPosition(view) == 0 -> {
                            outRect.top = resources.getDimensionPixelSize(R.dimen.layout_margin)
                            outRect.left = resources.getDimensionPixelSize(R.dimen.layout_margin)
                        }
                        parent.getChildAdapterPosition(view) == 1 -> {
                            outRect.top = resources.getDimensionPixelSize(R.dimen.layout_margin)
                            outRect.right = resources.getDimensionPixelSize(R.dimen.layout_margin)
                        }
                        parent.getChildAdapterPosition(view) % 2 == 0 -> {
                            outRect.left = resources.getDimensionPixelSize(R.dimen.layout_margin)
                        }
                        parent.getChildAdapterPosition(view) % 2 != 0 -> {
                            outRect.right = resources.getDimensionPixelSize(R.dimen.layout_margin)
                        }
                    }
                else -> top = 56
            }
        }
    }
}