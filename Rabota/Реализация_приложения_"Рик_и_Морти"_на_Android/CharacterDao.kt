// CharacterDao.kt
@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE 
        (:name IS NULL OR name LIKE '%' || :name || '%') AND
        (:status IS NULL OR status = :status) AND
        (:species IS NULL OR species = :species) AND
        (:gender IS NULL OR gender = :gender)")
    fun pagingSource(
        @Nullable name: String?,
        @Nullable status: String?,
        @Nullable species: String?,
        @Nullable gender: String?
    ): PagingSource<Int, CharacterEntity>
}

// ViewModel
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _filterState = MutableStateFlow(CharacterFilter())
    val characters = _filterState.flatMapLatest { 
        repository.getCharacters(it) 
    }.cachedIn(viewModelScope)

    fun applyFilter(filter: CharacterFilter) {
        _filterState.value = filter
    }
}
