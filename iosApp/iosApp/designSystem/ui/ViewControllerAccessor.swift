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

func saveDocument(value: String, fileName: String, viewController: UIViewController, completion: @escaping (Error?) -> Void = { _ in }) {
    let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
    let fileURL = documentsDirectory.appendingPathComponent(fileName)
    
    DispatchQueue.global(qos: .background).async {
        do {
            try value.write(to: fileURL, atomically: true, encoding: .utf8)
            DispatchQueue.main.async {
                let activityViewController = UIActivityViewController(activityItems: [fileURL], applicationActivities: nil)
                viewController.present(activityViewController, animated: true)
                completion(nil)
            }
        } catch {
            DispatchQueue.main.async {
                completion(error)
            }
        }
    }
}

func readJSONFileAsString(fileUrl: URL) -> Result<String, Error> {
    
    do {
        let jsonData = try Data(contentsOf: fileUrl)
        
        if let jsonString = String(data: jsonData, encoding: .utf8) {
            return .success(jsonString)
        } else {
            return .failure(NSError(domain: "InvalidEncoding", code: 2, userInfo: [NSLocalizedDescriptionKey: "Unable to decode JSON data into a string"]))
        }
    } catch {
        return .failure(error)
    }
}
