package com.dicoding.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.dicoding.githubuser.databinding.ItemRowUserBinding

class ListFollowAdapter(private val listFollow: List<ItemsItem?>?) : RecyclerView.Adapter<ListFollowAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        listFollow?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return listFollow?.size ?: 0
    }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ItemsItem) {
            binding.tvItemName.text = user.login
            Picasso.get().load(user.avatarUrl).into(binding.imgItemPhoto)
        }
    }
}
