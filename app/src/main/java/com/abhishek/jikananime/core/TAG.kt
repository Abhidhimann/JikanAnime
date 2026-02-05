package com.abhishek.jikananime.core

fun Any.classTag(): String = this::class.java.simpleName

fun Any.tempTag(): String = "tempTag"