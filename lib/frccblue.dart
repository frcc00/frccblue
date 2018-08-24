import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class Frccblue {
  static const MethodChannel _channel = const MethodChannel('frccblue');

  static Future<dynamic> init(
      {Function didReceiveRead,
      Function didReceiveWrite,
      Function didSubscribeTo,
      Function didUnsubscribeFrom,
      Function peripheralManagerDidUpdateState}) async {
    _channel.setMethodCallHandler((MethodCall call) {
      print(call.method);
      if (call.method == 'didReceiveRead') {
        return Future(() {
          return didReceiveRead(call);
        });
      }
      if (call.method == 'didReceiveWrite') {
        return Future(() {
          return didReceiveWrite(call);
        });
      }
      if (call.method == 'didSubscribeTo') {
        return Future(() {
          return didSubscribeTo(call);
        });
      }
      if (call.method == 'didUnsubscribeFrom') {
        return Future(() {
          return didUnsubscribeFrom(call);
        });
      }
      if (call.method == 'peripheralManagerDidUpdateState') {
        return Future(() {
          return peripheralManagerDidUpdateState(call);
        });
      }
    });
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void stopPeripheral(){
    _channel.invokeMethod("stopPeripheral");
  }

  static Future<void> peripheralUpdateValue(String centraluuidString,String characteristicUUID, Uint8List datalist) async {
    await _channel.invokeMethod("peripheralUpdateValue",{"centraluuidString":centraluuidString,"characteristicuuidString":characteristicUUID,"data":datalist});
  }

  static Future<String> startPeripheral(
      String serviceUUID, String characteristicUUID) async {
    final String version = await _channel.invokeMethod('startPeripheral',
        {"serviceUUID": serviceUUID, "characteristicUUID": characteristicUUID});
    return version;
  }
}
