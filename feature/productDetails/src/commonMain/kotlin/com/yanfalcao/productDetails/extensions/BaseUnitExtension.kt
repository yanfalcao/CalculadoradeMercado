package com.yanfalcao.productDetails.extensions

import androidx.compose.runtime.Composable
import calculadorademercado.feature.productdetails.generated.resources.Res
import calculadorademercado.feature.productdetails.generated.resources.lenght_kilometer
import calculadorademercado.feature.productdetails.generated.resources.length
import calculadorademercado.feature.productdetails.generated.resources.length_centimeter
import calculadorademercado.feature.productdetails.generated.resources.length_foot
import calculadorademercado.feature.productdetails.generated.resources.length_inch
import calculadorademercado.feature.productdetails.generated.resources.length_meter
import calculadorademercado.feature.productdetails.generated.resources.length_mile
import calculadorademercado.feature.productdetails.generated.resources.mass
import calculadorademercado.feature.productdetails.generated.resources.mass_gram
import calculadorademercado.feature.productdetails.generated.resources.mass_kilogram
import calculadorademercado.feature.productdetails.generated.resources.mass_milligram
import calculadorademercado.feature.productdetails.generated.resources.mass_ounce
import calculadorademercado.feature.productdetails.generated.resources.mass_pound
import calculadorademercado.feature.productdetails.generated.resources.volume
import calculadorademercado.feature.productdetails.generated.resources.volume_fluid_ounce
import calculadorademercado.feature.productdetails.generated.resources.volume_gallon
import calculadorademercado.feature.productdetails.generated.resources.volume_liter
import calculadorademercado.feature.productdetails.generated.resources.volume_milliliter
import calculadorademercado.feature.productdetails.generated.resources.volume_pint
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

@Composable
fun BaseUnits.getBaseUnitDropItem(): DropdownItem {
    return when(this) {
        is Mass -> massUnitsToDropdwnItem().filter { it.baseUnit == this }.first()
        is Volume -> volumeUnitsToDropdwnItem().filter { it.baseUnit == this }.first()
        is Length -> lengthUnitsToDropdwnItem().filter { it.baseUnit == this }.first()
        else -> massUnitsToDropdwnItem().filter { it.baseUnit == this }.first()
    }
}

@Composable
fun BaseUnits.baseUnitsToDropdownItem(): List<DropdownItem> {
    return when(this) {
        is Mass -> massUnitsToDropdwnItem()
        is Volume -> volumeUnitsToDropdwnItem()
        is Length -> lengthUnitsToDropdwnItem()
        else -> massUnitsToDropdwnItem()
    }
}

@Composable
private fun lengthUnitsToDropdwnItem(): List<DropdownItem> {
    return listOf(
        DropdownItem(stringResource(Res.string.length_centimeter), Length.centimeters),
        DropdownItem(stringResource(Res.string.length_meter), Length.meters),
        DropdownItem(stringResource(Res.string.length_foot), Length.feet),
        DropdownItem(stringResource(Res.string.length_centimeter), Length.centimeters),
        DropdownItem(stringResource(Res.string.length_inch), Length.inches),
        DropdownItem(stringResource(Res.string.length_mile), Length.miles),
        DropdownItem(stringResource(Res.string.lenght_kilometer), Length.kilometers),
    )
}

@Composable
private fun massUnitsToDropdwnItem(): List<DropdownItem> {
    return listOf(
        DropdownItem(stringResource(Res.string.mass_gram), Mass.gram),
        DropdownItem(stringResource(Res.string.mass_milligram), Mass.milligram),
        DropdownItem(stringResource(Res.string.mass_kilogram), Mass.kilogram),
        DropdownItem(stringResource(Res.string.mass_ounce), Mass.ounce),
        DropdownItem(stringResource(Res.string.mass_pound), Mass.pound),
    )
}

@Composable
private fun volumeUnitsToDropdwnItem(): List<DropdownItem> {
    return listOf(
        DropdownItem(stringResource(Res.string.volume_milliliter), Volume.milliliter),
        DropdownItem(stringResource(Res.string.volume_liter), Volume.liter),
        DropdownItem(stringResource(Res.string.volume_gallon), Volume.gallon),
        DropdownItem(stringResource(Res.string.volume_fluid_ounce), Volume.fluidOunce),
        DropdownItem(stringResource(Res.string.volume_pint), Volume.pint),
    )
}