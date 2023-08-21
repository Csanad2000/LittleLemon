package com.csanad.littlelemon.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.csanad.littlelemon.Profile
import com.csanad.littlelemon.R
import com.csanad.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
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
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        //Home()
    }
}