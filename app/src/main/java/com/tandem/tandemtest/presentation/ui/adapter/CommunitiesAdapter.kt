package com.tandem.tandemtest.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tandem.tandemtest.R
import com.tandem.tandemtest.core.extension.hide
import com.tandem.tandemtest.core.extension.show
import com.tandem.tandemtest.core.imageloader.IImageLoader
import com.tandem.tandemtest.presentation.model.Community
import kotlinx.android.synthetic.main.community_row.view.*
import org.koin.java.KoinJavaComponent.inject

/**
 * Created by Mahsa on 2022.02.27
 *
 * This Adapter is responsible for showing Communities list
 *
 */
class CommunitiesAdapter(
    private val data: MutableList<Community>,
    private val context: Context,
    private val onCommunityClicked: (Community) -> Unit
) : RecyclerView.Adapter<CommunitiesAdapter.ViewHolder>() {

    private val imageLoader by inject(IImageLoader::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_row, parent, false)

        view.setOnClickListener { v ->
            onCommunityClicked((v.tag as Community))
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun addData(newData: List<Community>) {
        val firstPosition = data.size
        data.addAll(newData)
        notifyItemRangeInserted(firstPosition, data.size)
    }

    fun changeLikeStatus(communityId: Int, isLike: Boolean) {
        val community = data.find { it.id == communityId }
        community?.isLike = isLike
        notifyItemChanged(data.indexOf(community))
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(community: Community) {
            with(community) {

                view.firstNameTextView.text = firstName
                view.topicTextView.text = topic
                view.learnTextView.text = when {
                    learns.size > 1 -> learns.first() + " +" + (learns.size - 1).toString()
                    learns.size == 1 -> learns.first()
                    else -> ""
                }
                view.nativeTextView.text = when {
                    natives.size > 1 -> natives.first() + " +" + (natives.size - 1).toString()
                    natives.size == 1 -> natives.first()
                    else -> ""
                }
                view.tag = this


                if (referenceCnt == 0) {
                    view.referenceCountTextView.hide()
                    view.newIconImageView.show()
                } else {
                    view.referenceCountTextView.show()
                    view.newIconImageView.hide()
                    view.referenceCountTextView.text = referenceCnt.toString()
                }

                if (isLike) {
                    view.likeImageView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.ic_like
                        )
                    )
                } else {
                    view.likeImageView.setImageDrawable(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.ic_dislike
                        )
                    )
                }

                imageLoader.load(
                    context,
                    pictureUrl,
                    view.pictureImageView
                )
            }
        }
    }
}