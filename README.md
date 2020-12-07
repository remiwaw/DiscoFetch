# Clean-Notes

**A simple app using http://auth.discogs.com/ api**

I've tried to use https://developer.android.com/reference/kotlin/androidx/paging/RemoteMediator to implement cache.
Following problem appears: discoOgs sorting schema is not clear.

So its impossible to sync entries from DB and server, but we dont know sorting schema used by server.
We need to use the same sorting when retrieving the data from DB and server.

More details here
https://github.com/googlecodelabs/android-paging/issues/127

DONE:
* Basic version: fetching albums, album info, artist info
* Uses:
    * MVI
    * RecyclerView
    * Paging
    * Retrofit
    * Coroutines
    * Data binding
    * Offline notification

TODO:
* Better error handling in network layer - now errors are handled directly in viewmodels. Api Result should be wrapped in a wrapper class using for example following:

```kotlin
sealed class ApiResult<out T> {

    data class Success<out T>(val value: T): ApiResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ): ApiResult<Nothing>()

    object NetworkError: ApiResult<Nothing>()
}
```

Then there should be an error handler, dealing with Network errors.

* UnitTests
* Espresso test
* Generic classes for MVI
* Caching
* Styling
* Remove not used dependencies

![](DiscoFetchSmall.gif)

# Credits
https://github.com/android/architecture-components-samples/tree/paging2/PagingWithNetworkSample
https://codingwithmitch.com/
