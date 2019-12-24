package com.adammcneilly.pokedex.data.paging

import androidx.paging.PageKeyedDataSource
import com.adammcneilly.pokedex.DispatcherProvider
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

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Pokemon>
    ) {
        scope.launch(dispatcherProvider.IO) {
            val response = pokemonAPI.getPokemon(offset = 0)
            val items = response.results?.mapNotNull(PokemonDTO::toPokemon).orEmpty()

            callback.onResult(
                items,
                null,
                response.nextKey
            )
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Pokemon>
    ) {
        val offset = params.key

        scope.launch(dispatcherProvider.IO) {
            val response = pokemonAPI.getPokemon(offset = offset)
            callback.onResult(
                response.results?.map(PokemonDTO::toPokemon).orEmpty(),
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