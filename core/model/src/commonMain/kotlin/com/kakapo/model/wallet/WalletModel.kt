package com.kakapo.model.wallet

import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName

data class WalletModel(
    val id: Long = 0,
    val currency: Currency = Currency.IDR,
    val balance: Double = 0.0,
    val name: String = "",
    val isDefaultIcon: Boolean = false,
    val icon: String = "",
    val color: String = ""
){
    val iconName: CategoryIconName
        get() {
            return if (isDefaultIcon) CategoryIconName.fromString(icon)
            else CategoryIconName.SALARY
        }
}

val defaultWallet = WalletModel()
