package com.kthulhu.currencyconverter.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kthulhu.currencyconverter.domain.IConvertInteractor
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val interactor: IConvertInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            IConvertInteractor::class.java
        ).newInstance(interactor)
    }
}

/* подсказка куда вставлять зависимости
class ViewModelFactory @Inject constructor(
    private val factory: PortalFactory,
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            PortalFactory::class.java,
            Repository::class.java
        ).newInstance(factory, repository)
    }
}*/
