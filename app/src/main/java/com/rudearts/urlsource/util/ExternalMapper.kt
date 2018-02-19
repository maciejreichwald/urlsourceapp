package com.rudearts.urlsource.util

import com.rudearts.urlsource.model.greendao.SiteExternal
import com.rudearts.urlsource.model.local.Site

class ExternalMapper {

    fun site2local(site:SiteExternal) = with(site) {
        Site(url, source)
    }

    fun site2external(site:Site) = with(site) {
        val external = SiteExternal()
        external.url = url
        external.source = source
        return@with external
    }

}