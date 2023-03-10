package com.xxx.thachraucau.launcher.adapter

import android.appwidget.AppWidgetProviderInfo
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxx.thachraucau.launcher.R

class WidgetDetailAdapter : RecyclerView.Adapter<WidgetDetailAdapter.WidgetDetailViewHolder>() {

    private var mListInfo: List<AppWidgetProviderInfo> = arrayListOf()

    inner class WidgetDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mAppName: TextView = itemView.findViewById(R.id.tv_widget_name)
        private val mWidgetSize: TextView = itemView.findViewById(R.id.tv_widget_size)
        private val mWidgetThumb: ImageView = itemView.findViewById(R.id.widget_preview)

        fun bindView(info: AppWidgetProviderInfo, position: Int) {

            val drawable = info.loadPreviewImage(itemView.context, DisplayMetrics.DENSITY_HIGH)
            drawable.let {
                mWidgetThumb.setImageDrawable(it)
            }
            mAppName.text = info.label
            mWidgetSize.text = "${info.minHeight}dp x ${info.minWidth}dp"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetDetailViewHolder =
        WidgetDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_single_widget, parent, false)
        )

    override fun onBindViewHolder(
        holder: WidgetDetailAdapter.WidgetDetailViewHolder,
        position: Int
    ) {
        holder.bindView(mListInfo.get(position), position)
    }

    override fun getItemCount(): Int {
        return mListInfo.size
    }

    fun setData(newList: List<AppWidgetProviderInfo>) {
        mListInfo = newList
        notifyDataSetChanged()
    }
}