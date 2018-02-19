package com.rudearts.urlsource.domain

import com.rudearts.urlsource.model.local.Site
import io.reactivex.Single

interface UrlHintsLoadable {

    fun loadUrlHints(urlPart:String): Single<List<Site>>
}