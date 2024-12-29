import SwiftUI

struct ViewControllerAccessor: UIViewControllerRepresentable {
    var callback: (UIViewController) -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = UIViewController()
        DispatchQueue.main.async {
            self.callback(viewController)
        }
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
