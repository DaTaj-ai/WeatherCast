package com.example.weathercast

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercast.navigation.navSetup
import com.example.weathercast.ui.theme.WeatherCastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherCastTheme {
                Scaffold(modifier = Modifier.fillMaxSize() ,
                    bottomBar = {
                        BottomAppBar(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Bottom app bar",
                            )
                        }
                    },
                ) { innerPadding ->
                    navSetup()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherCastTheme {
        Greeting("Android")
    }
}


@Composable
fun SignUpScreen(onSignUpClick: (email: String, password: String) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text("Email") }
        )

        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text("Password") }
        )
        TextField(
            value = confirmPassword,
            onValueChange = { newConfirmPassword ->
                confirmPassword = newConfirmPassword
            }, visualTransformation = PasswordVisualTransformation(),
            label = { Text("Confirm Password") }
        )
        Button(
            onClick = {
                Log.i("TAG", "SignUpScreen:  $email.v $password")
                if (password == confirmPassword) {
                    onSignUpClick(email, password)
                } else {
                    val toast =
                        Toast.makeText(context, "Check Password", Toast.LENGTH_SHORT) // in Activity
                    toast.show()
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Sign Up")
        }
    }
}


@Composable
fun LoginScreen(onSignInClick: (email: String, password: String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            visualTransformation = PasswordVisualTransformation()
            ,label = { Text("Password") }
        )
        Button(
            onClick = {
                Log.i("TAG", "LoginScreen: $email $password")

                onSignInClick(email, password)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login")
        }

    }
}

