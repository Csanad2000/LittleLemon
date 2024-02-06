package com.csanad.littlelemon.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.csanad.littlelemon.MenuItemDatabase

fun filterBySearchPhrase(
    searchPhrase: MutableState<String>,
    menu: State<List<MenuItemDatabase>>): List<MenuItemDatabase> {
    return if (searchPhrase.value.isBlank()) {
        menu.value
    } else {
        menu.value.filter {
            it.title.contains(searchPhrase.value, true)
        }
    }
}

fun filterByCategory(category: MutableState<String>, data: List<MenuItemDatabase>): List<MenuItemDatabase> {
    return if (category.value.isBlank()) {
        data
    } else {
        data.filter { it.category == category.value }
    }
}