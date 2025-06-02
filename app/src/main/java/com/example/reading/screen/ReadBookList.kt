package com.example.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.TextUnit
import androidx.compose.foundation.clickable


@Composable
fun ReadBookListScreen(navController: NavController, viewModel: UserRecommendationProfileViewModel) {
    val kidFont = FontFamily(Font(R.font.uhbee_puding))
    val selectedBgColor = Color(0xFFB9D99A) // #B9D99A
    val unselectedColor = Color.Gray
    var selectedIndex by remember { mutableStateOf(2) }
    val userName by viewModel.userName.collectAsState()
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
                        onClick = { selectedIndex = 0 },
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
                .padding(16.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.light_green),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        // ViewModel에서 가져온 사용자 이름을 표시하거나, 이름이 없을 경우 기본값("사용자")을 표시
                        append(userName ?: "사용자") // <-- 'OO' 대신 userName 변수 사용
                    }
                    append("이가 읽은 책들\n")
                },
                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = kidFont,
                    fontSize = 36.sp
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp), // 각 선 높이 120
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { rowIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        if (rowIndex == 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .align(Alignment.BottomCenter),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                // "강아지 똥": 더 큰 높이, 클릭 없음
                                BookSpine(
                                    title = "강\n아\n지\n똥",
                                    modifier = Modifier.height(90.dp),
                                    fontSize = 16.sp
                                )
                                // "책먹는여우": 더 작은 높이, 클릭 시 "eval" 페이지로 이동
                                Box(
                                    modifier = Modifier
                                        .height(110.dp)
                                        .clickable { navController.navigate("eval") }
                                ) {
                                    BookSpine(
                                        title = "책\n먹\n는\n여\n우",
                                        fontSize = 16.sp
                                    )
                                }
                            }

                        }
                        // 책장 선 (나무판)
                        Divider(
                            color = Color(0xFF8B4513), // 갈색
                            thickness = 18.dp,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }


        }
    }
}


@Composable
fun BookSpine(
    title: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp
) {
    Box(
        modifier = modifier
            .width(40.dp)
            .background(Color(0xFFB9D99A), shape = RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = fontSize,
            lineHeight = fontSize, // lineHeight를 fontSize와 같게!
            color = Color.Black,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}


@Composable
fun BookButton(title: String) {
    Button(
        onClick = { /* TODO: 책 클릭 처리 */ },
        modifier = Modifier
            .width(80.dp) // 너비 키움
            .height(64.dp), // 높이는 기존 유지
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray) // 더 보기 쉬운 색
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

