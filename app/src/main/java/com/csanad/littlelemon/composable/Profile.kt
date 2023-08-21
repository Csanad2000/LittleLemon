package com.csanad.littlelemon.composable

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.csanad.littlelemon.Onboarding
import com.csanad.littlelemon.R
import com.csanad.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController, prefs: SharedPreferences) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(20.dp),
            alignment = Alignment.TopStart
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .padding(20.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Personal information", modifier = Modifier.padding(vertical = 20.dp))
            TextField(
                value = prefs.getString("firstName", "")!!,
                onValueChange = {},
                placeholder = { Text(text = "First name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                readOnly = true
            )
            TextField(
                value = prefs.getString("lastName", "")!!,
                onValueChange = {},
                placeholder = { Text(text = "Last name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                readOnly = true
            )
            TextField(
                value = prefs.getString("emailAddress", "")!!,
                onValueChange = {},
                placeholder = { Text(text = "Email address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                readOnly = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.weight(1.0f))
        }
        Button(
            onClick = {
                if (prefs.edit().clear().commit())
                    navController.navigate(Onboarding.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            )
        ) { Text(text = "Log out") }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        //Profile()
    }
}