import Foundation
import Shared
import SwiftUI

struct ReportCsvModel {
    let month: String
    let categoryName: String
    let categoryBudgetLimit: String
    let amountSpentInCategory: String
    let remainingCategoryBudget: String
    let walletName: String
    let walletBalance: String
    let transactionTitle: String
    let transactionAmount: String
    let transactionType: String
    let transactionDate: String
    let note: String
    
    init(model: ReportCsvModelKt){
        month = model.month
        categoryName = model.categoryName
        categoryBudgetLimit = model.categoryBudgetLimit
        amountSpentInCategory = model.amountSpentInCategory
        remainingCategoryBudget = model.remainingCategoryBudget
        walletName = model.walletName
        walletBalance = model.walletBalance
        transactionTitle = model.transactionTitle
        transactionAmount = model.transactionAmount
        transactionType = model.transactionType
        transactionDate = model.transactionDate
        note = model.note
    }
}

extension Array where Element == ReportCsvModelKt {
    func toReportCsvModels() -> [ReportCsvModel] {
        self.map{ ReportCsvModel(model: $0) }
    }
}


func generateCSV(from data: [ReportCsvModel]) -> String {
    var csvString = "Month,Category Name,Category Budget Limit,Amount Spent in Category,Remaining Category Budget,Wallet Name,Wallet Balance,Transaction Title,Transaction Amount,Transaction Type,Transaction Date,Note\n"
    
    for report in data {
        let row = "\(report.month),\(report.categoryName),\(report.categoryBudgetLimit),\(report.amountSpentInCategory),\(report.remainingCategoryBudget),\(report.walletName),\(report.walletBalance),\(report.transactionTitle),\(report.transactionAmount),\(report.transactionType),\(report.transactionDate),\(report.note)\n"
        csvString.append(row)
    }
    
    return csvString
}

func saveCSVAndShare(csvString: String, fileName: String, viewController: UIViewController) {
    let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
    let fileURL = documentsDirectory.appendingPathComponent(fileName)

    do {
        try csvString.write(to: fileURL, atomically: true, encoding: .utf8)

        let activityViewController = UIActivityViewController(activityItems: [fileURL], applicationActivities: nil)
        viewController.present(activityViewController, animated: true)
    } catch {
        print("Error saving CSV file: \(error)")
    }
}
