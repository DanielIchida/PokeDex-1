package com.adammcneilly.pokedex.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.adammcneilly.pokedex.databinding.ListItemPokemonBinding
import com.adammcneilly.pokedex.models.Pokemon

class PokemonPagingAdapter(
    private val pokemonClickListener: ((Pokemon) -> Unit)
) : PagedListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(POKEMON_COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonAdapter.PokemonViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ListItemPokemonBinding.inflate(inflater, parent, false)
        return PokemonAdapter.PokemonViewHolder(binding, pokemonClickListener)
    }

    override fun onBindViewHolder(holder: PokemonAdapter.PokemonViewHolder, position: Int) {
        getItem(position)?.let(holder::bindPokemon)
    }

    companion object {
        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}