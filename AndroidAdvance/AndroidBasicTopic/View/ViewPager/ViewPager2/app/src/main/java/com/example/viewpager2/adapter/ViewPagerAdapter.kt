package com.example.viewpager2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager2.R
import kotlinx.android.synthetic.main.viewpager_layout.view.*

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.PagerView>() {

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerView {
        return PagerView(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_layout, parent, false))
    }

    override fun onBindViewHolder(holder: PagerView, position: Int) {
        holder.itemView.run {
            if(position == 0){
                tv_title.text = "Page : $position"
            }
            if(position == 1){
                tv_title.text = "Page : $position"
            }
            if(position == 2){
                tv_title.text = "Page : $position"
            }
            if(position == 3){
                tv_title.text = "Page : $position"
            }
            if(position == 4){
                tv_title.text = "Page : $position"
            }
            if(position == 5){
                tv_title.text = "Page : $position"
            }
        }
    }


    inner class PagerView(itemView: View) : RecyclerView.ViewHolder(itemView)
}