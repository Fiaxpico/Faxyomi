package faxyomi.domain.source.model

import eu.fiax.faxyomi.source.model.FilterList

data class EXHSavedSearch(
    val id: Long,
    val name: String,
    val query: String?,
    val filterList: FilterList?,
)
