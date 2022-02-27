package com.tandem.tandemtest.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tandem.tandemtest.R
import com.tandem.tandemtest.core.ui.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<CommunityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getCommunities(1)
        subscribeViews()
    }

    private fun subscribeViews() {
        viewModel.communityLiveData.observe(this, { state ->
            when (state) {
                is State.Loading -> {}
                is State.Error -> {}
                is State.Success -> {
                    Log.i("tag", state.data.toString())
                }
            }
        })
    }

}