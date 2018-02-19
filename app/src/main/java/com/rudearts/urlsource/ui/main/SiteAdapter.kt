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

    private val inflater by lazy {  LayoutInflater.from(context) }

    private var items: List<Site> = ArrayList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val site = getItem(position)
        val binding = createViewBinding()
        binding.site = site
        return binding.root
    }

    override fun getItem(position:Int) = items[position]

    override fun getCount() = items.size

    fun updateItems(items:List<Site>) {
        this.items = items
        notifyDataSetChanged()
    }

    internal fun createViewBinding(): SiteItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.site_item, null, false)

    internal fun updateViewBinding(convertView: View) =
            DataBindingUtil.getBinding<SiteItemBinding>(convertView)
}