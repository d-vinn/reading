package com.example.reading

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.res.colorResource

@Composable
fun MakeAiImgScreen(navController: NavController) {
    val context = LocalContext.current
    val kidFont = FontFamily(Font(R.font.uhbee_puding))
    val selectedBgColor = Color(0xFFB9D99A) // #B9D99A
    val unselectedColor = Color.Gray


    var selectedIndex by remember { mutableStateOf(2) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 이미지 선택용 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (uri == null) {
            Toast.makeText(context, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // 상단 도서 정보
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("도서명 : 책 먹는 여우\n저자 : 프란치스카 비어만", fontSize = 32.sp, style = TextStyle(fontFamily = kidFont))
            }

            Spacer(modifier = Modifier.height(22.dp))

            // +그림 올리기 버튼
            Button(onClick = {
                galleryLauncher.launch("image/*")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                )
            ) {
                Text("+그림 올리기", style = TextStyle(fontFamily = kidFont))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 큰 네모 영역: 이미지 미리보기 또는 빈칸
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .border(1.dp, Color.Gray)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "선택한 이미지",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("여기에 이미지가 표시됩니다", color = Color.Gray, style = TextStyle(fontFamily = kidFont))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 완료 버튼 추가
            Button(
                onClick = { navController.navigate("eval") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                )
            ) {
                Text(text = "완료", style = TextStyle(fontFamily = kidFont))
            }
        }
    }
}
