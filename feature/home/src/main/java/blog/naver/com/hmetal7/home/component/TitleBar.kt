package blog.naver.com.hmetal7.home.component

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import blog.naver.com.hmetal7.home.R

@Composable
fun TitleBar(statusBarColor: Color, height: Dp, onSettingClick: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(statusBarColor)
        )

        val fontSize = with(LocalDensity.current) { 16.dp.toSp() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(statusBarColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "로고",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(42.dp)
            )

            Text(
                text = "간지달력",
                color = Color.White,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            SettingIcon(onClick = onSettingClick)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingIcon(onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(
            id = if (isPressed) R.drawable.ic_color_lens_selected else R.drawable.ic_color_lens
        ),
        contentDescription = "설정",
        modifier = Modifier
            .padding(end = 12.dp)
            .size(32.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        isPressed = false
                        onClick()
                        true
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        true
                    }

                    else -> false
                }
            }
    )
}