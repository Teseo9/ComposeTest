package blog.naver.com.hmetal7.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomFixedBar(statusBarColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(statusBarColor)
    )
}