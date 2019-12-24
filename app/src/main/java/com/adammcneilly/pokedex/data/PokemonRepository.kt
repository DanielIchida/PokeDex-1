package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import kotlinx.coroutines.CoroutineScope

interface PokemonRepository {
    suspend fun getPokemon(): PokemonResponse?
    fun getPokemonPaging(scope: CoroutineScope): Listing<Pokemon>
    suspend fun getPokemonDetail(pokemonName: String): Pokemon?
}