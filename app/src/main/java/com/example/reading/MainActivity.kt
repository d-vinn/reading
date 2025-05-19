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
                    NavHost(navController, startDestination = "start") {
                        composable("start") { StartScreen(navController) }
                        composable("signup") { SignupScreen(navController) }
                        composable("login") { LoginScreen(navController) }
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



