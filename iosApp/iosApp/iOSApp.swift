import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    init() {
        PlatformModuleKt.doInitKoin()
    }

    
    var body: some Scene {
        WindowGroup {
            RootView(root: appDelegate.root, backDispatcher: appDelegate.backDispatcher)
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    let root: RootComponent = DefaultRootComponent(
        ctx: DefaultComponentContext(lifecycle: ApplicationLifecycle())
    )
    
    var backDispatcher: BackDispatcher = BackDispatcherKt.BackDispatcher()
}
