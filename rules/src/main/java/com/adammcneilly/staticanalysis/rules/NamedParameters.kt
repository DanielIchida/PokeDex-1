package com.adammcneilly.staticanalysis.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression

class NamedParameters(config: Config) : Rule(config) {
    override val issue: Issue
        get() = Issue(
            id = "Use Named Parameters",
            severity = Severity.Maintainability,
            description = "Use named parameters when calling methods with multiple arguments.",
            debt = Debt.FIVE_MINS
        )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
    }
}