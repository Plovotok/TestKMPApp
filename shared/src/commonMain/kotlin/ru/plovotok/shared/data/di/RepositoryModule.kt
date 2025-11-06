package ru.plovotok.shared.data.di

import org.koin.dsl.module
import ru.plovotok.shared.data.impl.BooksRepositoryImpl
import ru.plovotok.shared.data.impl.BooksTestRepository
import ru.plovotok.shared.domain.BooksRepository

internal val repositoryModule = module {
//    factory<BooksRepository> { BooksRepositoryImpl(get(), get()) }
    factory<BooksRepository> { BooksTestRepository(get()) }
}