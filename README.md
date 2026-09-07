# Practical-4: Android Alarm Application

## 🎯 Aim

Create an Android Alarm application by using **Service** and **BroadcastReceiver**.

The application demonstrates how to create and schedule an alarm using Android components such as **AlarmManager, PendingIntent, BroadcastReceiver, and Service**.

## 📌 Practical Description

This practical demonstrates the implementation of an Android Alarm application.

The application allows the user to select a time and set an alarm. When the scheduled time is reached, the **AlarmManager** triggers a **BroadcastReceiver**, which starts the **AlarmService**. The service then performs the alarm operation using **MediaPlayer**.

The practical demonstrates the following:

* Creating MainActivity according to the given UI design
* Creating `AlarmBroadcastReceiver`
* Creating `AlarmService`
* Scheduling an alarm using `AlarmManager`
* Using `PendingIntent`
* Starting and stopping a Service
* Using `MediaPlayer`
* Passing data using Intent
* Using `SCHEDULE_EXACT_ALARM` permission

## 🎨 User Interface

The MainActivity contains the UI required for setting an alarm.

The application uses:

* TextClock
* TimePickerDialog
* MaterialCardView
* Buttons
* TextViews
* ConstraintLayout / CoordinatorLayout

### Main UI Functions

The user can:

1. View the current time.
2. Select an alarm time.
3. Set the alarm.
4. Start the alarm service when the alarm is triggered.
5. Stop the alarm service.

## ⏰ Alarm Working Flow

The alarm application works according to the following flow:

```text
MainActivity
     ↓
Select Time
     ↓
TimePickerDialog
     ↓
Calendar
     ↓
AlarmManager
     ↓
PendingIntent
     ↓
AlarmBroadcastReceiver
     ↓
AlarmService
     ↓
MediaPlayer
     ↓
Alarm Sound
```

## 📢 BroadcastReceiver

`BroadcastReceiver` is used to receive the broadcast sent when the alarm time is reached.

The `AlarmBroadcastReceiver` receives the broadcast and starts the `AlarmService`.

Example:

```kotlin
class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, AlarmService::class.java)
        context.startService(serviceIntent)
    }
}
```

A BroadcastReceiver can receive broadcasts sent by the system or an application. Android provides the `onReceive()` method for handling received broadcasts.

## ⚙️ AlarmService

`AlarmService` is used to perform the alarm operation in the background.

The service starts the alarm sound using `MediaPlayer`.

Example:

```kotlin
class AlarmService : Service() {

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        // Start alarm sound

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
```

## ⏱️ AlarmManager

`AlarmManager` is used to schedule operations at a specific time.

Example:

```kotlin
val alarmManager =
    getSystemService(Context.ALARM_SERVICE) as AlarmManager
```

The alarm can then be scheduled using the appropriate `AlarmManager` method.

Android's `AlarmManager` allows an application to trigger an Intent at a specified time, even when the application is not actively running.

## 📦 PendingIntent

`PendingIntent` allows another application or the Android system to perform an action on behalf of the application at a later time.

Example:

```kotlin
val pendingIntent = PendingIntent.getBroadcast(
    this,
    0,
    intent,
    PendingIntent.FLAG_IMMUTABLE
)
```

In this practical, `PendingIntent` is used with `AlarmManager` to trigger the `AlarmBroadcastReceiver`.

## 🕐 TimePickerDialog

`TimePickerDialog` is used to allow the user to select the alarm time.

Example:

```kotlin
TimePickerDialog(
    this,
    { _, hourOfDay, minute ->
        // Selected time
    },
    hour,
    minute,
    true
).show()
```

## 📅 Calendar Class

The `Calendar` class is used to create and manipulate the date and time required for scheduling the alarm.

Example:

```kotlin
val calendar = Calendar.getInstance()

calendar.set(
    Calendar.HOUR_OF_DAY,
    hour
)

calendar.set(
    Calendar.MINUTE,
    minute
)
```

## 📆 SimpleDateFormat Class

`SimpleDateFormat` is used to format date and time into a readable format.

Example:

```kotlin
val sdf = SimpleDateFormat(
    "HH:mm",
    Locale.getDefault()
)

val formattedTime = sdf.format(calendar.time)
```

## 🕰️ TextClock

`TextClock` displays the current time in the application UI.

Example:

```xml
<TextClock
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:format12Hour="hh:mm:ss a"
    android:format24Hour="HH:mm:ss" />
```

## 🔄 `getSystemService()` Method

`getSystemService()` is used to access Android system services.

Example:

```kotlin
val alarmManager =
    getSystemService(Context.ALARM_SERVICE) as AlarmManager
```

It can be used to access services such as `AlarmManager`.

## 📡 `sendBroadcast()` Method

`sendBroadcast()` is used to send a broadcast Intent to registered BroadcastReceivers.

Example:

```kotlin
sendBroadcast(intent)
```

Android's `sendBroadcast()` sends the Intent to matching BroadcastReceivers asynchronously.

