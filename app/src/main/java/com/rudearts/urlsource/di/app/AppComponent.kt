package com.rudearts.urlsource.di.app

import com.rudearts.urlsource.domain.SiteLoadable
import com.rudearts.urlsource.domain.UrlHintsLoadable
import com.rudearts.urlsource.util.ExternalMapper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, ExternalModule::class])
interface AppComponent {
    val siteUseCase: SiteLoadable
    val hintUseCase: UrlHintsLoadable
    val mapper: ExternalMapper
}