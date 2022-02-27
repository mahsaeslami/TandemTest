package com.tandem.tandemtest.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tandem.tandemtest.R
import com.tandem.tandemtest.core.extension.hide
import com.tandem.tandemtest.core.extension.show
import com.tandem.tandemtest.core.extension.toast
import com.tandem.tandemtest.core.ui.State
import com.tandem.tandemtest.presentation.ui.adapter.CommunitiesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.tandem.tandemtest.presentation.util.EndlessRecyclerViewScrollListener

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<CommunityViewModel>()
    private lateinit var adapter: CommunitiesAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        subscribeViews()

        executeGetCommunities(1)
    }

    private fun subscribeViews() {
        viewModel.communityLiveData.observe(this, { state ->
            when (state) {
                is State.Loading -> {
                    communitiesProgressBar.show()
                }
                is State.Error -> {
                    communitiesProgressBar.hide()
                    toast(state.error)
                }
                is State.Success -> {
                    communitiesProgressBar.hide()
                    state.data?.let {
                        adapter.addData(it)
                        if (it.size < 20) {
                            communitiesRecyclerView.removeOnScrollListener(scrollListener)
                        }
                    }
                }
            }
        })

        viewModel.saveCommunityLiveData.observe(this, { state ->
            when (state) {
                is State.Loading -> {
                }
                is State.Error -> {
                    toast(state.error)
                }
                is State.Success -> {
                    state.data?.let {
                        adapter.changeLikeStatus(it.first, it.second)
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        communitiesRecyclerView.layoutManager = layoutManager
        communitiesRecyclerView.setHasFixedSize(true)

        // add pagination
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                executeGetCommunities(page)
            }
        }

        scrollListener.let {
            communitiesRecyclerView.addOnScrollListener(it)
        }

        // add divider
        val dividerItemDecoration = DividerItemDecoration(
            communitiesRecyclerView.context,
            layoutManager.orientation
        )
        communitiesRecyclerView.addItemDecoration(dividerItemDecoration)

        // Create adapter
        adapter = CommunitiesAdapter(mutableListOf(), this) {
            executeChangeLikeStatus(it.id, !it.isLike)
        }.apply {
            communitiesRecyclerView.adapter = this
        }
    }

    private fun executeGetCommunities(pageId: Int) {
        viewModel.getCommunities(pageId)
    }

    private fun executeChangeLikeStatus(communityId: Int, isLike: Boolean) {
        viewModel.saveCommunity(communityId, isLike)
    }
}