package ru.silantevdr.kiparotask.domain.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

data class News(
    @field:Element(name = "id")
    @param:Element(name = "id")
    val id: Int?,
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String?,
    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String?,
    @field:Element(name = "date")
    @param:Element(name = "date")
    val date: String?,
    @field:ElementList(name = "keywords")
    @param:ElementList(name = "keywords")
    val keywords: List<String?>?,
    @field:Element(name = "visible")
    @param:Element(name = "visible")
    val visible: Boolean?
)