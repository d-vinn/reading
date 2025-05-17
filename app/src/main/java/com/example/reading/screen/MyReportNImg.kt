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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MyReportNImgScreen(navController: NavController, thoughtTextFromReport: String = "") {
    var thoughtText by remember { mutableStateOf(thoughtTextFromReport) }

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
                    selected = true,
                    onClick = { navController.navigate("notes") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    selected = false,
                    onClick = { /* 설정 화면 이동 */ }
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
            // 도서명 & 저자
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("도서명 : --", fontSize = 18.sp)
                Text("저자 : --", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 감상평
            Text(text = "감상평 :", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(1.dp, Color.Gray)
                    .padding(12.dp)
            ) {
                BasicTextField(
                    value = thoughtText,
                    onValueChange = { thoughtText = it },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )
                if (thoughtText.isEmpty()) {
                    Text(
                        text = "감상평이 없습니다.",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 내가 그린 장면
            Text(text = "내가 그린 장면 :", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .border(1.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "그림 미리보기 영역", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
