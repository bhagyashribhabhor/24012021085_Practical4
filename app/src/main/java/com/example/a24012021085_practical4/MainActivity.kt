package com.example.a24012021085_practical4

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    lateinit var textAlarm: TextView
    lateinit var cardSetAlarm: MaterialCardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textAlarm = findViewById<TextView>(R.id.textView6)
        cardSetAlarm = findViewById(R.id.Materalcard2)
        cardSetAlarm.visibility = View.GONE
        findViewById<MaterialButton>(R.id.set_alarm_btn).setOnClickListener {
            showTimeDialog()
        }
        findViewById<MaterialButton>(R.id.cancel_alarm_btn).setOnClickListener {
            setAlarm(0, AlarmBroadcastReceiver.START_VALUE)
            cardSetAlarm.visibility= View.GONE

        }
    }

    private fun showTimeDialog() {
        val cldr: Calendar = Calendar.getInstance()
        val h: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val m: Int = cldr.get(Calendar.MINUTE)
        val picker = TimePickerDialog(
            this, { tp, hour, minute -> sendDialogDataTOActivity(hour, minute) },
            h, m, false
        )


        picker.show()
    }

    private fun sendDialogDataTOActivity(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)
        if (setAlarm(alarmCalendar.timeInMillis, AlarmBroadcastReceiver.START_VALUE)) {
            textAlarm.text = "$hour:$minute"
            cardSetAlarm.visibility = View.VISIBLE
        }
        Toast.makeText(this, "Time: hours:${hour}, minute:${minute}, millis:${alarmCalendar.timeInMillis}",
            Toast.LENGTH_SHORT).show()

    }

    private fun setAlarm(millisTime: Long, str: String): Boolean{
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra(AlarmBroadcastReceiver.SERVICE_KEY, str)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 23245, intent,
            PendingIntent.FLAG_MUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (str == AlarmBroadcastReceiver.START_VALUE) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, millisTime, pendingIntent)
                    return true
                } else {
                    Toast.makeText(this, "Can't schedule alarm", Toast.LENGTH_SHORT).show()
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, "package:$packageName".toUri()).apply { startActivity(this)
                    }
                    return false
                }
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, millisTime, pendingIntent)
                return true
            }
        }
        return false
    }



}
