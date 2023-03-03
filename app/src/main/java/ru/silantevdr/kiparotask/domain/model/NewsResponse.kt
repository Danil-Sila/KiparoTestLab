package ru.silantevdr.kiparotask.domain.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "news", strict = false)
data class NewsResponse constructor(
    @field:ElementList(name = "news")
    @param:ElementList(name = "news")
    var news: List<News>
)