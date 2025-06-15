package parinexus.tmdb.movies.presentation.bottomBar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import parinexus.tmdb.movies.R

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    Log.i("BottomBar", "BottomBar: ${items.size}")
    NavigationBar(
        containerColor = colorResource(id = R.color.black3),
        contentColor = Color.Gray
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                        )
                    }
                },
                label = { Text(item.title) },
                alwaysShowLabel = true,
                selected = index == selectedItem,
                onClick = {
                    onItemClick(index)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = colorResource(id = R.color.blue)
                ),
            )
        }
    }
}
