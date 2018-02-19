package com.rudearts.urlsource.domain

import com.rudearts.urlsource.api.RestAPI
import com.rudearts.urlsource.model.greendao.DaoSession
import com.rudearts.urlsource.model.greendao.SiteExternal
import com.rudearts.urlsource.model.greendao.SiteExternalDao
import com.rudearts.urlsource.model.local.Site
import com.rudearts.urlsource.util.ExternalMapper
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.ResponseBody
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.PrettyXmlSerializer
import retrofit2.Response
import javax.inject.Inject


class LoadSiteUseCase @Inject constructor(
        internal val restApi: RestAPI,
        internal val database: DaoSession,
        internal val mapper: ExternalMapper) : SiteLoadable {

    companion object {
        private const val HTTP = "http://"
    }

    internal lateinit var subscriber: SingleEmitter<Site>
    internal lateinit var url: String

    override fun loadSite(url: String): Single<Site> =
            Single.create { subscriber ->
                this.subscriber = subscriber
                this.url = updateUrl(url)
                restApi.loadWebsiteSource(this.url)
                        .subscribe(
                                { response -> onUrlLoaded(response) },
                                { error -> onWebError(error) }
                        )
            }

    private fun updateUrl(url: String) = when (url.contains(HTTP, true)) {
        true -> url
        false -> "$HTTP$url"
    }

    internal fun onUrlLoaded(response: Response<ResponseBody>) {
        when (response.isSuccessful) {
            true -> handleResponse(response)
            false -> onWebError(Error(response.errorBody()?.string()))
        }
    }

    internal fun onWebError(error: Throwable) {
        if (url.isEmpty()) {
            subscriber.onError(error)
            return
        }

        val site = loadSiteByUrl()
        val url = site?.url

        when (url) {
            null -> subscriber.onError(error)
            else -> subscriber.onSuccess(prepareSite(site))
        }
    }

    internal fun handleResponse(response: Response<ResponseBody>) {
        val body = response.body()?.string() ?: ""
        var site = loadSiteByUrl()

        when(site) {
            null -> site = mapper.site2external(Site(url, body))
            else -> site.source = body
        }

        database.siteExternalDao.insertOrReplace(site)

        subscriber.onSuccess(prepareSite(site))
    }

    fun prepareSite(site:SiteExternal):Site {
        val cleaner = HtmlCleaner()
        val rootTagNode = cleaner.clean(site.source)

        val cleanerProperties = cleaner.properties
        cleanerProperties.isOmitXmlDeclaration = true

        val xmlSerializer = PrettyXmlSerializer(cleanerProperties)
        val html = xmlSerializer.getAsString(rootTagNode)

        site.source = html

        return mapper.site2local(site)
    }

    internal fun loadSiteByUrl() = database.siteExternalDao.queryBuilder()
            .where(SiteExternalDao.Properties.Url.eq(url))
            .unique()
}