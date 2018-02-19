package com.rudearts.urlsource.di.main

import android.content.Context
import com.rudearts.urlsource.di.ActivityScope
import com.rudearts.urlsource.domain.SiteLoadable
import com.rudearts.urlsource.domain.UrlHintsLoadable
import com.rudearts.urlsource.ui.main.MainContract
import com.rudearts.urlsource.ui.main.MainPresenter
import com.rudearts.urlsource.ui.main.SiteAdapter
import dagger.Module
import dagger.Provides


@Module
class MainModule(private val context:Context, private val view:MainContract.View) {

    @Provides
    @ActivityScope
    fun providesMainView() = view

    @Provides
    @ActivityScope
    fun providesContext() = context

    @Provides
    @ActivityScope
    fun providesMainPresenter(view:MainContract.View,
                              siteUseCase: SiteLoadable,
                              hintUseCase: UrlHintsLoadable):MainContract.Presenter = MainPresenter(view,siteUseCase, hintUseCase)

    @Provides
    @ActivityScope
    fun providesSiteAdapter() = SiteAdapter(context)
}