package io.chever.tv.ui.home.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.fromJson
import io.chever.tv.ui.home.common.model.HeaderIconItem
import io.chever.tv.ui.home.common.presenter.HeaderIconItemPresenter
import io.chever.tv.ui.movies.view.MoviesBrowseFragment
import kotlinx.coroutines.delay

/**
 * TODO: document class
 */
class HomeFragment : BrowseSupportFragment() {

    private lateinit var mPageRowsAdapter: ArrayObjectAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {

            setupUI()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }


    private fun setupUI() {

        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        title = getString(R.string.app_name)

        //setOnSearchClickedListener {  }

        //prepareEntranceTransition()

        setupAdapter()

        setupFragmentFactory()


        lifecycleScope.launchWhenStarted {

            setupMenuHeaderItems()

            delay(250)

            startHeadersTransition(false)
        }
    }

    private fun setupAdapter() {
        // Initialize items show on home menu.
        // This control page rows fragments by adapter.
        mPageRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mPageRowsAdapter
    }

    private fun setupMenuHeaderItems() {

        setupHeaderItemPresenter()

        val itemType = arrayOf<HeaderIconItem>()::class.java

        resources.openRawResource(R.raw.header_items).fromJson(itemType).let {

            it?.forEach { item ->

                mPageRowsAdapter.add(PageRow(item))
            }
        }
    }

    private fun setupHeaderItemPresenter() {

        setHeaderPresenterSelector(object : PresenterSelector() {

            override fun getPresenter(item: Any?): Presenter {

                var iconResId = -1
                var titleResId = -1

                if (item is PageRow) {

                    val headerItem = item.headerItem as HeaderIconItem
                    titleResId = resources.getIdentifier(
                        headerItem.resTitleId,
                        "string",
                        context?.packageName
                    )
                    iconResId = resources.getIdentifier(
                        headerItem.resIconId,
                        "drawable",
                        context?.packageName
                    )
                }

                return HeaderIconItemPresenter(
                    resTitleId = titleResId,
                    resIconId = iconResId
                )
            }
        })
    }

    private fun setupFragmentFactory() {

        // TODO: check this method
        mainFragmentRegistry?.registerFragment(
            PageRow::class.java,
            HomePageRowFragmentFactory()
        )
    }


    // TODO: refactor this class
    class HomePageRowFragmentFactory : BrowseSupportFragment.FragmentFactory<Fragment>() {

        override fun createFragment(anyItem: Any?): Fragment {

            val rowItem = anyItem as Row

            return when (HeaderIconItem.Id.valueOf(rowItem.id)) {

                HeaderIconItem.Id.Movies -> MoviesBrowseFragment()

                else -> TestFragment()
            }
        }
    }

    class TestFragment : Fragment(), MainFragmentAdapterProvider {

        private val mMainFragmentAdapter = MainFragmentAdapter(this)

        override fun getMainFragmentAdapter(): MainFragmentAdapter<*> {
            return mMainFragmentAdapter
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //mainFragmentAdapter.fragmentHost.showTitleView(false)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            val root = LinearLayout(requireActivity())

            val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            return root.apply {

                val tvTitle = TextView(requireActivity()).apply {
                    text = "PENDING BUILD ..."
                    gravity = Gravity.CENTER
                    setTextAppearance(R.style.TextAppearance_Large)
                }

                addView(tvTitle, lp)
            }
        }
    }

}