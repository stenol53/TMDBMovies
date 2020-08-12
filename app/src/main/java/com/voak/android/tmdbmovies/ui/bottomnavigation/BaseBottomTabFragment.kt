package com.voak.android.tmdbmovies.ui.bottomnavigation

import androidx.activity.addCallback
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.voak.android.tmdbmovies.utils.popBackStackAllInstances
import dagger.android.support.DaggerFragment

open class BaseBottomTabFragment : DaggerFragment() {
    var isNavigated = false

    fun navigateWithAction(action: NavDirections) {
        isNavigated = true
        findNavController().navigate(action)
    }

    fun navigate(resId: Int) {
        isNavigated = true
        findNavController().navigate(resId)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        if (!isNavigated) {
//            requireActivity().onBackPressedDispatcher.addCallback(this) {
//                val navController = findNavController()
//                if (navController.currentBackStackEntry?.destination?.id != null) {
//                    findNavController().popBackStackAllInstances(
//                        navController.currentBackStackEntry?.destination?.id!!,
//                        true
//                    )
//                } else {
//                    navController.popBackStack()
//                }
//            }
//        }
//    }
}