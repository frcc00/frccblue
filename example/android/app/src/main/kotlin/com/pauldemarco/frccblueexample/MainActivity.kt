package com.pauldemarco.frccblueexample

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import com.pauldemarco.frccblue.FrccbluePlugin

import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity(): FlutterActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)
    Log.d("MainActivity", "MainActivity onCreate()")
    val intent = Intent(this,FrccblueService::class.java)
    startService(intent)
  }
}

class FrccblueService : Service() {

  private val myBinder = MylocalBinder()
  private val TAG = FrccblueService::class.java!!.getSimpleName()

  override fun onBind(intent: Intent?): IBinder {
    print("onBind")
    return myBinder
  }

  inner class MylocalBinder : Binder() {
    fun getService() : FrccblueService {
      return this@FrccblueService
    }
  }

  override fun onCreate() {
    print("onCreate")
    super.onCreate()
    Log.d(TAG, "onCreate()")
  }

  override fun onDestroy() {
    print("onDestroy")
    super.onDestroy()
    stopForeground(true)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    print("onStartCommand")
    Log.d(TAG, "onStartCommand()")
    var builder: Notification.Builder? = null
    val nfIntent = Intent(this, FrccbluePlugin.activity?.javaClass)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      builder = Notification.Builder(FrccbluePlugin.activity?.applicationContext,"666")
              .setContentIntent(PendingIntent.getActivity(this,0,nfIntent,0))
              .setSmallIcon(R.mipmap.ic_logo)
              .setContentTitle("正在后台运行")
              .setContentText("随时准备蓝牙开锁")
              .setWhen(System.currentTimeMillis())
    }else{
      builder = Notification.Builder(FrccbluePlugin.activity?.applicationContext)
              .setContentIntent(PendingIntent.getActivity(this,0,nfIntent,0))
              .setSmallIcon(R.mipmap.ic_logo)
              .setContentTitle("正在后台运行")
              .setContentText("随时准备蓝牙开锁")
              .setWhen(System.currentTimeMillis())
    }
    //startForeground(666,builder?.build())
    startForeground()
    return Service.START_STICKY
  }

  private fun startForeground() {
    val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              createNotificationChannel("my_service", "My Background Service")
            } else {
              // If earlier version channel ID is not used
              // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
              ""
            }

    val notificationBuilder = NotificationCompat.Builder(this, channelId )
    val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(com.pauldemarco.frccblue.R.drawable.notification_icon_background)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    startForeground(666, notification)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun createNotificationChannel(channelId: String, channelName: String): String{
    val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
    chan.lightColor = Color.BLUE
    chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
    val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    service.createNotificationChannel(chan)
    return channelId
  }
}
