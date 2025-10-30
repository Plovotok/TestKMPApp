package ru.plovotok.testkmpapp

import ru.plovotok.testkmpapp.Platform

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

fun getPlatform(): Platform = JVMPlatform()