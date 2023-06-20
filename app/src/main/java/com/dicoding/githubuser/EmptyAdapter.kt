package com.dicoding.githubuser

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EmptyAdapter : RecyclerView.Adapter<EmptyAdapter.EmptyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyHolder {
        return EmptyHolder(View(parent.context))
    }

    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 0
    }

    inner class EmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
