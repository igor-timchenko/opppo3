// Piece.kt
sealed class Piece(val color: Int, val symbol: String) {
    companion object {
        const val WHITE = 0
        const val BLACK = 1
    }
}

class King(color: Int) : Piece(color, "K")
class Queen(color: Int) : Piece(color, "Q")
class Rook(color: Int) : Piece(color, "R")
class Bishop(color: Int) : Piece(color, "B")
class Knight(color: Int) : Piece(color, "N")
class Pawn(color: Int) : Piece(color, "P")
