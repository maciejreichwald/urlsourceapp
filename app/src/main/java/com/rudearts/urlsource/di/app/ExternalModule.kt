package com.rudearts.urlsource.di.app

import android.content.Context
import com.rudearts.urlsource.api.RestAPI
import com.rudearts.urlsource.api.RestController
import com.rudearts.urlsource.database.DatabaseController
import com.rudearts.urlsource.model.greendao.DaoSession
import com.rudearts.urlsource.util.ExternalMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExternalModule(private val context:Context) {

    @Provides
    @Singleton
    fun provideDatabase(): DaoSession  = DatabaseController(context).session


    @Provides
    @Singleton
    fun provideRestApi():RestAPI = RestController().restApi

    @Provides
    fun provideExternalMapper() = ExternalMapper()

}