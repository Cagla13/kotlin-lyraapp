package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryapp.ui.viewmodel.AuthState
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Kütüphane Sistemi", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Yeni Hesap Oluştur")

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value = name,
            label = { Text("Ad Soyad") },
            onValueChange = { name = it },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))


        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = { Text("E-posta") },
            onValueChange = { email = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(10.dp))


        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { Text("Şifre") },
            onValueChange = { password = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Kayıt Butonu ve Yükleniyor Durumu
        if (authState is AuthState.Loading) {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Button(
                onClick = {

                    authViewModel.signIn(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kayıt Ol")
            }
        }


        TextButton(onClick = onNavigateToLogin) {
            Text("Zaten hesabın var mı? Giriş Yap")
        }


        if (authState is AuthState.Success) {
            Text("Hesap başarıyla oluşturuldu!", color = MaterialTheme.colorScheme.primary)
        } else if (authState is AuthState.Error) {
            Text((authState as AuthState.Error).message, color = MaterialTheme.colorScheme.error)
        }
    }
}