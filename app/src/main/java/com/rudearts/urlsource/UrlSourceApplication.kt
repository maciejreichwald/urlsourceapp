package com.rudearts.urlsource

import android.app.Application
import com.rudearts.urlsource.di.app.AppComponent
import com.rudearts.urlsource.di.app.DaggerAppComponent
import com.rudearts.urlsource.di.app.DomainModule
import com.rudearts.urlsource.di.app.ExternalModule

class UrlSourceApplication : Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        createComponent()
    }

    internal fun createComponent() {
        appComponent =  DaggerAppComponent.builder()
                .domainModule(DomainModule())
                .externalModule(ExternalModule(this))
                .build()
    }
}