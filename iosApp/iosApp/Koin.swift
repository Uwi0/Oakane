import Foundation
import Shared

final class Koin {
    private var core: Koin_coreKoin?
    
    static let instance: Koin = Koin()
    
    static func start() {
        if instance.core == nil {
            let app = KoinIos.shared.initialize()
            instance.core = app.koin
        }
        if instance.core == nil {
            fatalError("Koin is not initialized")
        }
    }
    
    private init() {
        
    }
    
    func get<T: AnyObject>() -> T {
        guard let core = core else {
          fatalError("You should call `start()` before using \(#function)")
        }

        guard let result = core.get(objCClass: T.self) as? T else {
          fatalError("Koin can't provide an instance of type: \(T.self)")
        }

        return result
      }
}
