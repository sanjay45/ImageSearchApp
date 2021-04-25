package com.sanjay.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sanjay.imagesearchapp.R
import com.sanjay.imagesearchapp.data.UnsplashPhoto
import com.sanjay.imagesearchapp.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoAdapter(private val onClick: (UnsplashPhoto) -> Unit) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.UnsplashPhotoViewHolder>(
        PhotoDiffUtilCallback
    ) {

     class UnsplashPhotoViewHolder(val binding: ItemUnsplashPhotoBinding,
                                   val onClick: (UnsplashPhoto) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

         private lateinit var currentPhoto: UnsplashPhoto

        init {
            binding.root.setOnClickListener {
                onClick(currentPhoto)
            }
        }
        fun bindData(photo: UnsplashPhoto) {
            currentPhoto = photo
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewUserName.text = photo.user.username

            }
        }

    }

    override fun onBindViewHolder(holder: UnsplashPhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bindData(currentItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashPhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnsplashPhotoViewHolder(binding,onClick)
    }
}

object PhotoDiffUtilCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem == newItem
    }

}