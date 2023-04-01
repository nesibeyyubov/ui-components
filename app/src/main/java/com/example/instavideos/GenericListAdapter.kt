package com.example.instavideos

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class GenericListAdapter<Binding : ViewBinding, Model>(
    val inflate: (Context, ViewGroup, Boolean) -> Binding,
    val onBind: (Model, Int, Binding) -> Unit,
) :
    RecyclerView.Adapter<GenericListAdapter.GenericViewHolder<Binding>>() {

    protected val items = mutableListOf<Model>()

    fun submitItems(newItems: List<Model>) {
        this.items.clear()
        this.items.addAll(newItems)
        notifyDataSetChanged()
    }

    class GenericViewHolder<Binding : ViewBinding>(val binding: Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<Binding> {
        return GenericViewHolder(inflate(parent.context, parent, false))
    }

    override fun onBindViewHolder(holder: GenericViewHolder<Binding>, position: Int) {
        onBind(items[actualPosition(position)], actualPosition(position), holder.binding)
    }

    open fun itemCount() = items.size

    open fun actualPosition(position: Int) = position

    override fun getItemCount() = itemCount()
}