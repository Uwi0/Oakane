package com.kakapo.oakane.domain.usecase

import android.content.Context
import com.kakapo.model.report.ReportCsvModel
import com.opencsv.CSVWriter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.File

private val headers = arrayOf(
    "Month",
    "Category Name",
    "Category Budget Limit",
    "Amount Spent in Category",
    "Remaining Category Budget",
    "Wallet Name",
    "Wallet Balance",
    "Transaction Title",
    "Transaction Amount",
    "Transaction Type",
    "Transaction Date",
    "Note"
)

suspend fun List<ReportCsvModel>.toCsvUseCase(context: Context, fileName: String): Deferred<File?> = coroutineScope {
    val reports = this@toCsvUseCase
    val deferredAsync = async {
        if(reports.isEmpty()){
            return@async null
        }

        val outputDir = context.getExternalFilesDir(null)
        val outputFile = File(outputDir, fileName)

        CSVWriter(outputFile.writer()).use { csvWriter ->
            csvWriter.writeNext(headers)
            reports.forEach { report ->
                csvWriter.writeNext(
                    arrayOf(
                        report.month,
                        report.categoryName,
                        report.categoryBudgetLimit,
                        report.amountSpentInCategory,
                        report.remainingCategoryBudget,
                        report.walletName,
                        report.walletBalance,
                        report.transactionTitle,
                        report.transactionAmount,
                        report.transactionType,
                        report.transactionDate,
                        report.note
                    )
                )
            }
        }

        return@async outputFile
    }

    return@coroutineScope deferredAsync
}