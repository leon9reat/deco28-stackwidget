package com.medialink.deco28stackwidget.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.medialink.deco28stackwidget.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

internal class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory, CoroutineScope {

    val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val widgetItem = ArrayList<Bitmap>()

    override fun onCreate() {
        Log.d("debug", "onCreate :")
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        /*widgetItem.add(BitmapFactory.decodeResource(context.resources, R.drawable.darth_vader))
        widgetItem.add(BitmapFactory.decodeResource(context.resources, R.drawable.star_wars_logo))
        widgetItem.add(BitmapFactory.decodeResource(context.resources, R.drawable.storm_trooper))
        widgetItem.add(BitmapFactory.decodeResource(context.resources, R.drawable.starwars))
        widgetItem.add(BitmapFactory.decodeResource(context.resources, R.drawable.falcon))*/
        runBlocking {
            widgetItem.clear()
            widgetItem.addAll(fetchData())

            Log.d("debug", widgetItem.size.toString())
        }

    }

    private suspend fun fetchData(): ArrayList<Bitmap> {
        val result = withContext(Dispatchers.IO) {
            val proses = arrayListOf<Bitmap>()
            with(proses) {
                add(BitmapFactory.decodeResource(context.resources, R.drawable.darth_vader))
                add(BitmapFactory.decodeResource(context.resources, R.drawable.star_wars_logo))
                add(BitmapFactory.decodeResource(context.resources, R.drawable.storm_trooper))
                add(BitmapFactory.decodeResource(context.resources, R.drawable.starwars))
                add(BitmapFactory.decodeResource(context.resources, R.drawable.falcon))
            }

            return@withContext proses
        }

        return result
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.widget_item)
        remoteView.setImageViewBitmap(R.id.imageView, widgetItem[position])

        val extras = bundleOf(ImageBannerWidget.EXTRA_ITEM to position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteView.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return remoteView
    }

    override fun getCount(): Int {
        Log.d("debug", "getCount() ${widgetItem.size}")
       return widgetItem.size
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        job.cancel()
    }



}