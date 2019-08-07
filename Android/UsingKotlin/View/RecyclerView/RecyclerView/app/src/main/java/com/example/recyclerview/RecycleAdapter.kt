package com.example.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class RecycleAdapter internal constructor(private val dataList: Array<String?>) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.recycle_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val title = dataList[i]
        viewHolder.txtTitle.text = title
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var imgIcon: ImageView
        internal var txtTitle: TextView

        init {
            imgIcon = itemView.findViewById(R.id.recycle_view_icon)
            txtTitle = itemView.findViewById(R.id.recycle_view_txt)
        }
    }
}
