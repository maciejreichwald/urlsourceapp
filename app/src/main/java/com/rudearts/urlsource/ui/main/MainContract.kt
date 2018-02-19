package com.rudearts.urlsource.ui.main

import com.rudearts.urlsource.model.LoadingState
import com.rudearts.urlsource.model.local.Site
import io.reactivex.Single

interface MainContract {

    interface View {
        fun updateLoadingState(state:LoadingState)
        fun updateSites(sites: List<Site>)
        fun updateSource(source:String)
        fun showMessage(message: String)
        fun hideKeyboard()
    }

    interface Presenter {
        fun loadSource(url:String)
        fun loadUrlHints(urlPart:String)
    }
}