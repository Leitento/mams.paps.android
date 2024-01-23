package com.gomaping.gomaping.auth.validator

object PasswordValidator : Validator<CharSequence?, PasswordValidator.Result> {
    override fun validate(value: CharSequence?): Result {
        if (value.isNullOrBlank()) {
            return Result.ERROR_EMPTY
        }
        if (value.length < MIN_PASSWORD_LENGTH) {
            return Result.ERROR_TOO_SHORT
        }
        return Result.SUCCESS
    }

    const val MIN_PASSWORD_LENGTH = 6

    enum class Result {
        SUCCESS,
        ERROR_EMPTY,
        ERROR_TOO_SHORT
    }
}
