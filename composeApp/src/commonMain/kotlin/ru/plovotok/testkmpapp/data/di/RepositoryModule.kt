package ru.plovotok.testkmpapp.data.di

import ru.plovotok.testkmpapp.data.impl.BooksRepositoryImpl
import ru.plovotok.testkmpapp.data.impl.BooksTestRepository
import ru.plovotok.testkmpapp.domain.BooksRepository
import org.koin.dsl.module

val repositoryModule = module {
//    factory<BooksRepository> { BooksRepositoryImpl(get(), get()) }
    factory<BooksRepository> { BooksTestRepository(get()) }
}