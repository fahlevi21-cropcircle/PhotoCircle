package com.cropcircle.photocircle.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.databinding.ItemPopularPhotoBinding
import com.cropcircle.photocircle.model.PhotoItem

class PopularPhotoRecyclerAdapter : RecyclerView.Adapter<PopularPhotoRecyclerAdapter.ViewHolder>() {
    private val list = mutableListOf<PhotoItem>()
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPopularPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<PhotoItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun onItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    fun getItem(position: Int): PhotoItem {
        return list[position]
    }

    inner class ViewHolder(private val binding: ItemPopularPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoItem, position: Int) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickListener.onClick(item, root, position)
                }

                Glide.with(itemImage)
                    .asDrawable()
                    .load(item.urls.regular + "&fm=jpg&fit=max&q=75")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemImage)
                Glide.with(profilePic)
                    .load(item.user.profileImage.medium)
                    .error(R.drawable.ic_baseline_person)
                    .into(profilePic)

                username.text = item.user.username ?: item.user.name
                when {
                    item.user.location != null -> {
                        bio.text = item.user.location
                    }
                    item.user.bio != null -> {
                        bio.text = item.user.bio
                    }
                    else -> {
                        bio.text = item.altDescription
                    }
                }
            }
        }
    }

    interface OnItemClickListener{
        fun onClick(item: PhotoItem, view: View, position: Int)
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