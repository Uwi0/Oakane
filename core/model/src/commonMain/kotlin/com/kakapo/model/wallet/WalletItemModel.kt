package com.kakapo.model.wallet

import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.toFormatNumber

data class WalletItemModel(
    val id: Long = DEFAULT_ID,
    val name: String = "",
    val isDefault: Boolean = true,
    val icon: String = "",
    val currency: Currency = Currency.IDR,
    val balance: Double = 0.0,
    val color: String = "",
    val income: Double = 0.0,
    val expense: Double = 0.0,
    val isSelected: Boolean = false
){
    val iconName: CategoryIconName
        get() {
        return if (isDefault) CategoryIconName.fromString(icon)
        else CategoryIconName.SALARY
    }

    fun isDefaultWallet() = id == DEFAULT_ID

    val startBalance: String get() {
        return if(balance == 0.0) ""
        else balance.toFormatNumber(currency)
    }

    companion object {
        const val DEFAULT_ID = 0L
    }
}

val defaultWalletItem = WalletItemModel()