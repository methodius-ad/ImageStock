package com.xmethodius.imagestock.view.recycler.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xmethodius.imagestock.model.Image


class PhotosAdapter(
        photos: MutableList<Image>,
        context: Context,
        listener: OnPhotoClickedListener
) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    private val imageList: MutableList<Image>
    private val mContext: Context
    private val mListener: OnPhotoClickedListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image: Image = imageList[position]
        holder.imageView.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(v: View?) {
                mListener.photoClicked(imageList[holder.adapterPosition], v as ImageView?)
            }
        })
        Picasso.get()
            .load(image.getUrls()?.getRegular())
            .resize(300, 300)
            .centerCrop()
            .into(holder.imageView)
    }

    fun addImages(photos: List<Image>) {
        val lastCount = itemCount
        imageList.addAll(photos)
        notifyItemRangeInserted(lastCount, photos.size)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.img)
        }
    }

    interface OnPhotoClickedListener {
        fun photoClicked(photo: Image?, imageView: ImageView?)
    }

    init {
        imageList = photos
        mContext = context
        mListener = listener
    }
}