package com.nexora.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF0F1115)
private val Surface = Color(0xFF171A20)
private val Chip = Color(0xFF1C1F26)
private val Accent = Color(0xFFE5484D)
private val TextPrimary = Color(0xFFF2F2F2)
private val TextSecondary = Color(0xFF8B8F99)

data class Title(
    val name: String,
    val year: Int,
    val type: String,
    val rating: Double,
    val posterUrl: String? = null
)

private val categories = listOf("Trending", "Movies", "Series", "Anime")

private val sample = listOf(
    Title("The Blue Road", 2026, "Series", 8.1),
    Title("The Lord of the Rings", 2022, "Series", 7.6),
    Title("MobLand", 2025, "Series", 7.9),
    Title("12 12 12", 2026, "Movie", 7.2),
    Title("VisionQuest", 2026, "Series", 7.0),
    Title("Nocturne", 2026, "Movie", 6.8),
)

@Composable
fun DiscoverScreen(
    titles: List<Title> = sample,
    onTitleClick: (Title) -> Unit = {},
    onSearch: () -> Unit = {},
    onCast: () -> Unit = {},
) {
    var selectedCategory by remember { mutableStateOf(0) }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Bg,
        bottomBar = { BottomBar(selectedTab) { selectedTab = it } }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item(span = { GridItemSpan(2) }) { TopBar(onCast, onSearch) }
            item(span = { GridItemSpan(2) }) {
                CategoryChips(selectedCategory) { selectedCategory = it }
            }
            item(span = { GridItemSpan(2) }) { FeaturedCard() }
            item(span = { GridItemSpan(2) }) {
                Text(
                    "Trending now",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
            items(titles) { PosterCard(it) { onTitleClick(it) } }
        }
    }
}

@Composable
private fun TopBar(onCast: () -> Unit, onSearch: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Nexora",
            color = TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onCast) {
            Icon(Icons.Outlined.Cast, "Cast", tint = TextPrimary)
        }
        IconButton(onClick = onSearch) {
            Icon(Icons.Outlined.Search, "Search", tint = TextPrimary)
        }
    }
}

@Composable
private fun CategoryChips(selected: Int, onSelect: (Int) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories.size) { i ->
            val active = i == selected
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(if (active) Accent else Chip)
                    .clickable { onSelect(i) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    categories[i],
                    color = if (active) Color.White else Color(0xFFC9CCD3),
                    fontSize = 13.sp
                )
            }
        }
    }
}
