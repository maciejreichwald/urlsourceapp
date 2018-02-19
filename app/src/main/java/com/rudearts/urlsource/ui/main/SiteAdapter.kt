package com.rudearts.urlsource.ui.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.rudearts.urlsource.R
import com.rudearts.urlsource.databinding.SiteItemBinding
import com.rudearts.urlsource.model.local.Site

class SiteAdapter(context:Context) : ArrayAdapter<Site>(context, R.layout.site_item) {

    private var sites:List<Site> = ArrayList()
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val site = sites[position]
        val binding = createViewBinding()
        binding.site = site
        return binding.root
    }

    override fun getItem(position:Int) = sites[position]

    override fun getCount() = sites.size

    fun updateItems(items:List<Site>) {
        sites = items
        notifyDataSetChanged()
    }

    internal fun createViewBinding(): SiteItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.site_item, null, false)
}