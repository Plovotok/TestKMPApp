package com.example.testkmpapp.data.di

import com.example.testkmpapp.data.impl.BooksRepositoryImpl
import com.example.testkmpapp.data.impl.BooksTestRepository
import com.example.testkmpapp.domain.BooksRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<BooksRepository> { BooksRepositoryImpl(get(), get()) }
//    factory<BooksRepository> { BooksTestRepository(get()) }
}