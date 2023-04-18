package com.charlie.gallery

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.logic.CalculatorThread
import com.charlie.gallery.model.*

class ListAdapter(
    private val onClickViewHolder: (Int, ImageData) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val imageDataList = mutableListOf<ImageData>()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemGridImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageDataList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageDataList[position]
        holder.itemView.post {
            val viewWidth = holder.itemView.width
            val viewHeight = holder.itemView.height
            holder.bind(data, viewWidth, viewHeight)
        }
        holder.itemView.setOnClickListener {
            onClickViewHolder(position, data)
        }
    }

    fun initList(imageDataList: List<ImageData>) {
        this.imageDataList.addAll(imageDataList)
        notifyItemRangeChanged(0, this.imageDataList.size)
    }

    fun addList(imageDataList: List<ImageData>) {
        val tempSize = this.imageDataList.lastIndex
        this.imageDataList.addAll(imageDataList)
        notifyItemRangeChanged(tempSize, this.imageDataList.size)
    }

    fun clearList() {
        imageDataList.clear()
        notifyDataSetChanged()
    }

    fun getDetailImageData(detailScreen: DetailScreen): WithCurrentIndexImageData {
        return when (detailScreen) {
            is DetailScreen.OnClickNext -> {
                getNextImageData(detailScreen.imageData)
            }
            is DetailScreen.OnClickPrevious -> {
                getPreviousImageData(detailScreen.imageData)
            }
        }
    }

    private fun getPreviousImageData(
        imageData: ImageData
    ): WithCurrentIndexImageData {
        val previousIndex = imageDataList.indexOf(imageData) - 1
        return WithCurrentIndexImageData(previousIndex, imageDataList.getOrNull(previousIndex))
    }

    private fun getNextImageData(
        imageData: ImageData
    ): WithCurrentIndexImageData {
        val nextIndex = imageDataList.indexOf(imageData) + 1
        return WithCurrentIndexImageData(nextIndex, imageDataList.getOrNull(nextIndex))
    }

    fun getCurrentImageSet(imageData: ImageData?): DetailImageSet {
        val index = imageDataList.indexOf(imageData)
        return DetailImageSet(
            previousImageData = imageDataList.getOrNull(index - 1),
            currentImageData = imageDataList.getOrNull(index),
            nextImageData = imageDataList.getOrNull(index + 1),
        )
    }

    inner class ListViewHolder(private val binding: ItemGridImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageData: ImageData, viewWidth: Int, viewHeight: Int) {

            val uri = Uri.parse(imageData.downloadUrl)
            val segmentPath = uri.pathSegments.toMutableList()
            val lastIndex = segmentPath.lastIndex
            val originSize = Size(
                width = segmentPath[lastIndex - 1].toInt(),
                height = segmentPath[lastIndex].toInt(),
            )
            val targetSize = Size(
                width = viewWidth,
                height = viewHeight,
            )

            CalculatorThread(originSize, targetSize) { calculatedSize ->
                segmentPath[lastIndex - 1] = calculatedSize.width.toString()
                segmentPath[lastIndex] = calculatedSize.height.toString()
                val resizeUrl =
                    "${uri.scheme}://${uri.host}/${segmentPath.joinToString(separator = "/")}"
                handler.post {
                    Glide.with(this.itemView)
                        .load(imageData.downloadUrl)
                        .thumbnail(
                            Glide.with(this.itemView)
                                .load(resizeUrl)
                                .override(viewWidth, viewHeight)
                        )
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.close)
                        .timeout(1000)
                        .into(binding.root)
                }
            }.start()
        }
    }
}
