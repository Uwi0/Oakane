package com.kakapo.model.report

import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import kotlin.native.ObjCName

@ObjCName("ReportModelKt")
data class ReportModel(
    val id: Long,
    val name: String,
    val color: String,
    val amount: Double,
    val isExpense: Boolean,
    val isDefault: Boolean,
    val icon: String = "Salary",
    val currency: Currency
){
    val formattedColor: Long get() = color.ifEmpty { "0xFF4CAF50" }.toColorLong()

    val iconName: CategoryIconName
        get() {
            return if (isDefault) CategoryIconName.fromString(icon)
            else CategoryIconName.SALARY
        }
}