package com.example.smartwatchdarkmodeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class adapter(
    private val context: Context,
    private val smartWatche: List<SmartWatch>,
): ArrayAdapter<SmartWatch>(context,0, smartWatche) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.items, parent,false)

        val watch = getItem(position)

        val imageV = view.findViewById<ImageView>(R.id.imgItem)
        val name = view.findViewById<TextView>(R.id.nameItem)
        val price = view.findViewById<TextView>(R.id.priceItem)
        val waterResistant = view.findViewById<CheckBox>(R.id.checkItem)

        watch?.let {
            Glide.with(context)
                .load(it.imageURL)
                .into(imageV)

            name.text = it.name
            price.text = "${it.price} Dh"
            waterResistant.isChecked = it.isWaterResistant
        }

        return view
    }
}