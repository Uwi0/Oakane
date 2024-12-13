package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.WalletEntity
import com.kakapo.oakane.model.wallet.WalletModel

fun WalletEntity.toWalletModel(): WalletModel {
    return WalletModel(
        id = id,
        currency = "Rp",
        balance = balance,
        name = name,
        isDefault = false,
        icon = "quas"
    )
}