package com.juniar.googledirection

interface MapsView{
    fun onGetDirection(error:Boolean,direction:DirectionResponse?,t:Throwable?)
}