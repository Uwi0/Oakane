package com.kakapo.domain.usecase.impl

import com.kakapo.data.model.WalletTransferParam
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.data.repository.base.WalletTransferRepository
import com.kakapo.domain.usecase.base.AddWalletTransferUseCase
import com.kakapo.model.wallet.WalletModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AddWalletTransferUseCaseImpl(
    private val walletRepository: WalletRepository,
    private val walletTransferRepository: WalletTransferRepository,
    private val dispatcher: CoroutineDispatcher
) : AddWalletTransferUseCase {

    override suspend fun execute(walletTransfer: WalletTransferParam): Result<Unit> = withContext(dispatcher) {
        if (walletTransfer.amount <= 0) {
            return@withContext Result.failure(IllegalArgumentException("Amount must be greater than 0"))
        } else if (walletFromHasBalance(walletTransfer)) {
            return@withContext Result.failure(IllegalArgumentException("Insufficient balance"))
        } else {
            runCatching {
                val walletTransferLogDeferred = async(dispatcher) { walletTransferRepository.save(walletTransfer) }
                walletRepository.update(walletTransfer.amount, walletTransfer.toWalletId).getOrThrow()
                walletRepository.update(-walletTransfer.amount, walletTransfer.fromWalletId).getOrThrow()
                walletTransferLogDeferred.await().getOrThrow()
            }
        }
    }

    private suspend fun walletFromHasBalance(param: WalletTransferParam): Boolean {
        val walletFrom =
            walletRepository.loadWalletBy(param.fromWalletId).getOrElse { WalletModel() }
        return walletFrom.balance >= param.amount
    }
}