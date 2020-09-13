package com.example.habitsca.view.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class ExposedDropdownMenu constructor(
    @NonNull context: Context,
    @Nullable attributeSet: AttributeSet
): MaterialAutoCompleteTextView(context, attributeSet) {

    override fun getFreezesText(): Boolean {
        return false
    }
}