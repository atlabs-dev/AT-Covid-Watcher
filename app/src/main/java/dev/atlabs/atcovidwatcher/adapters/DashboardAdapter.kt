package dev.atlabs.atcovidwatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.atlabs.atcovidwatcher.R
import dev.atlabs.atcovidwatcher.data.models.DashboardItem
import kotlinx.android.synthetic.main.item_dashboard_layout.view.*

class DashboardAdapter(private val context: Context) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    private lateinit var items: List<DashboardItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_dashboard_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = if (::items.isInitialized) items.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(comments: List<DashboardItem>) {
        this.items = comments
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.tv_title
        private val tvValue: TextView = view.tv_unit
        private val tvCases: TextView = view.tv_cases

        fun bind(item: DashboardItem) {
            if(item.name.equals("Fatality Rate", true) || item.name.equals("Recovery Rate", true))
            tvTitle.text = item.name
            tvValue.text = item.value
            tvCases.visibility = View.INVISIBLE
        }
    }
}
