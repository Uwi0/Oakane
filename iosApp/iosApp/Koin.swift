import Foundation
import Shared

final class Koin {
    private var core: Koin_coreKoin?

    static let shared = Koin()

    private init() {}

    static func start() {
        guard shared.core == nil else { return }
        let app = KoinIos.shared.initialize()
        shared.core = app.koin

        if shared.core == nil {
            fatalError("Failed to initialize Koin")
        }
    }

    func get<T: AnyObject>() -> T {
        guard let core = core else {
            fatalError("Koin is not initialized. Call `Koin.start()` before using this method.")
        }

        guard let result = core.get(objCClass: T.self) as? T else {
            fatalError("Koin can't provide an instance of type: \(T.self)")
        }

        return result
    }
}
