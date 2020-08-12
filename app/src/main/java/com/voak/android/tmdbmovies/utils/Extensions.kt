package com.voak.android.tmdbmovies.utils

import androidx.navigation.NavController

/**
 *  Extension for regulating bottom navigation fragments' back stack.
 */
fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean): Boolean {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
    return popped
}