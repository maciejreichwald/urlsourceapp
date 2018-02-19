package com.rudearts.urlsource.ui.main

import com.rudearts.urlsource.domain.SiteLoadable
import com.rudearts.urlsource.domain.UrlHintsLoadable
import com.rudearts.urlsource.model.LoadingState
import com.rudearts.urlsource.model.local.Site
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
        internal val view:MainContract.View,
        internal val siteUseCase: SiteLoadable,
        internal val hintsUseCase: UrlHintsLoadable) : MainContract.Presenter {

    override fun loadSource(url:String) {
        view.updateLoadingState(LoadingState.LOADING)

        siteUseCase.loadSite(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onItemLoaded(it)},
                        {onError(it)})
    }

    override fun loadUrlHints(urlPart: String) {
        hintsUseCase.loadUrlHints(urlPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onHintsLoaded(it)},
                        { onHintsError(it) }
                )
    }

    private fun onHintsError(error: Throwable) {
        error.printStackTrace()
        view.updateSites(emptyList())
    }

    private fun onHintsLoaded(sites: List<Site>) {
        view.updateSites(sites)
    }

    internal fun onError(error: Throwable) {
        error.printStackTrace()

        updateTracksAndLoadingState(Site())
        view.showMessage(error.toString())
    }

    internal fun onItemLoaded(site:Site) {
        updateTracksAndLoadingState(site)
    }

    internal fun updateTracksAndLoadingState(site:Site) {
        when(site.source.isEmpty()) {
            true -> view.updateLoadingState(LoadingState.NO_RESULTS)
            false -> view.updateSource(site.source)
        }
    }
}