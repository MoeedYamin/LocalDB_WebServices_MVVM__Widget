package com.example.task_7_localdb_webservices_mvvm__widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.RemoteViews
import com.example.task_7_localdb_webservices_mvvm__widget.retrofit.ApiServiceFactory
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.User
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.UserRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val userRepository = UserRepository(ApiServiceFactory.apiService, BaseApplication.database.userDao())
            val users = userRepository.getAllUsers()

            if (users.isNotEmpty()) {
                val user = users.first()

                withContext(Dispatchers.Main) {
                    for (appWidgetId in appWidgetIds) {
                        updateWidget(context, appWidgetManager, appWidgetId, user)
                        val views = RemoteViews(context.packageName, R.layout.widget_layout)
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
                            PendingIntent.FLAG_IMMUTABLE)
                        views.setOnClickPendingIntent(R.id.widget, pendingIntent)
                        appWidgetManager.updateAppWidget(appWidgetId, views)


                    }
                }
            }
        }
    }
    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        user: User
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        val fullName = "${user.firstName} ${user.lastName}"
        views.setTextViewText(R.id.widgetFullName, fullName)

        if (user.avatar.isNotEmpty()) {
            Picasso.get()
                .load(user.avatar)
                .into(views, R.id.widgetImage, intArrayOf(appWidgetId))
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}