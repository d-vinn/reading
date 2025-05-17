package com.example.reading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import kotlinx.coroutines.delay
import com.example.reading.ui.theme.ReadingTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reading.ui.theme.ReadingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadingTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "splash") {
                        composable("splash") { SplashScreen(navController) }
                        composable("consent") { ConsentScreen(navController) }
                        composable("emotion") { EmotionSelectionScreen(navController) }
                        composable("TodayDo") { TodayDoScreen(navController) }
                        composable("category") { CategoryScreen(navController) }
                        composable("home") { HomeScreen(navController)}
                        composable("BookContents") { BookContentsScreen(navController) }
                        composable("TodayRec") { TodayRecScreen(navController)}
                        composable("minilib") { MiniLibScreen(navController)}
                        composable(
                            route = "bookCase/{bookshelfName}",
                            arguments = listOf(
                                navArgument("bookshelfName") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            // navArgument에서 책장 이름 가져오기
                            val bookshelfName = backStackEntry.arguments?.getString("bookshelfName") ?: ""
                            BookCaseScreen(navController = navController, bookshelfName = bookshelfName)
                        }
                        composable("notes") { ReadBookListScreen(navController) }
                        composable("eval"){ BookEvaluateScreen(navController) }
                        composable("report"){ BookReportScreen(navController) }
                        composable("quiz"){ QuizScreen (navController) }
                        composable("img"){MakeAiImgScreen(navController)}
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1500) // 3초 대기
        navController.navigate("consent") {
            popUpTo("splash") { inclusive = true } // 뒤로가기 방지
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Readers", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.main),
            contentDescription = "Readers Image",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun ConsentScreen(navController: NavHostController) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "[필수] 더욱 개인화된 추천을 위해 유튜브 구독 목록을 활용합니다.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        RadioOption(
            label = "동의합니다",
            selected = selectedOption == "agree",
            onSelect = { selectedOption = "agree" }
        )
        Spacer(modifier = Modifier.height(8.dp))
        RadioOption(
            label = "동의하지 않습니다",
            selected = selectedOption == "disagree",
            onSelect = { selectedOption = "disagree" }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("emotion") },
            enabled = selectedOption != null
        ) {
            Text("다음")
        }
    }
}

@Composable
fun RadioOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(text = label, fontSize = 16.sp)
    }
}


