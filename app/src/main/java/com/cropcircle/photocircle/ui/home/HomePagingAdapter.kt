package com.cropcircle.photocircle.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cropcircle.photocircle.databinding.ItemLatestPhotoBinding
import com.cropcircle.photocircle.model.PhotoItem
import kotlin.random.Random
import kotlin.random.nextInt

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
        val randomChildPosition = Random.nextInt(itemCount)
        val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams

        if (position == randomChildPosition){
            layoutParams.isFullSpan = true
        }
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
            val sizeList = listOf(350,435,450,320,645,580)
            val randSize = sizeList.random()
            binding.apply {
                Glide.with(itemView)
                    .load(photoItem.urls.thumb)
                    .override(200, randSize)
                    .thumbnail(0.3f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemLatestPhotoImg)

                /*itemLatestPhotoUsername.text = photoItem.user.username
                itemLatestPhotoDescription.text = photoItem.altDescription*/
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