package com.skarlat.core.util.dialog

sealed class DialogData(
    val positiveButtonText: String,
    val negativeButtonText: String? = null,
    val onPositiveButtonClicked: () -> Unit,
    val onNegativeButtonClicked: () -> Unit
) {
    class SingleChoice(
        positiveButtonText: String,
        negativeButtonText: String? = null,
        onPositiveButtonClicked: () -> Unit,
        onNegativeButtonClicked: () -> Unit,
        val items: List<SingleChoiceItem>,
        val onItemSelectedListener: (SingleChoiceItem) -> Unit
    ) : DialogData(
        positiveButtonText,
        negativeButtonText,
        onPositiveButtonClicked,
        onNegativeButtonClicked
    )
}