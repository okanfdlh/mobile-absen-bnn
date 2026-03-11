package com.example.absenbnn.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenbnn.ui.AppViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelFactory),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loggedIn.collectLatest {
            onLoggedIn()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Login") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Absen Apel Pagi",
                style = MaterialTheme.typography.titleLarge,
            )

            OutlinedTextField(
                value = state.username,
                onValueChange = viewModel::setUsername,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::setPassword,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
            )

            state.errorMessage?.let { msg ->
                Text(text = msg, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = viewModel::login,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                contentPadding = PaddingValues(vertical = 14.dp),
            ) {
                Text(if (state.isLoading) "Memproses..." else "Masuk")
            }

            Text("Default: admin / admin123", style = MaterialTheme.typography.bodySmall)
        }
    }
}

