package com.sports.app.main.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sports.app.data.domain.Match
import com.sports.app.data.domain.MatchType
import com.sports.app.databinding.ItemMatchBinding
import com.sports.app.utils.DateUtils
import com.sports.app.utils.DateUtils.toDate
import com.sports.app.utils.DateUtils.toFormattedString

class MatchAdapter(
    private var itemHighlightClicked: ((Match) -> Unit)? = null,
    private var itemReminderClicked: ((Match) -> Unit)? = null,
) : ListAdapter<Match, MatchAdapter.ViewHolder>(MatchDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return
        holder.bind(model)
    }

    inner class ViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Match) = with(binding) {
            textTeamHome.text = item.home
            textTeamAway.text = item.away
            when (item.type) {
                MatchType.PREVIOUS -> {
                    textWinnerHome.isVisible = item.winner == item.home
                    textWinnerAway.isVisible = item.winner == item.away

                    textTeamHome.setTypeface(
                        null,
                        if (textWinnerHome.isVisible) Typeface.BOLD else Typeface.NORMAL,
                    )

                    textTeamAway.setTypeface(
                        null,
                        if (textWinnerAway.isVisible) Typeface.BOLD else Typeface.NORMAL,
                    )

                    imageHighlight.isInvisible = item.highlights.isNullOrBlank()
                    imageHighlight.setOnClickListener { itemHighlightClicked?.invoke(item) }
                }
                MatchType.UPCOMING -> {
                    textWinnerHome.isVisible = false
                    textWinnerAway.isVisible = false
                    imageTimer.isInvisible = false
                    imageTimer.setOnClickListener { itemReminderClicked?.invoke(item) }
                }
            }
            item.date.toDate()?.let {
                textDate.text = it.toFormattedString(DateUtils.DD_MMM_FORMAT)
                textTime.text = it.toFormattedString(DateUtils.HH_MM_FORMAT)
            }
        }
    }

    object MatchDiffUtil : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }
    }
}
