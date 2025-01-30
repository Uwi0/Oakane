package com.kakapo.domain.usecase.impl

import com.kakapo.data.model.WalletTransferParam
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.data.repository.base.WalletTransferRepository
import com.kakapo.domain.usecase.base.MoveWalletBalanceUseCase
import com.kakapo.model.wallet.WalletModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MoveWalletBalanceUseCaseImpl(
    private val walletRepository: WalletRepository,
    private val walletTransferRepository: WalletTransferRepository,
    private val dispatcher: CoroutineDispatcher
) : MoveWalletBalanceUseCase {

    override suspend fun execute(moveBalance: WalletTransferParam): Result<Unit> = withContext(dispatcher) {
        if (moveBalance.amount <= 0) {
            return@withContext Result.failure(IllegalArgumentException("Amount must be greater than 0"))
        } else if (walletFromHasBalance(moveBalance)) {
            return@withContext Result.failure(IllegalArgumentException("Insufficient balance"))
        } else if(moveBalance.fromWalletId == moveBalance.toWalletId) {
            return@withContext Result.failure(IllegalArgumentException("Cannot transfer to the same wallet"))
        }else {
            runCatching {
                val walletTransferLogDeferred = async(dispatcher) { walletTransferRepository.save(moveBalance) }
                walletRepository.update(moveBalance.amount, moveBalance.toWalletId).getOrThrow()
                walletRepository.update(-moveBalance.amount, moveBalance.fromWalletId).getOrThrow()
                walletTransferLogDeferred.await().getOrThrow()
            }
        }
    }

    private suspend fun walletFromHasBalance(param: WalletTransferParam): Boolean {
        val walletFrom = walletRepository.loadWalletBy(param.fromWalletId).getOrElse { WalletModel() }
        return walletFrom.balance <= param.amount
    }
}