package com.kakapo.model.wallet

enum class WalletTransferType {
    Outgoing, Incoming
}

fun String.toWalletTransferType(): WalletTransferType {
    return when(this) {
        "OUTGOING" -> WalletTransferType.Outgoing
        "INCOMING" -> WalletTransferType.Incoming
        else -> throw IllegalArgumentException("Unknown wallet transfer type: $this")
    }
}