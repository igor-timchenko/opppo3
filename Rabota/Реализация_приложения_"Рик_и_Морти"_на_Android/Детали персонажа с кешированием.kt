@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    fun loadCharacter(id: Int) = viewModelScope.launch {
        repository.getCharacter(id)
            .catch { _character.value = repository.getCachedCharacter(id) }
            .collect { _character.value = it }
    }
}
