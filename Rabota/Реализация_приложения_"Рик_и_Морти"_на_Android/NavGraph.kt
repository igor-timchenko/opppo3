// NavGraph.kt
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController, startDestination = "characters") {
        composable("characters") {
            CharactersScreen(
                onCharacterClick = { id -> navController.navigate("details/$id") },
                onFilterClick = { navController.navigate("filters") }
            )
        }
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            id?.let { CharacterDetailScreen(characterId = it) }
        }
        composable("filters") {
            FilterScreen(onApply = { navController.popBackStack() })
        }
    }
}
