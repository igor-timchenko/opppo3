// CharacterRepositoryImpl.kt
@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val api: CharacterApi,
    private val dao: CharacterDao,
    private val networkMonitor: NetworkMonitor
) : CharacterRepository {

    override fun getCharacters(filter: CharacterFilter): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(api, dao, filter, networkMonitor),
            pagingSourceFactory = { dao.pagingSource(filter) }
        ).flow.map { it.toDomain() }
    }
}

// CharacterRemoteMediator.kt
class CharacterRemoteMediator(
    private val api: CharacterApi,
    private val dao: CharacterDao,
    private val filter: CharacterFilter,
    private val networkMonitor: NetworkMonitor
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, CharacterEntity>): MediatorResult {
        return try {
            if (!networkMonitor.isOnline()) return MediatorResult.Success(true)
            
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> nextPage ?: return MediatorResult.Success(true)
            }

            val response = api.getCharacters(
                page = page,
                name = filter.name,
                status = filter.status,
                species = filter.species,
                gender = filter.gender
            )

            dao.insertAll(response.results.map { it.toEntity() })
            nextPage = response.info.next?.extractPageNumber()

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
