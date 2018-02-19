package com.rudearts.urlsource.domain

import com.nhaarman.mockitokotlin2.verify
import com.rudearts.urlsource.api.RestAPI
import com.rudearts.urlsource.model.greendao.DaoSession
import com.rudearts.urlsource.util.ExternalMapper
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit


class LoadSiteUseCaseTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var restApi: RestAPI
    @Mock lateinit var database: DaoSession
    @Mock lateinit var mapper: ExternalMapper

    @InjectMocks lateinit var siteUseCase: LoadSiteUseCase

    @Test
    fun loadSite_WithoutHttp() {
        siteUseCase.loadSite("lolo")
                .subscribe()

        verify(restApi).loadWebsiteSource("http://lolo")
    }

    @Test
    fun loadSite_WithHttp() {
        siteUseCase.loadSite("http://lolo")
                .subscribe()

        verify(restApi).loadWebsiteSource("http://lolo")
    }
}
