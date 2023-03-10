package com.xxx.thachraucau.launcher.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.databinding.AppIconBinding
import com.xxx.thachraucau.launcher.databinding.CustomLibraryItemBinding
import com.xxx.thachraucau.launcher.databinding.LibraryMoreIconBinding
import com.xxx.thachraucau.launcher.getScreenSize
import com.xxx.thachraucau.launcher.model.AppInfo
import com.xxx.thachraucau.launcher.model.LibraryInfo

class LibraryAdapter(private val mActivity: Activity) :
    RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    var mListItem: ArrayList<LibraryInfo> = ArrayList()

    val mScreenSize by lazy {
        mActivity.getScreenSize()
    }

    fun setListItems(listItem: ArrayList<LibraryInfo>) {
        mListItem = listItem
        notifyDataSetChanged()
    }

    inner class LibraryViewHolder(private val mRootView: View) : ViewHolder(mRootView) {
        fun bindItem(info: LibraryInfo) {
            val binding = CustomLibraryItemBinding.bind(mRootView)
            binding.tvCategory.text = "${info.mCategoryName} ${info.mListApp.size}"
            val gridParam = binding.gridLibraryCustom.layoutParams
            gridParam.width = (mScreenSize.mWidth / 2) * 4 / 5
            gridParam.height = gridParam.width
            binding.gridLibraryCustom.layoutParams = gridParam

            for (index in 0 until 3) {
                if (index < info.mListApp.size) {
                    binding.gridLibraryCustom.addView(
                        getIconView(
                            binding.gridLibraryCustom,
                            info.mListApp.get(index), false
                        )
                    )
                }
            }
            if (info.mListApp.size == 4) {
                binding.gridLibraryCustom.addView(
                    getIconView(
                        binding.gridLibraryCustom,
                        info.mListApp.last(), false
                    )
                )
            } else if (info.mListApp.size > 4) {
                binding.gridLibraryCustom.addView(getMoreView(binding.gridLibraryCustom, info))
            }

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun getIconView(parent: ViewGroup, appInfo: AppInfo, isSmall: Boolean): View {
            val binding =
                AppIconBinding.inflate(LayoutInflater.from(mActivity), parent, false).apply {
                    imCustom.setImageDrawable(appInfo.icon)
                    imCustom.background =
                        mActivity.getDrawable(if (isSmall) R.drawable.shape_effect_small else R.drawable.shape_effect)
                }

            val param = binding.imCustom.layoutParams
            param.width = (parent.layoutParams.width / 2) * 3 / 4
            param.height = param.width
            binding.imCustom.layoutParams = param
            if(!isSmall){
                binding.root.setOnClickListener {
                    val launchIntent =
                        mActivity.packageManager.getLaunchIntentForPackage(appInfo.packageName)
                    mActivity.startActivity(launchIntent)
                }
            }
            return binding.root
        }

        private fun getMoreView(parent: ViewGroup, info: LibraryInfo): View {
            val binding =
                LibraryMoreIconBinding.inflate(LayoutInflater.from(mActivity), parent, false)
            val param = binding.girdMoreIcon.layoutParams
            param.width = (parent.layoutParams.width / 2) * 3 / 4
            param.height = param.width
            binding.girdMoreIcon.layoutParams = param
            for (index in 3 until info.mListApp.size) {
                if (index < 7) {
                    binding.girdMoreIcon.addView(
                        getIconView(
                            binding.girdMoreIcon,
                            info.mListApp.get(index), true
                        )
                    )
                }
            }
            return binding.root
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_library_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bindItem(mListItem.get(position))
    }

    override fun getItemCount() = mListItem.size
}