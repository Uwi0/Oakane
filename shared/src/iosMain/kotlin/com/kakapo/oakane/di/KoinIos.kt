package com.kakapo.oakane.di

import com.kakapo.oakane.data.database.MySqlDriverFactory
import com.kakapo.oakane.data.preference.OakanePreferenceDataStoreFactory
import com.kakapo.oakane.presentation.AddGoalViewModelAdapter
import com.kakapo.oakane.presentation.AddTransactionViewModelAdapter
import com.kakapo.oakane.presentation.CategoriesViewModelAdapter
import com.kakapo.oakane.presentation.GoalViewModelAdapter
import com.kakapo.oakane.presentation.GoalsViewModelAdapter
import com.kakapo.oakane.presentation.HomeViewModelAdapter
import com.kakapo.oakane.presentation.MonthlyBudgetViewModelAdapter
import com.kakapo.oakane.presentation.TransactionViewModelAdapter
import com.kakapo.oakane.presentation.TransactionsViewModelAdapter
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

object KoinIos {
    fun initialize(): KoinApplication = initKoin(
        appModule = module {
            factory { HomeViewModelAdapter(get(), get()) }
            factory { TransactionsViewModelAdapter(get(), get()) }
            factory { TransactionViewModelAdapter(get(), get()) }
            factory { AddTransactionViewModelAdapter(get(), get()) }
            factory { CategoriesViewModelAdapter(get(), get()) }
            factory { AddGoalViewModelAdapter(get(), get()) }
            factory { GoalViewModelAdapter(get(), get()) }
            factory { GoalsViewModelAdapter(get(), get()) }
            factory { MonthlyBudgetViewModelAdapter(get(), get()) }
        }
    )
}

@OptIn(kotlinx.cinterop.BetaInteropApi::class)
fun Koin.get(objCClass: ObjCClass): Any {
    val kClass = getOriginalKotlinClass(objCClass)!!
    return get(kClass)
}

@OptIn(kotlinx.cinterop.BetaInteropApi::class)
fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

actual val platformModule: Module = module {
    single { MySqlDriverFactory().createDriver() }
    single { OakanePreferenceDataStoreFactory().dataStore() }
}
