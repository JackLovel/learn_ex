package org.example

internal enum class TokenType {
    NUMBER, INEDNTIFIER, PLUS, ASSIGN, EOF
}

internal class Token(var type: TokenType, var value: String) {
    override fun toString(): String {
        return "$type $value"
    }
}

internal class Lexer(private val input: String) {
    private var pos = 0
    internal fun tokenize(): List<Token> {
        val tokens = ArrayList<Token>()
        while (pos < input.length) {
            val current: Char = input[pos]
            if (Character.isDigit(current)) {
                tokens.add(Token(TokenType.NUMBER, readNumber()))
            } else if (Character.isLetter(current)) {
                tokens.add(Token(TokenType.INEDNTIFIER, readIdentifer()))
            } else if (current == '+') {
                tokens.add(Token(TokenType.PLUS, "+"))
                pos++
            } else if (
                current == '='
            ) {
                tokens.add(Token(TokenType.ASSIGN, "="))
                pos++
            } else {
                pos++
            }
        }
        tokens.add(Token(TokenType.EOF, ""))
        return tokens
    }

    internal fun readNumber(): String {
        val sb = StringBuilder()
        while (pos < input.length && Character.isDigit(input[pos])) {
            sb.append(input[pos])
            pos++
        }
        return sb.toString()
    }

    internal fun readIdentifer(): String {
        val sb = StringBuilder()
        while (pos < input.length && Character.isLetter(input[pos])) {
            sb.append(input[pos])
            pos++
        }
        return sb.toString()
    }
}