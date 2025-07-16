// CharactersScreen.kt
@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = characters.loadState.refresh is LoadState.Loading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { characters.refresh() }
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(characters) { character ->
                character?.let { CharacterItem(it) }
            }

            when (val state = characters.loadState.append) {
                is LoadState.Error -> item { ErrorItem(state.error) }
                is LoadState.Loading -> item { LoadingItem() }
            }
        }

        if (characters.loadState.refresh is LoadState.Error) {
            ErrorView((characters.loadState.refresh as LoadState.Error).error)
        }
    }
}
