package com.mctable.easybiz.features.my_business.presentation.ui.components

import androidx.compose.runtime.Composable
import com.mctable.easybiz.core.ds.components.molecules.BusinessInfoCardMolecule
import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity

@Composable
fun MyBusinessCard(
    business: MyBusinessEntity,
    onClick: () -> Unit,
    onEditClick: () -> Unit
) {
    BusinessInfoCardMolecule(
        title = business.name,
        subtitle = business.category,
        logoUrl = business.logoUrl,
        onClick = onClick,
        onEditClick = onEditClick
    )
}
