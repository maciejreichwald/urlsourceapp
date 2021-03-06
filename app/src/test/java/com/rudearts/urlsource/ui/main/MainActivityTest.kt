package com.rudearts.urlsource.ui.main

import android.view.View
import br.tiagohm.codeview.CodeView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rudearts.urlsource.extentions.visible
import com.rudearts.urlsource.model.LoadingState
import com.rudearts.urlsource.model.local.Site
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

class MainActivityTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var presenter: MainContract.Presenter

    @Mock lateinit var adapter: SiteAdapter

    @InjectMocks @Spy lateinit var activity:MainActivityMock

    private val progress = mock<View> {}
    private val lblSource = mock<CodeView> {}
    private val emptyView = mock<View> {}

    @Before
    fun setup() {
        whenever(activity.progressBar).thenReturn(progress)
        whenever(activity.lblSource).thenReturn(lblSource)
        whenever(activity.emptyView).thenReturn(emptyView)
    }


    @Test
    fun updateUrlHints() {
        val items:List<Site> = mock {}
        activity.updateSites(items)

        verify(adapter, times(1)).updateItems(items)
    }

    @Test
    fun updateLoadingState_WhenLoading() {
        activity.updateLoadingState(LoadingState.LOADING)

        verify(progress, times(1)).visible = true
        verify(emptyView, times(1)).visible = false
        verify(lblSource, times(1)).visible = false
    }

    @Test
    fun updateLoadingState_WhenNoResults() {
        activity.updateLoadingState(LoadingState.NO_RESULTS)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = true
        verify(lblSource, times(1)).visible = false
    }

    @Test
    fun updateLoadingState_WhenShowResults() {
        activity.updateLoadingState(LoadingState.SHOW_RESULTS)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = false
        verify(lblSource, times(1)).visible = true
    }

    /**
     * Ok, I gave up here - I had some problems with mocking activity
     * it was holiday, it was late and this "temporary" fix worked like a charm...
     */
    class MainActivityMock : MainActivity() {
        override fun <T : View?> findViewById(id: Int): T {
            return null as T
        }
    }
}