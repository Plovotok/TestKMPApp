package com.example.testkmpapp.presentation

class Paginator <Key, Item> (
    private val initialKey: Key,
    private val onLoadUpdated: (currentKey: Key, Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<Item>,
    private val getNextKey: suspend (currentKey: Key, result: Item) -> Key,
    private val onError: suspend (currentKey: Key, Throwable?) -> Unit,
    private val onSuccess: suspend (result: Item, newKey: Key) -> Unit,
    private val endReached: (currentKey: Key, result: Item) -> Boolean
) {

    private var isMakingRequest = false
    private var currentKey = initialKey
    private var isEndReached: Boolean = false
    private var hasError = false  // NEW FLAG

    fun reset() {
        currentKey = initialKey
        isEndReached = false
        hasError = false
    }

    suspend fun retry() = loadNextItems(true)

    suspend fun loadNextItems(force: Boolean = false) {
        if (isMakingRequest) return
        if (isEndReached) return  // Prevent further loading if error occurred
        if (hasError && !force) return

        hasError = false
        isMakingRequest = true
        onLoadUpdated(currentKey, true)

        val result = onRequest(currentKey)
        isMakingRequest = false
        val item = result.getOrElse {
            onError(currentKey, it)
            hasError = true
            onLoadUpdated(currentKey, false)
            return
        }

        hasError = false
        currentKey = getNextKey(currentKey, item)
        onSuccess(item, currentKey)
        onLoadUpdated(currentKey, false)
        isEndReached = endReached(currentKey, item)
    }

}