package org.example

import java.nio.file.Files
import java.nio.file.Paths

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
        if (args.size != 1) {
            println("unage: java min ruby <file.rb>")
            return;
        }
        **/
        try {
            val filePath = "C:\\Users\\wcz\\IdeaProjects\\InterpreterDemo\\src\\main\\java\\org\\example\\ruby\\sum.rb";
            val code = String(Files.readAllBytes(Paths.get(filePath)))
            //val code = "a = 1 + 2"
            // 1.词法分析
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            // 2.语法解析
            val parser = Parser(tokens)
            val ast = parser.parse()
            // 3.评估AST
            val evaluator = Evaluator()
            val result = evaluator.evaluate(ast)

            println("Result: $result")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}