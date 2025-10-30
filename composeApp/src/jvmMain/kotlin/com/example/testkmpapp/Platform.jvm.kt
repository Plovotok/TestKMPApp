package com.example.myapplication

import com.example.testkmpapp.Platform

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

fun getPlatform(): Platform = JVMPlatform()