package com.csanad.littlelemon.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.csanad.littlelemon.LittleLemonDatabase
import com.csanad.littlelemon.MenuItemDatabase
import com.csanad.littlelemon.Profile
import com.csanad.littlelemon.R
import com.csanad.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavHostController, database: LittleLemonDatabase) {
    val menu = database.menuDao().getItems().observeAsState(emptyArray())

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
        Hero()
        Box(modifier = Modifier.weight(1.0f)) { MenuItems(menu.value) }
    }
}

@Composable
fun Hero() {
    Row(
        modifier = Modifier
            .background(Color.Green)
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.weight(0.6f)) {
            Text(text = "Little Lemon")
            Text(text = "Chicago")
            Text(text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.")
        }
        Image(
            painter = painterResource(id = R.drawable.hero_image),
            contentDescription = "Hero image",
            modifier = Modifier.weight(0.4f)
        )
    }
}

@Composable
fun MenuItems(data: Array<MenuItemDatabase>) {
    LazyColumn {
        items(data.size) { index -> MenuItem(item = data[index]) }
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