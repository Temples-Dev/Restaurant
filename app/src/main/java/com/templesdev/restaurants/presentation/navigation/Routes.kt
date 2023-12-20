package com.templesdev.restaurants.presentation.navigation

sealed class Routes(
    val route: String
) {
    data object Restaurant : Routes(
        route = "restaurants"
    )


    /**
     * This is a helper function to pass arguments to navigation destination.
     * For example, instead of using [Routes.Restaurant.route]/first_argument/second_argument
     * you can use like [Route.Restaurant.withArgs(first_argument, second_argument)]
     */
    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

