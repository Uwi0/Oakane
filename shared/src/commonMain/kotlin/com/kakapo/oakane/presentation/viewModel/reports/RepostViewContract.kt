package com.kakapo.oakane.presentation.viewModel.reports

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.ReportModel
import com.kakapo.oakane.model.fakeReports
import kotlin.native.ObjCName

@ObjCName("ReportsState")
data class ReportsState(
    val reports: List<ReportModel> = fakeReports,
){
    val proportions: List<Float> get(){
        val total = reports.sumOf { it.amount }
        return reports.map { (it.amount / total).toFloat() }
    }

    val colors: List<Int> get() = reports.map { it.color.toColorInt() }
}