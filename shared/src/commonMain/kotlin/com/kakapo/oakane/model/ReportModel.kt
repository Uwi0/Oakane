package com.kakapo.oakane.model

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.category.CategoryIconName

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

val fakeReports = listOf(
    ReportModel(1, "Salary", "0xFF4CAF50", 1723450.00, false, true, "Salary"),
    ReportModel(2, "Freelance", "0xFF66BB6A", 853120.00, false, true, "Freelance"),
    ReportModel(3, "Business Profits", "0xFF81C784", 1402780.00, false, true, "Business Profits"),
    ReportModel(4, "Rental Income", "0xFFA5D6A7", 612340.00, false, true, "Rental Income"),
    ReportModel(5, "Interest", "0xFF388E3C", 1134560.00, false, true, "Interest"),
    ReportModel(6, "Dividends", "0xFF43A047", 1023670.00, false, true, "Dividends"),
    ReportModel(7, "Gifts", "0xFFFFD54F", 789120.00, false, true, "Gifts"),
    ReportModel(8, "Rent/Mortgage", "0xFF795548", 1324450.00, true, true, "Rent/Mortgage"),
    ReportModel(9, "Utilities", "0xFF64B5F6", 203780.00, true, true, "Utilities"),
    ReportModel(10, "Groceries", "0xFF8BC34A", 456340.00, true, true, "Groceries"),
    ReportModel(11, "Restaurants", "0xFFFF7043", 167890.00, true, true, "Restaurants"),
    ReportModel(12, "Movies", "0xFF5C6BC0", 89450.00, true, true, "Movies"),
    ReportModel(13, "Tuition", "0xFF283593", 987120.00, true, true, "Tuition"),
    ReportModel(14, "Doctors Visits", "0xFFE57373", 543220.00, true, true, "Doctors Visits"),
    ReportModel(15, "Hair", "0xFFEF9A9A", 120450.00, true, true, "Hair"),
    ReportModel(16, "Clothing", "0xFFAB47BC", 345670.00, true, true, "Clothing"),
    ReportModel(17, "Gifts Expense", "0xFFFFD54F", 234780.00, true, true, "Gifts Expense")
)