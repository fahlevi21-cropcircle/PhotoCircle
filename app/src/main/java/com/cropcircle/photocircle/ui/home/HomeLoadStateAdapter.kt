package com.cropcircle.photocircle.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cropcircle.photocircle.databinding.LayoutLoadStateFooterBinding

class HomeLoadStateAdapter(private val retry:() -> Unit) : LoadStateAdapter<HomeLoadStateAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = LayoutLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = loadState is LoadState.Loading
        layoutParams.isFullSpan = loadState is LoadState.Error
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: LayoutLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.loadStateRetryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){
            binding.apply {
                loadStateLoading.isVisible = loadState is LoadState.Loading
                loadStateRetryBtn.isVisible = loadState !is LoadState.Loading
                loadStateLabel.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}