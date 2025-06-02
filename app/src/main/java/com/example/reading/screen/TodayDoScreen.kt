package com.example.reading

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.colorResource
import com.example.reading.R // 폰트 리소스를 위해 필요

@Composable
fun TodayDoScreen(navController: NavHostController, viewModel: UserRecommendationProfileViewModel) {
    var inputText by remember { mutableStateOf("") }

    // 저작권 문제 없는 어린이용 폰트 (예: 어비 서현체)
    val kidFont = FontFamily(Font(R.font.uhbee_puding))

    val topPadding = 100.dp // 기본값을 40.dp로 설정, 원하는 값으로 조절 가능

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(topPadding)) // 여기서 높이 조절
        Text(
            text = "오늘은 어떤 일이 있었어?🤔\n오늘 일을 바탕으로\n책📚을 추천해줄게!😉",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 52.sp,
            style = TextStyle(fontFamily = kidFont)
        )

        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .width(280.dp)
                .height(180.dp),
            placeholder = { Text("여기에 작성해 주세요") },
            label = { Text("오늘의 일☀️", color = colorResource(id = R.color.light_green)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.DarkGray,
                cursorColor = Color.DarkGray
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                viewModel.setUserInterest(inputText)
                Log.d("TodayDoScreen", "UserInterest 저장: $inputText")
                navController.navigate("category")
            },
            enabled = inputText.isNotBlank(),
            modifier = Modifier
                .width(130.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9EDC9A), // 연두색
                contentColor = Color.White // 텍스트 컬러
            )
        ) {
            Text("다음")
        }
    }

}
