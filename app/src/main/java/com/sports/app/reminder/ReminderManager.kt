package com.sports.app.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import com.sports.app.R
import com.sports.app.data.domain.Match
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

interface ReminderManager {
    fun setReminder(date: Date, match: Match)
}

@Singleton
class ReminderManagerImpl @Inject constructor(@ApplicationContext val context: Context) :
    ReminderManager {
    private val DAILY_REMINDER_REQUEST_CODE = 0

    override fun setReminder(
        date: Date,
        match: Match,
    ) {
        val calendar: Calendar = Calendar.getInstance()
        val setCalendar: Calendar = Calendar.getInstance().apply { time = date }
        // cancel already scheduled reminders

        if (setCalendar.before(calendar)) {
            Toast.makeText(
                context,
                context.getString(R.string.error_set_reminder),
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        // Enable a receiver
        val receiver = ComponentName(context, ReminderReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP,
        )
        val intent = ReminderReceiver.newIntent(context, match.description)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            DAILY_REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            setCalendar.timeInMillis,
            pendingIntent,
        )
        Toast.makeText(
            context,
            context.getString(R.string.set_reminder_successful, match.home, match.away),
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun cancelReminder(context: Context, cls: Class<*>) {
        // Disable a receiver
        val receiver = ComponentName(context, cls)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP,
        )
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            DAILY_REMINDER_REQUEST_CODE,
            Intent(context, cls),
            PendingIntent.FLAG_IMMUTABLE,
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}
