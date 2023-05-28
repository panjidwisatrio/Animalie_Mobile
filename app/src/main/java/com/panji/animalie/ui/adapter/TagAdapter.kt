package com.panji.animalie.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.R
import com.panji.animalie.databinding.TagsBinding
import com.panji.animalie.model.Tag
import com.panji.animalie.ui.tag.DetailTagActivity
import com.panji.animalie.util.Constanta.EXTRA_SLUG

class TagAdapter(private val context: Context?) :
    ListAdapter<Tag, TagAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(
        private val binding: TagsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: Tag) = with(binding) {

            tagName.text = tag.name_tag
            tagCounter.text = context?.getString(R.string.tag_counter, tag.tag_counter.toString())

            //send slug
            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(itemView.context, DetailTagActivity::class.java)
                        .putExtra(EXTRA_SLUG, tag.slug)
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            TagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        bind(getItem(position))
    }
}