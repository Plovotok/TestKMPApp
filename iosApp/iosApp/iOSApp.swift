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
    
    private var stateKeeper = StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: nil)
    
    lazy var root: RootComponent = DefaultRootComponent(
            ctx: DefaultComponentContext(
                lifecycle: ApplicationLifecycle(),
                stateKeeper: stateKeeper,
                instanceKeeper: nil,
                backHandler: backDispatcher
            ),
        )
    
    var backDispatcher: BackDispatcher = BackDispatcherKt.BackDispatcher()
    
    func application(_ application: UIApplication, shouldSaveSecureApplicationState coder: NSCoder) -> Bool {
            StateKeeperUtilsKt.save(coder: coder, state: stateKeeper.save())
            return true
        }
        
        func application(_ application: UIApplication, shouldRestoreSecureApplicationState coder: NSCoder) -> Bool {
    //        stateKeeper = StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: StateKeeperUtilsKt.restore(coder: coder))
            return true
        }
}
