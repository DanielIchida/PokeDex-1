package com.adammcneilly.pokedex.data

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.adammcneilly.pokedex.data.paging.PokemonPagingDataSourceFactory
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.models.toPokemon
import com.adammcneilly.pokedex.models.toPokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI
import java.util.concurrent.Executor
import kotlinx.coroutines.CoroutineScope

/**
 * Implementation of a [PokemonRepository] that fetch from both local and remote sources.
 */
open class PokemonService(
    private val database: PokedexDatabase?,
    private val api: PokemonAPI,
    private val networkExecutor: Executor
) : PokemonRepository {
    override suspend fun getPokemon(): PokemonResponse? {
        return api.getPokemon().toPokemonResponse()
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon? {
        return getPokemonDetailFromDatabase(pokemonName) ?: getPokemonDetailFromNetwork(pokemonName)
    }

    override suspend fun getPokemonPaging(scope: CoroutineScope): Listing<Pokemon> {
        val sourceFactory = PokemonPagingDataSourceFactory(api, scope)

        val livePagedList = sourceFactory.toLiveData(
            pageSize = PAGE_SIZE,
            fetchExecutor = networkExecutor
        )

        val networkState = Transformations.switchMap(sourceFactory.pokemonDataSource) { dataSource ->
            dataSource.networkState
        }

        val refreshState = Transformations.switchMap(sourceFactory.pokemonDataSource) { dataSource ->
            dataSource.initialLoad
        }

        return Listing(
            pagedList = livePagedList,
            networkState = networkState,
            refreshState = refreshState,
            retry = {
                // TODO:
            },
            refresh = {
                // TODO:
            }
        )
    }

    private suspend fun getPokemonDetailFromDatabase(pokemonName: String): Pokemon? {
        return database?.getPokemonByName(pokemonName)?.toPokemon()
    }

    private suspend fun getPokemonDetailFromNetwork(pokemonName: String): Pokemon? {
        return api.getPokemonDetail(pokemonName).toPokemon()?.also {
            database?.insertPokemon(it.toPersistablePokemon())
        }
    }

    companion object {
        private const val PAGE_SIZE = 60
    }
}