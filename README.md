# frccblue

A new Flutter plugin.

## Getting Started

For help getting started with Flutter, view our online
[documentation](https://flutter.io/).

For help on editing plugin code, view the [documentation](https://flutter.io/developing-packages/#edit-plugin-package).

# 平台
iOS android

# 使用
```
dependencies:
  frccblue:
    git:
      url: https://github.com/frcc00/frccblue
```
      
      
# 调用
```
Frccblue.init(didReceiveRead:(MethodCall call){
      print(call.arguments);
      return Uint8List.fromList([11,2,3,4,5,6,7,8,9,10,]);
    }, didReceiveWrite:(MethodCall call){
      print(call.arguments);
    },didSubscribeTo: (MethodCall call){
      print(call.arguments);
//      Frccblue.peripheralUpdateValue()
    },didUnsubscribeFrom: (MethodCall call){
      print(call.arguments);
    },peripheralManagerDidUpdateState: (MethodCall call){
      print(call.arguments);
    });

Frccblue.startPeripheral("00000000-0000-0000-0000-AAAAAAAAAAA1", "00000000-0000-0000-0000-AAAAAAAAAAA2").then((_){});
```

# more
## peripheralManagerDidUpdateState
iOS上传状态
```
switch peripheral.state {
        case .unknown:
            print("未知的")
            state = "unknown"
        case .resetting:
            print("重置中")
            state = "resetting"
        case .unsupported:
            print("不支持")
            state = "unsupported"
        case .unauthorized:
            print("未验证")
            state = "unauthorized"
        case .poweredOff:
            print("未启动")
            state = "poweredOff"
            self.peripheralManager?.stopAdvertising()
        case .poweredOn:
            print("可用")
            state = "poweredOn"
```
android上传状态
```
"unknown"
"poweredOff"
"poweredOn"
```


由于iOS没有设备连上和断开连接的回掉，android有；所以统一要求中心设备订阅Characteristic。
那么didSubscribeTo表示设备连上，didUnsubscribeFrom表示设备断开。android端didUnsubscribeFrom会被触发2次，在设备主动取消订阅的情况下。
