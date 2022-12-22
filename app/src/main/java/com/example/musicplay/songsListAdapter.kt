package com.example.musicplay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplay.databinding.ListItemBinding

class songsListAdapter(val context: Context, val songsNameList: ArrayList<String>, val songsDataList: ArrayList<String>): RecyclerView.Adapter<ListItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.songNameTV.setSelected(true)
        holder.songNameTV.text = songsNameList[position]
        holder.songCardView.setOnClickListener {
            val intent = Intent(holder.binding.root.context, PlayerActivity::class.java)
            intent.putExtra("Data", songsDataList[position])
            intent.putExtra("Name", songsNameList[position])
            intent.putExtra("Position", position)

            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return songsNameList.size
    }
}

class ListItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
    val songNameTV = binding.songNameTV
    val songCardView = binding.songCardView
}