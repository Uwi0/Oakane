package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton

@Composable
internal fun SelectColorView(defaultColor: Int, onSelectedColor: (String) -> Unit) {
    val controller = rememberColorPickerController()
    var hexCode by remember { mutableStateOf("") }
    var textColor by remember { mutableStateOf(Color.Transparent) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            controller = controller,
            drawOnPosSelected = {
                drawColorIndicator(
                    controller.selectedPoint.value,
                    controller.selectedColor.value
                )
            },
            onColorChanged = { colorEnvelope ->
                hexCode = colorEnvelope.hexCode
                textColor = colorEnvelope.color
            },
            initialColor = Color(defaultColor)
        )

        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(horizontal = 16.dp),
            controller = controller
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(horizontal = 16.dp),
            controller = controller
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "#$hexCode",
                color = textColor,
                style = MaterialTheme.typography.titleMedium
            )

            AlphaTile(
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small),
                controller = controller
            )
        }

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = { Text(text = "Select Color") },
            onClick = {
                val hex = "0x$hexCode"
                onSelectedColor.invoke(hex)
            }
        )
    }
}