#import "FrccbluePlugin.h"
#import <frccblue/frccblue-Swift.h>

@implementation FrccbluePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFrccbluePlugin registerWithRegistrar:registrar];
}
@end
