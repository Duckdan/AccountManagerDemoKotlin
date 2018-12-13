@file:JvmName("DefineAccount") //自定义类名
@file:JvmMultifileClass //当有多个相同自定义类名时，使用这个注解可以合并多个类

package com.study.yang.accoutmanagerdemokotlin.data

data class DefineAccount(var email: String, var pwd: String)