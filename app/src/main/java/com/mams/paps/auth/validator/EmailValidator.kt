package com.mams.paps.auth.validator

import android.util.Patterns

object EmailValidator : Validator<CharSequence?, Boolean> {
    override fun validate(value: CharSequence?): Boolean =
        !value.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(value).matches()
}
