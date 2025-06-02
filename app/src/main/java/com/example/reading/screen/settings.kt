package com.example.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font

@Composable
fun SettingsScreen(navController: NavController) {
    val kidFont = FontFamily(Font(R.font.uhbee_puding))
    val selectedBgColor = Color(0xFFB9D99A) // #B9D99A
    val unselectedColor = Color.Gray
    var selectedIndex by remember { mutableStateOf(3) }
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    modifier = Modifier.height(70.dp)
                ) {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0
                            navController.navigate("home")},
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("home", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 0) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = {
                            selectedIndex = 1
                            navController.navigate("minilib")
                        },
                        icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Library") },
                        label = { Text("library", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 1) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = {
                            selectedIndex = 2
                            navController.navigate("notes")
                        },
                        icon = { Icon(Icons.Default.Email, contentDescription = "Books") },
                        label = { Text("books", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 2) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 3,
                        onClick = {
                            selectedIndex = 3
                            navController.navigate("set")},
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Setting") },
                        label = { Text("setting", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 3) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "이름 : ---", style = MaterialTheme.typography.bodyLarge, fontSize=24.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "나이 : ---", style = MaterialTheme.typography.bodyLarge, fontSize=24.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "성별 : ---", style = MaterialTheme.typography.bodyLarge, fontSize=24.sp)
        }
    }
}
