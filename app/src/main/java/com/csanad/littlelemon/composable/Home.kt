package com.csanad.littlelemon.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.csanad.littlelemon.LittleLemonDatabase
import com.csanad.littlelemon.MenuItemDatabase
import com.csanad.littlelemon.Profile
import com.csanad.littlelemon.R
import com.csanad.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavHostController, database: LittleLemonDatabase) {
    val searchPhrase = remember {
        mutableStateOf("")
    }

    val menu = database.menuDao().getItems().observeAsState(emptyList())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon logo",
                modifier = Modifier
                    .height(100.dp)
                    .padding(20.dp)
                    .weight(1.0f),
                alignment = Alignment.TopStart
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile button",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(20.dp)
                    .clickable { navController.navigate(Profile.route) },
                alignment = Alignment.TopEnd
            )
        }
        Hero(searchPhrase = searchPhrase)
        MenuItems(data = (if (searchPhrase.value.isBlank()) {
            menu.value
        } else {
            menu.value.filter {
                it.title.contains(searchPhrase.value, true)
            }
        }))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hero(searchPhrase: MutableState<String>) {
    Column(
        modifier = Modifier
            .background(Color.Green)
            .padding(20.dp)
    ) {
        Text(text = "Little Lemon", modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp))
        Row {
            Column(
                modifier = Modifier.weight(0.66f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Chicago", modifier = Modifier.padding(bottom = 20.dp))
                Text(text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.")
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero image",
                modifier = Modifier.weight(0.34f)
            )
        }
        TextField(
            value = searchPhrase.value,
            onValueChange = { searchPhrase.value = it },
            placeholder = { Text(text = "Enter Search Phrase") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
    }
}

@Composable
fun MenuItems(data: List<MenuItemDatabase>) {
    var category by remember {
        mutableStateOf("")
    }

    val categorized = if (category.isBlank()) {
        data
    } else {
        data.filter { it.category == category }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .padding(20.dp)
            .horizontalScroll(
                rememberScrollState()
            )
    ) { //Chips are also an option
        Button(onClick = { category = if (category == "starters") "" else "starters" }) {
            Text(text = "Starters")
        }
        Button(onClick = { category = if (category == "mains") "" else "mains" }) {
            Text(text = "Mains")
        }
        Button(onClick = { category = if (category == "desserts") "" else "desserts" }) {
            Text(text = "Desserts")
        }
        Button(onClick = { category = if (category == "drinks") "" else "drinks" }) {
            Text(text = "Drinks")
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(categorized.size) { index -> MenuItem(item = categorized[index]) }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemDatabase) {
    Row(modifier = Modifier.padding(20.dp)) {
        Column(modifier = Modifier.weight(0.66f)) {
            Text(text = item.title)
            Text(
                text = item.description,
                maxLines = 2,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(text = "$${item.price}")
        }
        GlideImage(
            model = item.image.toUri(),
            contentDescription = "A picture of the dish.",
            modifier = Modifier
                .weight(0.34f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        //Home()
    }
}