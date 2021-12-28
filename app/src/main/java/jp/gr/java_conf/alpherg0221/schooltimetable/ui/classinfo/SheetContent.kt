package jp.gr.java_conf.alpherg0221.schooltimetable.ui.classinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.gr.java_conf.alpherg0221.compose.material.BottomSheetLayout
import jp.gr.java_conf.alpherg0221.schooltimetable.R
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.theme.ColorList
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.theme.ColorValueList
import jp.gr.java_conf.alpherg0221.schooltimetable.ui.theme.OnColorList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPicker(
    selected: Long,
    onSelected: (String) -> Unit,
) {
    BottomSheetLayout(
        modifier = Modifier.padding(9.dp),
        title = stringResource(id = R.string.select_color),
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(6),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            itemsIndexed(ColorList) { index, color ->
                ColorButton(
                    color = color,
                    contentColor = OnColorList[index],
                    isSelected = color == Color(selected),
                    onSelected = onSelected,
                )
            }
        }
    }
}

@Composable
fun ColorButton(
    color: Color,
    contentColor: Color,
    isSelected: Boolean = false,
    onSelected: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .clickable { onSelected(ColorValueList[ColorList.indexOf(color)].toString()) }
            .requiredSizeIn(36.dp, 36.dp, 36.dp, 36.dp),
        shape = CircleShape,
        color = color,
        contentColor = contentColor,
        border = BorderStroke(
            width = 1.dp,
            color = contentColor.copy(alpha = .5f),
        ),
    ) {
        if (isSelected) {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
            )
        }
    }
}