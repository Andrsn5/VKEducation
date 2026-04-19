package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.domain.model.Category
import dev.andre.vkeducation.presentation.domain.usecase.GetFilteredCatalogUseCase

@Composable
fun FilterSheetContent(
    currentParams: GetFilteredCatalogUseCase.Params,
    onFilterCategory: (Category) -> Unit,
    onFilterWishList: (Boolean) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 32.dp),
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.filter),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.onlyWishList),
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = currentParams.onlyWishList,
                    onCheckedChange = onFilterWishList
                )
            }

            HorizontalDivider()

            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(Category.entries) { category ->
                    val isSelected = currentParams.category.contains(category)
                    FilterChip(
                        selected = isSelected,
                        onClick = { onFilterCategory(category) },
                        label = { Text(category.name) },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else null
                    )
                }
            }
            TextButton(onClick = onReset) {
                Text(stringResource(R.string.resert))
            }
        }
    }
}

@Preview()
@Composable
private fun PreviewFilterSheetContent() {
    FilterSheetContent(
        currentParams = GetFilteredCatalogUseCase.Params(
            category = setOf(Category.GAMES),
            onlyWishList = false
        ),
        onFilterCategory = {},
        onFilterWishList = {},
        modifier = Modifier,
        onReset = {}
    )
}