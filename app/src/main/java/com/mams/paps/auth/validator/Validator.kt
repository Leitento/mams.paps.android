package com.mams.paps.auth.validator

interface Validator<in T, out R> {
    fun validate(value: T): R
}
