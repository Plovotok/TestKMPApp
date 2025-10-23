package com.example.testkmpapp

import kotlinx.serialization.json.Json

internal val json =
    Json {
        allowStructuredMapKeys = true
    }