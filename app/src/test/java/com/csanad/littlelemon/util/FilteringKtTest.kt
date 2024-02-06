package com.csanad.littlelemon.util

import androidx.compose.runtime.mutableStateOf
import com.csanad.littlelemon.MenuItemDatabase
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class FilteringKtTest {
    val lc = MenuItemDatabase(0, "lemon cake", "", 1, "", "cake")
    val ld = MenuItemDatabase(0, "lemon drink", "", 1, "", "drink")
    val aj = MenuItemDatabase(0, "apricot juice", "", 1, "", "drink")
    val condition = mutableStateOf("")
    val data = mutableStateOf(listOf(lc, ld, aj))

    @BeforeEach
    fun setUp() {
        condition.value = ""
    }

    @Test
    fun filterBySearchPhrase_NoSearchPhrase_FullListReturned() {
        val list = filterBySearchPhrase(condition, data)
        assertEquals(data.value, list)
    }

    @Test
    fun filterBySearchPhrase_SetSearchPhrase_OnlyMatchingTitleListReturned() {
        condition.value = "lemon"
        val list = filterBySearchPhrase(condition, data)
        assertEquals(listOf(lc, ld), list)
    }

    @Test
    fun filterBySearchPhrase_SetNonsenseSearchPhrase_EmptyListReturned() {
        condition.value = "blabla"
        val list = filterBySearchPhrase(condition, data)
        assertEquals(emptyList<MenuItemDatabase>(), list)
    }

    @Test
    fun filterBySearchPhrase_IgnoreCase_OnlyMatchingTitleListReturned() {
        condition.value = "Lemon"
        val list = filterBySearchPhrase(condition, data)
        assertEquals(listOf(lc, ld), list)
    }

    @Test
    fun filterByCategory_NoCategory_FullListReturned() {
        val list = filterByCategory(condition, data.value)
        assertEquals(data.value, list)
    }

    @Test
    fun filterByCategory_SetCategory_OnlyMatchingCategoryReturned() {
        condition.value = "drink"
        val list = filterByCategory(condition, data.value)
        assertEquals(listOf(ld, aj), list)
    }

    @Test
    fun filterBySearchPhraseAndCategory_SetSearchPhraseAndCategory_OnlyMatchingTitleAndCategoryListReturned() {
        condition.value = "drink"
        val list = filterByCategory(condition, filterBySearchPhrase(condition, data))
        assertEquals(listOf(ld), list)
    }
}