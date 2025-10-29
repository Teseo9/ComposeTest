package blog.naver.com.hmetal7.home

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import blog.naver.com.hmetal7.home.calendar.CalendarScreen
import blog.naver.com.hmetal7.home.component.BottomFixedBar
import blog.naver.com.hmetal7.home.component.BottomShadow
import blog.naver.com.hmetal7.home.component.TitleBar
import blog.naver.com.hmetal7.home.component.TopShadow

@Composable
fun HomeScreen(
    onSettingClick: () -> Unit
) {
    val activity = requireNotNull(LocalActivity.current)
    val window = activity.window
    val insetsController = remember {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    SideEffect {
        insetsController.isAppearanceLightStatusBars = false
    }

    val statusBarColor = remember {
        val colorInt = ContextCompat.getColor(activity, R.color.app_main)
        Color(colorInt)
    }

    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val titleBarHeight = 46.dp
    val topPadding = statusBarHeight + titleBarHeight

    val bottomBarHeight = 56.dp
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val bottomPadding = bottomBarHeight + bottomInset

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.zIndex(1f)) {
            TitleBar(
                statusBarColor = statusBarColor,
                height = titleBarHeight,
                onSettingClick = onSettingClick
            )
            TopShadow()
        }

        Scaffold(
            modifier = Modifier
                .padding(top = topPadding)
                .zIndex(0f),
            containerColor = Color(0xFFF5F5F5),
            contentWindowInsets = WindowInsets(0)
        ) { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(bottom = bottomPadding)) {
                CalendarScreen()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(32.dp + bottomInset)
                .padding(bottom = bottomInset)
                .zIndex(1f)
        ) {
            BottomShadow()
            BottomFixedBar(statusBarColor = statusBarColor)
        }
    }
}