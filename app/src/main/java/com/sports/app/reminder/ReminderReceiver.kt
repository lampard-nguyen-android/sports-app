package com.sports.app.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sports.app.R
import com.sports.app.main.HomeActivity
import com.sports.app.utils.NotificationUtils
import timber.log.Timber

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("AlarmReceiver $intent")
        val startIntent = Intent(context.applicationContext, HomeActivity::class.java)
        NotificationUtils.showNotification(
            context,
            context.getString(R.string.notification_title_default),
            intent?.getStringExtra(EXTRA_TEXT) ?:  context.getString(R.string.notification_body_default),
            startIntent,
        )
    }

    companion object {
        const val EXTRA_TEXT = "EXTRA_TEXT"
        fun newIntent(context: Context, text: String) =
            Intent(context, ReminderReceiver::class.java).apply {
                putExtra(EXTRA_TEXT, text)
            }
    }
}
