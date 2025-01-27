package com.kakapo.domain.usecase.impl

import com.kakapo.data.model.GoalSavingParam
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.GoalSavingsRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.AddGoalSavingUseCase
import com.kakapo.model.wallet.WalletModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AddGoalSavingUseCaseImpl(
    private val goalRepository: GoalRepository,
    private val walletRepository: WalletRepository,
    private val goalSavingsRepository: GoalSavingsRepository,
    private val dispatcher: CoroutineDispatcher
) : AddGoalSavingUseCase {

    override suspend fun execute(param: GoalSavingParam): Result<Unit> =
        withContext(dispatcher) {
            if (param.amount <= 0) return@withContext Result.failure(Exception("Balance must be greater than 0"))
            if (hasSufficientBalance(param.walletId, param.amount)) runCatching {
                val addSavedGoalDeferred = async(dispatcher) {
                    goalRepository.addSaved(param.amount, param.goalId)
                }
                val saveGoalTransactionDeferred = async(dispatcher) {
                    goalSavingsRepository.saveGoal(param)
                }
                val updateWalletDeferred = async(dispatcher) {
                    walletRepository.update(-param.amount, param.walletId)
                }

                addSavedGoalDeferred.await().getOrThrow()
                saveGoalTransactionDeferred.await().getOrThrow()
                updateWalletDeferred.await().getOrThrow()
            } else {
                Result.failure(Exception("Insufficient balance"))
            }
        }

    private suspend fun hasSufficientBalance(id: Long, amount: Double): Boolean {
        val wallet = walletRepository.loadWalletBy(id).getOrElse { WalletModel() }
        return wallet.balance >= amount
    }
}