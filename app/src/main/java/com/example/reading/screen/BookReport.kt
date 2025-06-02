package com.example.reading

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.material3.TextFieldDefaults

@Composable
fun BookReportScreen(navController: NavController) {
    // 상태들
    var selectedYear by remember { mutableStateOf("____년") }
    var expandedYear by remember { mutableStateOf(false) }

    var selectedMonth by remember { mutableStateOf("__월") }
    var expandedMonth by remember { mutableStateOf(false) }

    var selectedDay by remember { mutableStateOf("__일") }
    var expandedDay by remember { mutableStateOf(false) }

    var thoughtText by remember { mutableStateOf("") }

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
                    onClick = { /* settings */ }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // 도서 정보
            Text(text = "도서명 : --", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "저자 : --", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "쪽수 : ", fontSize = 18.sp)

                var pageCount by remember { mutableStateOf("") }

                TextField(
                    value = pageCount,
                    onValueChange = {
                        if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                            pageCount = it
                        }
                    },
                    modifier = Modifier
                        .width(80.dp)
                        .height(56.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "쪽", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 날짜 선택
            Text(text = "읽은 날짜 :", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // 연도 선택
                Box {
                    Button(onClick = { expandedYear = true }) {
                        Text(selectedYear)
                    }
                    DropdownMenu(
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false }
                    ) {
                        (2020..2040).forEach { year ->
                            DropdownMenuItem(
                                text = { Text("$year 년") },
                                onClick = {
                                    selectedYear = "$year 년"
                                    expandedYear = false
                                }
                            )
                        }
                    }
                }

                // 월 선택
                Box {
                    Button(onClick = { expandedMonth = true }) {
                        Text(selectedMonth)
                    }
                    DropdownMenu(
                        expanded = expandedMonth,
                        onDismissRequest = { expandedMonth = false }
                    ) {
                        (1..12).forEach { month ->
                            DropdownMenuItem(
                                text = { Text("${month}월") },
                                onClick = {
                                    selectedMonth = "${month}월"
                                    expandedMonth = false
                                }
                            )
                        }
                    }
                }

                // 일 선택
                Box {
                    Button(onClick = { expandedDay = true }) {
                        Text(selectedDay)
                    }
                    DropdownMenu(
                        expanded = expandedDay,
                        onDismissRequest = { expandedDay = false }
                    ) {
                        (1..31).forEach { day ->
                            DropdownMenuItem(
                                text = { Text("${day}일") },
                                onClick = {
                                    selectedDay = "${day}일"
                                    expandedDay = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 감상 텍스트 입력
            Text("어떤 생각이 들었나요?", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

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
                        text = "여기에 입력하세요...",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
