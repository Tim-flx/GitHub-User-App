package com.dicoding.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.ui.detail.DetailActivity
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import com.squareup.picasso.Picasso

class ListUserAdapter(private val listUser: List<ItemsItem?>?) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: ItemRowUserBinding = ItemRowUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser?.size ?: 0
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        listUser?.getOrNull(position)?.let { (name, photo) ->
            Picasso.get().load(photo).into(holder.binding.imgItemPhoto)
            holder.binding.tvItemName.text = name
        }
        holder.binding.root.setOnClickListener {
            val intentDetail = Intent(holder.binding.root.context, DetailActivity::class.java)
            intentDetail.putExtra("key_user", listUser?.get(holder.adapterPosition))
            holder.binding.root.context.startActivity(intentDetail)
        }
    }

    inner class ListViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root)
}
