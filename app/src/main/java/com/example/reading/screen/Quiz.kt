package com.example.reading

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun QuizScreen(navController: NavController) {
    var answer1 by remember { mutableStateOf(TextFieldValue("")) }
    var answer2 by remember { mutableStateOf(TextFieldValue("")) }
    var answer3 by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
                    selected = false,
                    onClick = { navController.navigate("minilib") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
                    selected = false,
                    onClick = { navController.navigate("notes") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    selected = false,
                    onClick = { /* 설정 페이지로 이동 */ }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // 퀴즈 1
            Text(text = "퀴즈 1: ----", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = answer1,
                onValueChange = { answer1 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 퀴즈 2
            Text(text = "퀴즈 2: ----", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = answer2,
                onValueChange = { answer2 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 퀴즈 3
            Text(text = "퀴즈 3: ----", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = answer3,
                onValueChange = { answer3 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 결과 버튼
            Button(
                onClick = {
                    // 결과 보기 페이지로 이동 또는 처리
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "퀴즈 끝! 나의 결과는 >", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
