package com.kakapo.oakane.model.report

import com.kakapo.common.toColorInt
import com.kakapo.oakane.model.category.CategoryIconName
import kotlin.native.ObjCName

@ObjCName("ReportModelKt")
data class ReportModel(
    val id: Long,
    val name: String,
    val color: String,
    val amount: Double,
    val isExpense: Boolean,
    val isDefault: Boolean,
    val icon: String = "Salary"
){
    val formattedColor: Int get() = color.toColorInt()

    val iconName: CategoryIconName
        get() {
            return if (isDefault) CategoryIconName.fromString(icon)
            else CategoryIconName.SALARY
        }
}