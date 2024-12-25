package com.kakapo.oakane.model.wallet

import com.kakapo.oakane.model.category.CategoryIconName

data class WalletItemModel(
    val id: Long = 0,
    val name: String = "",
    val isDefault: Boolean = true,
    val icon: String = "",
    val currency: String = "",
    val balance: Double = 0.0,
    val color: String = "",
    val income: Double = 0.0,
    val expense: Double = 0.0,
    val isSelected: Boolean = false
){
    val iconName: CategoryIconName get() {
        return if (isDefault) CategoryIconName.fromString(icon)
        else CategoryIconName.SALARY
    }
}
