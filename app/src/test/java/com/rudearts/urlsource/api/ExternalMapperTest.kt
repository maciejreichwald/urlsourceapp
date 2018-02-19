package com.rudearts.urlsource.api

import android.content.Context
import com.nhaarman.mockitokotlin2.*
import com.rudearts.urlsource.model.greendao.SiteExternal
import com.rudearts.urlsource.model.local.Site
import com.rudearts.urlsource.util.ExternalMapper
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExternalMapperTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var context:Context

    @InjectMocks @Spy lateinit var mapper: ExternalMapper

    @Test
    fun site2local() {
        val external = mock<SiteExternal> {
            on { url } doReturn "none"
            on { source } doReturn "none"
        }

        val site = mapper.site2local(external)

        assertEquals(external.source, site.source)
    }

    @Test
    fun site2external() {
        val site = mock<Site> {
            on { url } doReturn "none"
            on { source } doReturn "none"
        }

        val external = mapper.site2external(site)

        assertEquals(site.url, external.url)
    }
}