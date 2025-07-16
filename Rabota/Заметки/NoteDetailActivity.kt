import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private var currentNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        // Инициализация ViewModel
        val database = NoteDatabase.getDatabase(application)
        val repository = NoteRepository(database.noteDao())
        viewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(repository)
        )[NoteViewModel::class.java]

        // Получение заметки для редактирования
        val noteId = intent.getIntExtra("note_id", -1)
        if (noteId != -1) {
            GlobalScope.launch {
                currentNote = database.noteDao().getNoteById(noteId)
                runOnUiThread { displayNote() }
            }
        }

        // Сохранение при выходе
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun displayNote() {
        currentNote?.let {
            findViewById<EditText>(R.id.note_title_edit).setText(it.title)
            findViewById<EditText>(R.id.note_content_edit).setText(it.content)
        }
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        saveNote()
        return super.onSupportNavigateUp()
    }

    private fun saveNote() {
        val title = findViewById<EditText>(R.id.note_title_edit).text.toString()
        val content = findViewById<EditText>(R.id.note_content_edit).text.toString()

        if (title.isBlank() && content.isBlank()) return

        if (currentNote == null) {
            // Новая заметка
            val newNote = Note(
                title = title,
                content = content,
                date = Date()
            )
            viewModel.insert(newNote)
        } else {
            // Обновление существующей
            currentNote?.let {
                val updatedNote = it.copy(
                    title = title,
                    content = content,
                    date = Date()
                )
                viewModel.update(updatedNote)
            }
        }
    }
}
