import UIKit
import SwiftUI
import ComposeApp

struct RootView: UIViewControllerRepresentable {
    let root: RootComponent
    
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = RootViewControllerKt.RootViewController(root: root)
        controller.overrideUserInterfaceStyle = .light
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}



