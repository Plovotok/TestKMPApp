package com.example.testkmpapp.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.testkmpapp.ui.BaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeContent(
    component: WelcomeComponent,
    modifier: Modifier = Modifier
) {
    val model by component.model.subscribeAsState()

    BaseScreen(
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome Screen") },
                navigationIcon = {
                    TextButton(onClick = component::onBackClicked) {
                        Text(text = "Back")
                    }
                },
            )
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "PromoCode ${component.promo}"
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 20.dp).widthIn(max = 260.dp)
            ) {

                TextField(
                    value = model.login,
                    onValueChange = component::onLoginChange,
                    placeholder = {
                        Text(text = "login")
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = model.password,
                    onValueChange = component::onPasswordChange,
                    placeholder = {
                        Text(text = "password")
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { component.onRegister() },
            ) {
                Text("Sign up")
            }
        }
    }
}