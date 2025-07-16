class ChessBoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val boardSize = 8
    private var cellSize = 0f
    private val lightColor = Color.parseColor("#F0D9B5")
    private val darkColor = Color.parseColor("#B58863")
    private val highlightColor = Color.argb(150, 100, 200, 100)
    private val selectedHighlightColor = Color.argb(150, 200, 100, 100)
    
    private val pieceDrawables = mutableMapOf<String, Drawable>()
    private var selectedPosition: Pair<Int, Int>? = null
    private var possibleMoves = listOf<Pair<Int, Int>>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cellSize = width.toFloat() / boardSize
        drawBoard(canvas)
        drawHighlights(canvas)
        drawPieces(canvas)
    }

    private fun drawBoard(canvas: Canvas) {
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                val paint = Paint().apply {
                    color = if ((row + col) % 2 == 0) lightColor else darkColor
                }
                canvas.drawRect(
                    col * cellSize,
                    row * cellSize,
                    (col + 1) * cellSize,
                    (row + 1) * cellSize,
                    paint
                )
            }
        }
    }

    private fun drawHighlights(canvas: Canvas) {
        selectedPosition?.let { (row, col) ->
            val paint = Paint().apply { color = selectedHighlightColor }
            canvas.drawRect(
                col * cellSize,
                row * cellSize,
                (col + 1) * cellSize,
                (row + 1) * cellSize,
                paint
            )
        }

        possibleMoves.forEach { (row, col) ->
            val paint = Paint().apply { color = highlightColor }
            canvas.drawRect(
                col * cellSize,
                row * cellSize,
                (col + 1) * cellSize,
                (row + 1) * cellSize,
                paint
            )
        }
    }

    private fun drawPieces(canvas: Canvas) {
        // Здесь должна быть логика отрисовки фигур
        // Пример: drawPiece(canvas, "R", 0, 0) // Белая ладья на a1
    }

    fun setOnSquareSelectedListener(listener: (Int, Int) -> Unit) {
        setOnClickListener { v ->
            val x = (v as View).x
            val y = v.y
            val row = (y / cellSize).toInt()
            val col = (x / cellSize).toInt()
            listener(row, col)
        }
    }

    fun updateSelection(position: Pair<Int, Int>?, moves: List<Pair<Int, Int>>) {
        selectedPosition = position
        possibleMoves = moves
        invalidate()
    }
}
