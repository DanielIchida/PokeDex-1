package com.adammcneilly.staticanalysis.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtFile

class NamedParameters(config: Config) : Rule(config) {
    override val issue: Issue
        get() = Issue(
            id = "Use Named Parameters",
            severity = Severity.Maintainability,
            description = "Use named parameters when calling methods with multiple arguments.",
            debt = Debt.FIVE_MINS
        )

    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)
        report(CodeSmell(issue, Entity.from(file), "Name: ${file.name}"))
    }
}