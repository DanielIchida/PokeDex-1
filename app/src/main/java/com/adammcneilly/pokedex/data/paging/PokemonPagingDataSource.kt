package com.adammcneilly.pokedex.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.data.NetworkState
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.toPokemon
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.models.PokemonDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PokemonPagingDataSource(
    private val pokemonAPI: PokemonAPI,
    private val scope: CoroutineScope,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : PageKeyedDataSource<Int, Pokemon>() {

    private val _initialLoad: MutableLiveData<NetworkState> = MutableLiveData()
    val initialLoad: LiveData<NetworkState> = _initialLoad

    private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val networkState: LiveData<NetworkState> = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Pokemon>
    ) {
        scope.launch(dispatcherProvider.IO) {
            _initialLoad.postValue(NetworkState.LOADING)
            _networkState.postValue(NetworkState.LOADING)

            try {
                val response = pokemonAPI.getPokemon(offset = 0)
                val items = response.results?.mapNotNull(PokemonDTO::toPokemon).orEmpty()

                callback.onResult(
                    items,
                    null,
                    response.nextKey
                )

                _initialLoad.postValue(NetworkState.LOADED)
                _networkState.postValue(NetworkState.LOADED)
            } catch (error: Throwable) {
                _initialLoad.postValue(NetworkState.error(error.message))
                _networkState.postValue(NetworkState.error(error.message))
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Pokemon>
    ) {
        val offset = params.key

        scope.launch(dispatcherProvider.IO) {
            val response = pokemonAPI.getPokemon(offset = offset)
            val items = response.results?.mapNotNull(PokemonDTO::toPokemon).orEmpty()

            callback.onResult(
                items,
                response.nextKey
            )
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Pokemon>
    ) {
        // Not needed
    }
}