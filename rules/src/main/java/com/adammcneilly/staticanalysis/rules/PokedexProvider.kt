package com.adammcneilly.staticanalysis.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class PokedexProvider : RuleSetProvider {

    override val ruleSetId: String = "pokedex"

    override fun instance(config: Config): RuleSet = RuleSet(
        ruleSetId,
        listOf(
            NamedParameters(config)
        )
    )
}