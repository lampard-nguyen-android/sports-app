package com.sports.app.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sports.app.R
import com.sports.app.data.domain.Team
import com.sports.app.databinding.ItemTeamBinding

class TeamsAdapter(
    private var itemClicked: ((Team) -> Unit)? = null,
) : ListAdapter<Team, TeamsAdapter.ViewHolder>(TeamDiffUtil) {

    class ViewHolder(val binding: ItemTeamBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return
        holder.binding.textTitle.text = model.name
        Glide.with(holder.binding.root.context).load(model.logo)
            .error(R.drawable.ic_teams)
            .diskCacheStrategy(
                DiskCacheStrategy.ALL,
            ).into(holder.binding.imageLogo)

        holder.itemView.setOnClickListener {
            itemClicked?.invoke(model)
        }
    }

    object TeamDiffUtil : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }
}
