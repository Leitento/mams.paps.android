package com.gomaping.navigation.ui.events.eventcar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gomaping.R
import com.gomaping.navigation.ui.events.eventcar.ImageModel

class CardReviewAdapter(private val photo: List<ImageModel>) :
    RecyclerView.Adapter<CardReviewAdapter.ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event_photo, parent, false)

        view.viewTreeObserver
            .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val lp: ViewGroup.LayoutParams = view.layoutParams
                    if (lp is StaggeredGridLayoutManager.LayoutParams) {
                        when (viewType) {
                            TYPE_FULL -> lp.isFullSpan = true
                            TYPE_LESS -> {
                                lp.isFullSpan = false
                                lp.marginStart = (view.width * 1.4).toInt() - view.width
                            }

                            TYPE_WIDER -> {
                                lp.isFullSpan = false
                                lp.width = (view.width * 1.45).toInt()
                            }

                            TYPE_CUSTOM -> {}
                        }
                        view.layoutParams = lp
                        val lm =
                            (parent as RecyclerView).layoutManager as StaggeredGridLayoutManager?
                        lm!!.invalidateSpanAssignments()
                    }
                    view.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            })
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.image_review_card)
            .setImageResource(photo[position].iconResId)
    }

    override fun getItemCount(): Int {
        return photo.size
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            1, 7 -> return TYPE_LESS
            0, 6 -> return TYPE_WIDER
            3, 4 -> return TYPE_CUSTOM
        }
        return TYPE_FULL
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private const val TYPE_FULL = 0
        private const val TYPE_LESS = 1
        private const val TYPE_WIDER = 2
        private const val TYPE_CUSTOM = 3
    }
}