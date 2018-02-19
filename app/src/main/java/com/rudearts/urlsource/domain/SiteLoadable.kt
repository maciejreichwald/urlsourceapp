package com.rudearts.urlsource.domain

import com.rudearts.urlsource.model.local.Site
import io.reactivex.Single

interface SiteLoadable {

    fun loadSite(url:String):Single<Site>
}