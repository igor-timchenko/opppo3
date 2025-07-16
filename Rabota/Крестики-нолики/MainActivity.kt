class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var currentPlayer = 'X'
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGameBoard()
        setupResetButton()
    }

    private fun initializeGameBoard() {
        buttons = arrayOf(
            arrayOf(findViewById(R.id.btn00), findViewById(R.id.btn01), findViewById(R.id.btn02)),
            arrayOf(findViewById(R.id.btn10), findViewById(R.id.btn11), findViewById(R.id.btn12)),
            arrayOf(findViewById(R.id.btn20), findViewById(R.id.btn21), findViewById(R.id.btn22))
        )

        // Очищаем доску
        resetGame()
    }

    fun onCellClick(view: View) {
        if (!gameActive) return

        val button = view as Button
        if (button.text != "") return

        button.text = currentPlayer.toString()
        button.setTextColor(if (currentPlayer == 'X') Color.RED else Color.BLUE)

        if (checkForWin()) {
            endGame("Игрок $currentPlayer победил!")
        } else if (isBoardFull()) {
            endGame("Ничья!")
        } else {
            switchPlayer()
        }
    }

    private fun checkForWin(): Boolean {
        // Проверка строк
        for (i in 0..2) {
            if (buttons[i][0].text == currentPlayer.toString() &&
                buttons[i][1].text == currentPlayer.toString() &&
                buttons[i][2].text == currentPlayer.toString()
            ) return true
        }

        // Проверка столбцов
        for (i in 0..2) {
            if (buttons[0][i].text == currentPlayer.toString() &&
                buttons[1][i].text == currentPlayer.toString() &&
                buttons[2][i].text == currentPlayer.toString()
            ) return true
        }

        // Проверка диагоналей
        if (buttons[0][0].text == currentPlayer.toString() &&
            buttons[1][1].text == currentPlayer.toString() &&
            buttons[2][2].text == currentPlayer.toString()
        ) return true

        if (buttons[0][2].text == currentPlayer.toString() &&
            buttons[1][1].text == currentPlayer.toString() &&
            buttons[2][0].text == currentPlayer.toString()
        ) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in buttons) {
            for (button in row) {
                if (button.text == "") return false
            }
        }
        return true
    }

    private fun endGame(message: String) {
        findViewById<TextView>(R.id.tvStatus).text = message
        gameActive = false
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        findViewById<TextView>(R.id.tvStatus).text = "Ход: $currentPlayer"
    }

    private fun setupResetButton() {
        findViewById<Button>(R.id.btnReset).setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        for (row in buttons) {
            for (button in row) {
                button.text = ""
                button.setTextColor(Color.BLACK)
            }
        }
        currentPlayer = 'X'
        gameActive = true
        findViewById<TextView>(R.id.tvStatus).text = "Ход: $currentPlayer"
    }
}