## ▶️ `startService()` Method

`startService()` is used to start a Service.

Example:

```kotlin
val intent = Intent(this, AlarmService::class.java)
startService(intent)
```

In this practical, the `AlarmBroadcastReceiver` starts the `AlarmService` when the alarm is triggered.

## ⏹️ `stopService()` Method

`stopService()` is used to stop a running Service.

Example:

```kotlin
val intent = Intent(this, AlarmService::class.java)
stopService(intent)
```

It is used to stop the alarm service.

## 📥 `Intent.getStringExtra()`

`getStringExtra()` is used to retrieve String data passed through an Intent.

Example:

```kotlin
val time = intent.getStringExtra("alarmTime")
```

## 📤 `Intent.putStringExtra()`

`putStringExtra()` is used to pass String data through an Intent.

Example:

```kotlin
intent.putStringExtra(
    "alarmTime",
    "10:30 AM"
)
```

## 🎵 MediaPlayer

`MediaPlayer` is used to play the alarm sound.

Example:

```kotlin
val mediaPlayer = MediaPlayer.create(
    this,
    R.raw.alarm
)

mediaPlayer.start()
```

The alarm sound can be stored inside the `res/raw` directory.

## 🃏 MaterialCardView

`MaterialCardView` is used to create a card-style UI component with Material Design features.

Example:

```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <!-- UI components -->

</com.google.android.material.card.MaterialCardView>
```

It can be used to organize the alarm controls and information in the UI.

## 🔐 SCHEDULE_EXACT_ALARM Permission

The application uses the following permission in the `AndroidManifest.xml`:

```xml
<uses-permission
    android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

This permission is used for applications that need to schedule exact alarms. On Android 12 and higher, exact alarm scheduling requires the appropriate **Alarms & reminders** access.

For modern Android versions, the application should check whether exact-alarm access is available before scheduling an exact alarm:

```kotlin
val alarmManager =
    getSystemService(Context.ALARM_SERVICE) as AlarmManager

if (alarmManager.canScheduleExactAlarms()) {
    // Schedule exact alarm
}
```

## 📂 Project Structure

```text
24012021085_Practical4/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   ├── MainActivity.kt
│           │   ├── AlarmBroadcastReceiver.kt
│           │   └── AlarmService.kt
│           │
│           ├── res/
│           │   ├── drawable/
│           │   ├── layout/
│           │   │   └── activity_main.xml
│           │   ├── raw/
│           │   │   └── alarm.mp3
│           │   └── values/
│           │
│           └── AndroidManifest.xml
│
├── README.md
└── ...
```

## 🛠️ Technologies Used

* **Android Studio**
* **Kotlin**
* **XML**
* **Android SDK**
* **AlarmManager**
* **BroadcastReceiver**
* **Service**
* **PendingIntent**
* **MediaPlayer**
* **MaterialCardView**
* **TimePickerDialog**
* **TextClock**

## 📸 Output

### Main Screen

The application displays the Alarm UI with the current time and controls for selecting and setting an alarm.

### Time Picker

The user can select the required alarm time using `TimePickerDialog`.

### Alarm Set

The selected time is scheduled using `AlarmManager`.

### Alarm Broadcast

When the scheduled time is reached, `AlarmBroadcastReceiver` receives the broadcast.

### Alarm Service

The BroadcastReceiver starts `AlarmService`.

### Alarm Sound

The `AlarmService` starts `MediaPlayer` to play the alarm sound.

### Screenshots

Add your practical screenshots here:

![Main Screen](screenshots/main_screen.png)

![Time Picker](screenshots/time_picker.png)

![Alarm Set](screenshots/alarm_set.png)

![Alarm](screenshots/alarm.png)

## 📚 Study / Learning Outcomes

After completing this practical, I learned:

1. How to create an Android Alarm application.
2. How to use `BroadcastReceiver`.
3. How to create and use a `Service`.
4. How to use `TextClock`.
5. How to use `TimePickerDialog`.
6. How to use the `Calendar` class.
7. How to use the `SimpleDateFormat` class.
8. How to create and use `PendingIntent`.
9. How to use `AlarmManager`.
10. How to use `getSystemService()`.
11. How to use `sendBroadcast()`.
12. How to use `MediaPlayer`.
13. How to use `startService()`.
14. How to use `stopService()`.
15. How to use `Intent.getStringExtra()`.
16. How to use `Intent.putStringExtra()`.
17. How to use `MaterialCardView`.
18. How to add `SCHEDULE_EXACT_ALARM` permission in the Manifest.
19. How an alarm works using BroadcastReceiver and Service.

## 🎯 Result

The Android Alarm application was successfully implemented using **Service, BroadcastReceiver, AlarmManager, PendingIntent, and MediaPlayer**. The application successfully schedules an alarm and plays the alarm sound at the selected time.

## 👩‍💻 Author

**Bhagyashri Bhabhor**
