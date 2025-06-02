package com.example.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reading.R

@Composable
fun StartScreen(navController: NavHostController) {
    val startFont = FontFamily(Font(R.font.start))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Readers", fontSize = 52.sp, style = TextStyle(fontFamily = startFont))
        Spacer(modifier = Modifier.height(45.dp))
        Image(
            painter = painterResource(id = R.drawable.main),
            contentDescription = "Readers Image",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier
                .width(130.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_green), // 연두색
                contentColor = Color.White // 텍스트 컬러
            )
        ) {
            Text("회원가입")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .width(130.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_green), // 연두색
                contentColor = Color.White // 텍스트 컬러
            )
        ) {
            Text("로그인")
        }
    }
}
