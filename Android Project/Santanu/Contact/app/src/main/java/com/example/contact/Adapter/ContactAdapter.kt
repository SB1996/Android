package com.example.contact.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.R
import android.content.Context
import android.view.LayoutInflater
import com.example.contact.Data.ContactUserData
import java.io.InputStream
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.IOException


class ContactAdapter
    internal constructor(private val mDataList: List<ContactUserData>?, private val context: Context? = null):
    RecyclerView.Adapter<ContactAdapter.InnerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_container, parent, false)
        return InnerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList!!.size
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val contactUserData: ContactUserData = mDataList!!.get(position)

        holder.nameTv.text = contactUserData.name
        holder.numberTv.text = "+91 ${contactUserData.contactNumber.toString()}"

        var inputStream: InputStream? = null
        try {
            inputStream = context!!.getAssets().open(contactUserData.profileImg!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            holder.PrifileIv.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (inputStream == null) {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }


    inner class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var PrifileIv: ImageView
        internal var nameTv: TextView
        internal var numberTv: TextView
        internal var dotIconIv: ImageView

        init{
            PrifileIv = itemView.findViewById(R.id.iv_profile)
            nameTv = itemView.findViewById(R.id.tv_name)
            numberTv = itemView.findViewById(R.id.tv_number)
            dotIconIv = itemView.findViewById(R.id.iv_dot)
        }


    }
}