package com.kakapo.oakane.presentation.feature.addGoal.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.R
import com.kakapo.oakane.common.utils.saveImageUri
import com.kakapo.oakane.common.utils.showToast
import com.kakapo.oakane.presentation.designSystem.component.image.CustomImagePicker

@Composable
internal fun ImageGoalPicker(onSelectedImage: (String) -> Unit) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            uri?.let {
                context.saveImageUri(it).fold(
                    onSuccess = { fileName -> onSelectedImage.invoke(fileName) },
                    onFailure = { throwable -> context.showToast(throwable.message ?: "Error") }
                )
            }
        }
    )

    CustomImagePicker(
        modifier = Modifier
            .size(120.dp),
        defaultImg = R.drawable.img_add_transaction_sample_mona,
        selectedImageUri = selectedImageUri,
        onSelectedImage = { singlePhotoPickerLauncher.launch(PickVisualMediaRequest(mediaType)) }
    )
}