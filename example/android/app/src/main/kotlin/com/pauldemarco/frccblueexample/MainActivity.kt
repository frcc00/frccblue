package com.pauldemarco.frccblueexample

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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
    startForeground(666,builder?.build())
    return Service.START_STICKY
  }
}
