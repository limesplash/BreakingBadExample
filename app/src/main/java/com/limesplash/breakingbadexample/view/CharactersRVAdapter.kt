package com.limesplash.breakingbadexample.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limesplash.breakingbadexample.R
import com.limesplash.breakingbadexample.model.Character
import kotlinx.android.synthetic.main.item_list_content.view.*

class CharactersRVAdapter ( private val values: List<Character> = emptyList(),
                            private val itemClickListener: (clicked: Character) -> Unit): RecyclerView.Adapter<CharactersRVAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.name_txt
        val image: ImageView = view.image
    }

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            itemClickListener(v.tag as Character)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }

        Glide.with(holder.itemView.context)
            .load(item.imageURL)
            .into(holder.image)
    }

    override fun getItemCount() = values.size

}