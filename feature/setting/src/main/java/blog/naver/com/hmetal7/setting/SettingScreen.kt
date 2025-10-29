package blog.naver.com.hmetal7.setting

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(onBackClick: () -> Unit) {
    val activity = requireNotNull(LocalActivity.current)
    val window = activity.window
    val backgroundColor = colorResource(id = R.color.colorF2F2F2)
    val insetsController = remember {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    SideEffect {
        insetsController.isAppearanceLightStatusBars = true
    }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = { Text("설정") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor // TopAppBar 배경색
                )
            )
        }
    ) { innerPadding ->
        Text(
            text = "여기는 설정 화면입니다.",
            modifier = Modifier.padding(innerPadding)
        )
    }
}