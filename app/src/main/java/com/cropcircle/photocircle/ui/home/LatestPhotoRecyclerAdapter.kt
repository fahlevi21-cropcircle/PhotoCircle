package com.cropcircle.photocircle.ui.home

import android.graphics.drawable.TransitionDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cropcircle.photocircle.databinding.ItemCardPhotoBinding
import com.cropcircle.photocircle.model.PhotoItem

class LatestPhotoRecyclerAdapter : RecyclerView.Adapter<LatestPhotoRecyclerAdapter.ViewHolder>() {
    private val list = mutableListOf<PhotoItem>()
    private lateinit var onItemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<PhotoItem>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun onItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    fun getItem(position: Int) : PhotoItem{
        return list[position]
    }

    inner class ViewHolder(private val binding: ItemCardPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoItem, position: Int) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickListener.onClick(item, root, position)
                }

                Glide.with(itemImage)
                    .load(item.urls.small)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemImage)
            }
        }
    }

    interface OnItemClickListener{
        fun onClick(item: PhotoItem, view: View, position: Int)
    }
}