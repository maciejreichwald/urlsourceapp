package com.rudearts.urlsource.ui.main

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import br.tiagohm.codeview.CodeView
import br.tiagohm.codeview.Language
import br.tiagohm.codeview.Theme
import com.rudearts.urlsource.R
import com.rudearts.urlsource.UrlSourceApplication
import com.rudearts.urlsource.di.main.DaggerMainComponent
import com.rudearts.urlsource.di.main.MainModule
import com.rudearts.urlsource.extentions.bind
import com.rudearts.urlsource.extentions.visible
import com.rudearts.urlsource.model.LoadingState
import com.rudearts.urlsource.model.local.Site
import com.rudearts.urlsource.ui.ToolbarActivity
import com.rudearts.urlsource.util.BaseTextWatcher
import javax.inject.Inject

/**
 * Yes, it is open, you can see in MainActivityTest bottom comment why
 */
open class MainActivity : ToolbarActivity(), MainContract.View {

    @Inject internal lateinit var presenter: MainContract.Presenter
    @Inject internal lateinit var adapter: SiteAdapter

    internal val inputAddress: AppCompatAutoCompleteTextView by bind(R.id.address_input)
    internal val lblSource: CodeView by bind(R.id.source_lbl)
    internal val progressBar: View by bind(R.id.progress_bar)
    internal val emptyView: View by bind(R.id.empty_view)
    internal val btnLoad: View by bind(R.id.load_btn)

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()
        setupViews()

        updateLoadingState(LoadingState.NO_RESULTS)
    }

    internal fun inject() {
        createComponent().apply {
            this.inject(this@MainActivity)
        }
    }

    internal fun createComponent() = DaggerMainComponent.builder()
            .appComponent(UrlSourceApplication.appComponent)
            .mainModule(MainModule(this, this))
            .build()

    internal fun setupTitle() {
        title = getString(R.string.app_name)
    }

    internal fun setupViews() {
        setupInput()
        setupLabel()
        setupBtn()
    }

    internal fun setupLabel() {
        lblSource.webViewClient = createWebViewClient()
    }

    private fun createWebViewClient() = object: WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            updateLoadingState(LoadingState.SHOW_RESULTS)
        }
    }

    internal fun setupBtn() {
        btnLoad.setOnClickListener { onBtnLoadClick() }
    }

    internal fun onBtnLoadClick() {
        presenter.loadSource(inputAddress.text.toString())
        hideKeyboard()
    }

    internal fun setupInput() {
        inputAddress.setAdapter(adapter)
        inputAddress.addTextChangedListener(object: BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.loadUrlHints(s.toString())
            }
        })
        inputAddress.setOnEditorActionListener { _, actionId, _ -> onEditorAction(actionId) }
    }

    internal fun onEditorAction(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            onBtnLoadClick()
        }
        return false
    }

    override fun updateLoadingState(state:LoadingState) {
        progressBar.visible = state == LoadingState.LOADING
        lblSource.visible = state == LoadingState.SHOW_RESULTS
        emptyView.visible = state == LoadingState.NO_RESULTS
    }

    override fun updateSites(sites: List<Site>) {
        adapter.updateItems(sites)
    }

    override fun updateSource(source: String) {
        lblSource.setCode(source)
                .setTheme(Theme.TOMORROW_NIGHT_BLUE)
                .setLanguage(Language.HTML)
                .setWrapLine(true)
                .setShowLineNumber(true)
                .setStartLineNumber(0)
                .apply()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun hideKeyboard() {
        currentFocus?.let {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}
