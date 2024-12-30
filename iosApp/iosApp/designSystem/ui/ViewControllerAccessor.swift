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

func saveDocument(value: String, fileName: String, viewController: UIViewController) {
    let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
    let fileURL = documentsDirectory.appendingPathComponent(fileName)

    do {
        try value.write(to: fileURL, atomically: true, encoding: .utf8)

        let activityViewController = UIActivityViewController(activityItems: [fileURL], applicationActivities: nil)
        viewController.present(activityViewController, animated: true)
    } catch {
        print("Error saving \(fileName) to file: \(error)")
    }
}

