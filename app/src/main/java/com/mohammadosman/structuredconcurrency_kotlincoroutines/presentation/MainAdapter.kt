package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mohammadosman.structuredconcurrency_kotlincoroutines.R
import com.mohammadosman.structuredconcurrency_kotlincoroutines.domain.Comment

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_comment_main,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when (holder) {
            is MainViewHolder -> {
                val itm = differ.currentList[position]
                holder.bind(itm)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(lst: List<Comment>) {
        differ.submitList(lst)
    }

    inner class MainViewHolder(
        private val itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: Comment) {

        }
    }


    private val comparator =
        object : DiffUtil.ItemCallback<Comment>() {

            override fun areItemsTheSame(oldItem: Comment, newItem: Comment)
                    : Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment)
                    : Boolean {
                return oldItem.id == newItem.id
            }
        }

    private val differ =
        AsyncListDiffer(this, comparator)

}