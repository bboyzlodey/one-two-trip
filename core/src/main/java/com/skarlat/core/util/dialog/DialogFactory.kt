package com.skarlat.core.util.dialog

import android.app.Dialog
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogFactory {

    fun showDialog(context: Context, data: DialogData) {
        val dialogBuilder = MaterialAlertDialogBuilder(context)
            .setPositiveButton(
                data.positiveButtonText
            ) { dialog, which ->
                data.onPositiveButtonClicked.invoke()
            }
        data.negativeButtonText?.let { negativeText ->
            dialogBuilder.setNegativeButton(negativeText) { dialog, which ->
                data.onNegativeButtonClicked.invoke()
            }
        }
        when (data) {
            is DialogData.SingleChoice -> buildDialog(dialogBuilder, data)
            else -> throw IllegalStateException("Unknown dialog data")
        }.show()
    }

    private fun buildDialog(
        builder: MaterialAlertDialogBuilder,
        data: DialogData.SingleChoice
    ): Dialog {
        return builder.setSingleChoiceItems(
            data.items.map { it.name }.toTypedArray(),
            0
        ) { _, which -> data.onItemSelectedListener.invoke(data.items[which]) }.create()
    }

}