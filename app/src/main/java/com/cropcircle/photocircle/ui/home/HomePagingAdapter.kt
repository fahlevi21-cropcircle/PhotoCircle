package com.cropcircle.photocircle.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cropcircle.photocircle.databinding.ItemLatestPhotoBinding
import com.cropcircle.photocircle.model.PhotoItem

class HomePagingAdapter(
   private val listener: OnItemClickListener
) : PagingDataAdapter<PhotoItem, HomePagingAdapter.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLatestPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ViewHolder(private val binding: ItemLatestPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onClick(item.id)
                    }
                }
            }
        }

        fun bind(photoItem: PhotoItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(photoItem.urls.thumb)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemLatestPhotoImg)

                itemLatestPhotoUsername.text = photoItem.user.username
                itemLatestPhotoDescription.text = photoItem.altDescription
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(id: String)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PhotoItem>() {
            override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}