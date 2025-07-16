class MainActivity : AppCompatActivity() {

    private lateinit var chessBoardView: ChessBoardView
    private lateinit var tvStatus: TextView
    private val chessGame = ChessGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chessBoardView = findViewById(R.id.chessBoard)
        tvStatus = findViewById(R.id.tvStatus)

        chessBoardView.setOnSquareSelectedListener { row, col ->
            val moves = chessGame.handleSquareClick(row, col)
            chessBoardView.updateSelection(
                chessGame.getSelectedPosition(), 
                moves
            )
            updateStatus()
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            chessGame.resetGame()
            chessBoardView.updateSelection(null, emptyList())
            updateStatus()
        }

        updateStatus()
    }

    private fun updateStatus() {
        val player = if (chessGame.getCurrentPlayer() == Piece.WHITE) "Белые" else "Черные"
        tvStatus.text = "Ход: $player"
    }
}
