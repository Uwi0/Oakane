package com.kakapo.model.wallet

import com.kakapo.model.transaction.TransactionModel

sealed class WalletLogItem<T>(
    open val data: T,

) {
    abstract val id: Long
    abstract val uniqueId: String
    abstract val dateCreated: Long

    data class WalletTransferLogItem(
        override val data: WalletTransferModel
    ) : WalletLogItem<WalletTransferModel>(data) {

        override val id: Long
            get() = data.id
        override val uniqueId: String
            get() = data.uniqueId
        override val dateCreated: Long
            get() = data.createdAt
    }

    data class TransactionLogItem(
        override val data: TransactionModel
    ) : WalletLogItem<TransactionModel>(data) {

        override val id: Long
            get() = data.id
        override val uniqueId: String
            get() = data.uniqueId
        override val dateCreated: Long
            get() = data.dateCreated
    }
}