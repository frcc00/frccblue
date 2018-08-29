import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:frccblue/frccblue.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Frccblue.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    Frccblue.init(didReceiveRead:(MethodCall call){
      print(call.arguments);
      return Uint8List.fromList([11,2,3,4,5,6,7,8,9,10,]);
    }, didReceiveWrite:(MethodCall call){
      print(call.arguments);
    },didSubscribeTo: (MethodCall call){
      print(call.arguments);
      Frccblue.peripheralUpdateValue(call.arguments["centraluuidString"],call.arguments["characteristicuuidString"],Uint8List.fromList([11,2,3,4,5,6,7,8,9,10,11,2,3]));
    },didUnsubscribeFrom: (MethodCall call){
      print(call.arguments);
    },peripheralManagerDidUpdateState: (MethodCall call){
      print(call.arguments);
    });

    Frccblue.startPeripheral("00000000-0000-0000-0000-AAAAAAAAAAA1", "00000000-0000-0000-0000-AAAAAAAAAAA2").then((_){});

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
          child: new Text('Running111 on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
