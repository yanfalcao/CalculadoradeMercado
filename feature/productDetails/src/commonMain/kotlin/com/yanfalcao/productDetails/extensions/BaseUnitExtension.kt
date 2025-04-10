package com.yanfalcao.productDetails.extensions

import androidx.compose.runtime.Composable
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.length
import calculadorademercado.feature.productdetails.generated.resources.mass
import calculadorademercado.feature.productdetails.generated.resources.volume
import com.yanfalcao.model.util.BaseUnits
import com.yanfalcao.model.util.Length
import com.yanfalcao.model.util.Mass
import com.yanfalcao.model.util.Volume
import com.yanfalcao.productDetails.widget.DropdownItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun BaseUnits.getDropItem(): DropdownItem {
    return when (this) {
        is Mass -> DropdownItem(
            name = stringResource(Res.string.mass),
            baseUnit = this
        )
        is Volume -> DropdownItem(
            name = stringResource(Res.string.volume),
            baseUnit = this
        )
        is Length -> DropdownItem(
            name = stringResource(Res.string.length),
            baseUnit = this
        )
        else -> DropdownItem(
            name = stringResource(Res.string.mass),
            baseUnit = this
        )
    }
}

@Composable
fun BaseUnits.entitiesToDropdownItem(): List<DropdownItem> {
    val list = mutableListOf<DropdownItem>()

    if (this !is Mass) {
        list.add(
            DropdownItem(
                name = stringResource(Res.string.mass),
                baseUnit = Mass.gram
            )
        )
    }
    if (this !is Volume) {
        list.add(
            DropdownItem(
                name = stringResource(Res.string.volume),
                baseUnit = Volume.milliliter
            )
        )
    }
    if (this !is Length) {
        list.add(
            DropdownItem(
                name = stringResource(Res.string.length),
                baseUnit = Length.centimeters
            )
        )
    }

    return list
}