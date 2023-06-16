package com.bangkit.section.ui.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.section.data.response.forum.ListForum
import com.bangkit.section.databinding.RvPostinganBinding
import com.bangkit.section.util.Utilities
import com.bumptech.glide.Glide

class ForumAdapter : PagingDataAdapter<ListForum, ForumAdapter.ForumViewHolder>(diffCallback) {
    private var clickListener: OnAdapterClickCallback? = null

    fun setOnItemClickCallback(onAdapterClickCallback: OnAdapterClickCallback) {
        this.clickListener = onAdapterClickCallback
    }

    inner class ForumViewHolder(val binding: RvPostinganBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forum: ListForum) {
            forum.let { forum ->
                binding.apply {
                    tvName.text = forum.full_name
                    tvForumDcs.text = forum.body_dcs
                    time.text = forum.publish_date_dcs?.let { Utilities.formatTimeDifference(it) }
                    Glide.with(itemView)
                        .load(forum.profile_picture)
                        .fitCenter()
                        .into(avatar)
                    comment.setOnClickListener {
                        clickListener?.onItemClick(forum)
                    }
                }
            }
        }
    }

    interface OnAdapterClickCallback {
        fun onItemClick(data: ListForum)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val forum = getItem(position)
        Log.e(TAG, "Forum data: $forum")
        if (forum != null) {
            holder.bind(forum)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view = RvPostinganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForumViewHolder(view)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ListForum>() {
            override fun areItemsTheSame(oldItem: ListForum, newItem: ListForum): Boolean {
                return oldItem.id_dcs == newItem.id_dcs
            }

            override fun areContentsTheSame(oldItem: ListForum, newItem: ListForum): Boolean {
                return oldItem == newItem
            }
        }
    }
}