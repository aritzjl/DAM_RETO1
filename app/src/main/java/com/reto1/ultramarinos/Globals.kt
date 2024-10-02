package com.reto1.ultramarinos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

var total_likes by mutableIntStateOf(0)
var is_single_column by mutableStateOf(false)
var toolbarTitle by mutableStateOf("Inicio")
var lightmode by mutableStateOf(true)
