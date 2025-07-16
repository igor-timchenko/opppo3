import android.app.Application

class NotesApp : Application() {
    companion object {
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "notes-db"
        ).fallbackToDestructiveMigration().build()
    }
}
