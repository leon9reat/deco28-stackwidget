package com.medialink.deco28stackwidget.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.medialink.deco28stackwidget.widget.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(applicationContext)
    }

}
