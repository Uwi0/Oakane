import Foundation
import Combine

class DebouncedQueryObserver: ObservableObject {
    @Published var debouncedQuery: String = ""
    @Published var searchQuery: String = ""
    
    private var subscribions = Set<AnyCancellable>()
    
    init() {
        $searchQuery.debounce(for: .seconds(0.5), scheduler: DispatchQueue.main).sink { [weak self] text in
            self?.debouncedQuery = text
        }
        .store(in: &subscribions)
    }
}
