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
            RootView(root: appDelegate.root)
                .ignoresSafeArea(.all)
                .onOpenURL { (url) in
                    DeeplinkHelper.shared.handleDeepLink(fullPath: url.absoluteString)
                }
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    
    private var stateKeeper = StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: nil)
    
    lazy var root: RootComponent = DefaultRootComponent(
            ctx: DefaultComponentContext(
                lifecycle: ApplicationLifecycle(),
                stateKeeper: stateKeeper,
                instanceKeeper: nil,
                backHandler: nil
            )
        )
}
