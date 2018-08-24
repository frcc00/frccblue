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
  frccble:
    git:
      url: https://github.com/asusaa/frccble
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
