package com.xxx.thachraucau.launcher.adapter

import android.appwidget.AppWidgetProviderInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxx.thachraucau.launcher.PackageMgr
import com.xxx.thachraucau.launcher.R

class WidgetNamesAdapter : RecyclerView.Adapter<WidgetNamesAdapter.WidgetNamesViewHolder>() {
    private var mItems: List<String> = ArrayList<String>()
    private var mClickItem: ClickItem? = null

    inner class WidgetNamesViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val mIcon = itemView.findViewById<ImageView>(R.id.img_icon_app_widget)
        private val mAppName = itemView.findViewById<TextView>(R.id.tv_app_name_widget)
        private val mDivider = itemView.findViewById<View>(R.id.divider_app_widget)
        private val mContext = itemView.context
        fun bindView(info: String, position: Int) {
            if (position == mItems.size - 1) mDivider.visibility = View.GONE
            loadAppIcon(info)
            mAppName.text = PackageMgr.getAppName(mContext, info)
            itemView.setOnClickListener {
                mClickItem?.onClickItem(packageName = info)
            }
        }

        private fun loadAppIcon(info: String) {
            val icon = PackageMgr.getAppIcon(mContext, info)
            icon?.let { mIcon.setImageDrawable(icon) }
                ?: mIcon.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WidgetNamesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_list_widget, parent, false)
    )

    override fun onBindViewHolder(holder: WidgetNamesViewHolder, position: Int) {
        holder.bindView(mItems[position], position)
    }

    override fun getItemCount(): Int = mItems.size

    fun setItems(items: List<String>) {
        mItems = items
        notifyDataSetChanged()
    }

    fun setOnItemClick(itemClick: ClickItem) {
        mClickItem = itemClick
        Log.d(TAG, "setOnItemClick: ")
    }

    interface ClickItem {
        fun onClickItem(packageName: String)
    }

    companion object {
        private const val TAG = "WidgetNamesAdapter"
    }
}