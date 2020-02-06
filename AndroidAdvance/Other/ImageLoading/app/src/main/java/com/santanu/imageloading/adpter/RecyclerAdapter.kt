package com.santanu.imageloading.adpter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.santanu.imageloading.R
import com.santanu.imageloading.data.MovieData

class RecyclerAdapter internal constructor(private val context: Context, private val items: ArrayList<MovieData>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private val TAG: String = RecyclerAdapter::class.java.simpleName

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.rec_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items: MovieData = items[position]

        holder.bindView(items)

        Glide.with(context).load(items.url.medium)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imgView)





    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var titleView: TextView
        lateinit var dateView: TextView
        lateinit var imgView: ImageView

        init {
            titleView = view.findViewById(R.id.tv_title)
            dateView = view.findViewById(R.id.tv_data)
            imgView = view.findViewById(R.id.iv_image)
        }

        fun bindView(items : MovieData) {
            val title: String = items.name
            val data: String = items.timestamp
            val imgUrl: String = items.url.medium

            titleView.text = title
            dateView.text = data



        }


    }
}