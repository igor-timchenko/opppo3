class ChessGame {

    private var currentPlayer = Piece.WHITE
    private var selectedPosition: Pair<Int, Int>? = null
    private val board = Array(8) { arrayOfNulls<Piece?>(8) }

    init {
        setupInitialBoard()
    }

    private fun setupInitialBoard() {
        // Расстановка пешек
        for (i in 0..7) {
            board[1][i] = Pawn(Piece.BLACK)
            board[6][i] = Pawn(Piece.WHITE)
        }

        // Ладьи
        board[0][0] = Rook(Piece.BLACK)
        board[0][7] = Rook(Piece.BLACK)
        board[7][0] = Rook(Piece.WHITE)
        board[7][7] = Rook(Piece.WHITE)

        // Кони
        board[0][1] = Knight(Piece.BLACK)
        board[0][6] = Knight(Piece.BLACK)
        board[7][1] = Knight(Piece.WHITE)
        board[7][6] = Knight(Piece.WHITE)

        // Слоны
        board[0][2] = Bishop(Piece.BLACK)
        board[0][5] = Bishop(Piece.BLACK)
        board[7][2] = Bishop(Piece.WHITE)
        board[7][5] = Bishop(Piece.WHITE)

        // Ферзи
        board[0][3] = Queen(Piece.BLACK)
        board[7][3] = Queen(Piece.WHITE)

        // Короли
        board[0][4] = King(Piece.BLACK)
        board[7][4] = King(Piece.WHITE)
    }

    fun handleSquareClick(row: Int, col: Int): List<Pair<Int, Int>> {
        val piece = board[row][col]
        
        // Если выбрана своя фигура
        if (piece?.color == currentPlayer) {
            selectedPosition = Pair(row, col)
            return calculatePossibleMoves(row, col)
        }

        // Если выбрана пустая клетка или фигура противника
        selectedPosition?.let { (selectedRow, selectedCol) ->
            if (Pair(row, col) in calculatePossibleMoves(selectedRow, selectedCol)) {
                movePiece(selectedRow, selectedCol, row, col)
                selectedPosition = null
                currentPlayer = 1 - currentPlayer // Смена игрока
                return emptyList()
            }
        }
        
        selectedPosition = null
        return emptyList()
    }

    private fun calculatePossibleMoves(row: Int, col: Int): List<Pair<Int, Int>> {
        val piece = board[row][col] ?: return emptyList()
        val moves = mutableListOf<Pair<Int, Int>>()

        // Упрощенная логика расчета ходов
        when (piece) {
            is Pawn -> {
                val direction = if (piece.color == Piece.WHITE) -1 else 1
                if (board[row + direction][col] == null) {
                    moves.add(Pair(row + direction, col))
                }
            }
            is Rook -> {
                // Логика для ладьи
            }
            // Аналогично для других фигур
        }

        return moves.filter { (r, c) ->
            r in 0..7 && c in 0..7 && board[r][c]?.color != piece.color
        }
    }

    private fun movePiece(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int) {
        board[toRow][toCol] = board[fromRow][fromCol]
        board[fromRow][fromCol] = null
        
        // Превращение пешки
        if (board[toRow][toCol] is Pawn && (toRow == 0 || toRow == 7)) {
            board[toRow][toCol] = Queen(currentPlayer)
        }
    }

    fun getBoardState(): Array<Array<Piece?>> = board
    fun getCurrentPlayer(): Int = currentPlayer
}
