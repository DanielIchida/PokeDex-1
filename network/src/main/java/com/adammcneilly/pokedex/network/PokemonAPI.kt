package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO
import com.adammcneilly.pokedex.network.retrofit.RetrofitPokemonAPI

@Suppress("UNUSED_PARAMETER")
abstract class PokemonAPI(baseUrl: String) {
    abstract suspend fun getPokemon(): PokemonResponseDTO
    abstract suspend fun getPokemon(offset: Int): PokemonResponseDTO
    abstract suspend fun getPokemonDetail(pokemonName: String): PokemonDTO
}

class DefaultPokemonAPI(baseUrl: String) : PokemonAPI(baseUrl) {
    private val retrofitAPI = RetrofitPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponseDTO {
        return getPokemon(offset = 0)
    }

    override suspend fun getPokemon(offset: Int): PokemonResponseDTO {
        return retrofitAPI.getPokemonAsync(offset = offset).await()
    }

    override suspend fun getPokemonDetail(pokemonName: String): PokemonDTO {
        return retrofitAPI.getPokemonDetailAsync(pokemonName).await()
    }
}