package com.rudearts.urlsource.di.main

import com.rudearts.urlsource.di.ActivityScope
import com.rudearts.urlsource.di.app.AppComponent
import com.rudearts.urlsource.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(MainModule::class)])
interface MainComponent {
    fun inject(activity: MainActivity)
}