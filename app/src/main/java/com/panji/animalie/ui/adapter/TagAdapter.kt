package com.panji.animalie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.databinding.TagsBinding
import com.panji.animalie.model.Tag

class TagAdapter(private val data: List<Tag>) :
    RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: TagsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: Tag) = with(binding) {

            tagName.text = tag.name_tag
            tagCounter.text = tag.tag_counter.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )
            : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TagsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


}