package com.adammcneilly.pokedex.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.network.PokemonAPI
import kotlinx.coroutines.CoroutineScope

class PokemonPagingDataSourceFactory(
    private val pokemonAPI: PokemonAPI,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Pokemon>() {
    private lateinit var dataSource: PokemonPagingDataSource
    val pokemonDataSource = MutableLiveData<PokemonPagingDataSource>()

    override fun create(): DataSource<Int, Pokemon> {
        dataSource = PokemonPagingDataSource(
            pokemonAPI = pokemonAPI,
            scope = scope
        )
        pokemonDataSource.postValue(dataSource)
        return dataSource
    }
}