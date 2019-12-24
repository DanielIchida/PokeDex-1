package com.adammcneilly.pokedex.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 *
 * @property[pagedList] The [LiveData] of [PagedList] items for the UI to observe.
 * @property[networkState] Represents the current network request status to show the user.
 * @property[refreshState] Similar to [networkState], but specific to scenarios where a refresh
 * is requested or the initial load.
 * @property[refresh] A callback to refresh all data and fetch from scratch.
 * @property[retry] A callback to retry any failed requests.
 */
data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)