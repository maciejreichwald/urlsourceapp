package com.rudearts.urlsource.di.app

import com.rudearts.urlsource.api.RestAPI
import com.rudearts.urlsource.domain.LoadSiteUseCase
import com.rudearts.urlsource.domain.LoadUrlHintsUseCase
import com.rudearts.urlsource.domain.SiteLoadable
import com.rudearts.urlsource.domain.UrlHintsLoadable
import com.rudearts.urlsource.model.greendao.DaoSession
import com.rudearts.urlsource.util.ExternalMapper
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun providesLoadUrlHintsUseCase(
            database: DaoSession,
            mapper: ExternalMapper): UrlHintsLoadable = LoadUrlHintsUseCase(database, mapper)

    @Provides
    fun providesLoadTracksUseCase(
            restApi: RestAPI,
            database: DaoSession,
            mapper: ExternalMapper): SiteLoadable = LoadSiteUseCase(restApi, database, mapper)


}