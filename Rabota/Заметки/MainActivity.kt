import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация базы данных
        val database = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "notes-db"
        ).build()

        val repository = NoteRepository(database.noteDao())
        viewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(repository)
        )[NoteViewModel::class.java]

        // Настройка RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        adapter = NoteAdapter { note ->
            openNoteDetail(note)
        }
        recyclerView.adapter = adapter

        // Подписка на изменения данных
        viewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }

        // Кнопка добавления новой заметки
        findViewById<FloatingActionButton>(R.id.add_note_fab).setOnClickListener {
            openNoteDetail(null)
        }
    }

    private fun openNoteDetail(note: Note?) {
        val intent = Intent(this, NoteDetailActivity::class.java).apply {
            note?.let {
                putExtra("note_id", it.id)
            }
        }
        startActivity(intent)
    }
}
