package com.rudearts.urlsource.domain

import com.rudearts.urlsource.model.greendao.DaoSession
import com.rudearts.urlsource.model.greendao.SiteExternalDao
import com.rudearts.urlsource.model.local.Site
import com.rudearts.urlsource.util.ExternalMapper
import io.reactivex.Single
import javax.inject.Inject

class LoadUrlHintsUseCase  @Inject constructor(
        internal val database:DaoSession,
        internal val mapper:ExternalMapper) : UrlHintsLoadable
{

    override fun loadUrlHints(urlPart: String): Single<List<Site>> =
            Single.create { subscriber ->
                val sites = database.siteExternalDao.queryBuilder()
                        .where(SiteExternalDao.Properties.Url.like("%$urlPart%"))
                        .orderAsc(SiteExternalDao.Properties.Url)
                        .list()
                subscriber.onSuccess(sites.map { mapper.site2local(it) })
            }

}