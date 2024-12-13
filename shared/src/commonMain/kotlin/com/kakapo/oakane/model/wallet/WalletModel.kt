package com.kakapo.oakane.model.wallet

import com.kakapo.oakane.model.category.CategoryIconName

data class WalletModel(
    val id: Long = 0,
    val currency: String = "",
    val balance: Double = 0.0,
    val name: String = "",
    val isDefault: Boolean = false,
    val icon: String = "",
){
    val iconName: CategoryIconName
        get() {
            return if (isDefault) CategoryIconName.fromString(icon)
            else CategoryIconName.SALARY
        }
}
