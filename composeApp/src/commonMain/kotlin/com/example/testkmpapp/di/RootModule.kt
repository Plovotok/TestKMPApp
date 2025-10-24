package com.example.testkmpapp.di

import com.example.testkmpapp.data.di.networkModule
import com.example.testkmpapp.data.di.repositoryModule

fun appModule() = listOf(
    platformModule,
    networkModule,
    repositoryModule
)