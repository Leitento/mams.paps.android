package com.gomaping.gomaping.auth.validator

interface Validator<in T, out R> {
    fun validate(value: T): R
}
