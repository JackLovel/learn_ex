package org.example

class Evaluator {
    private val env: MutableMap<String, Int> = HashMap()

    fun evaluate(node: ASTNode): Int {
        if (node is NumberNode) {
            return node.value
        } else if (node is VariableNode) {
            val name = node.name
            if (!env.containsKey(name)) {
                throw RuntimeException("Undefined variable: $name")
            }
            return env[name]!!
        } else if (node is BinaryOperationNode) {
            val left = evaluate(node.left)
            val right = evaluate(node.right)
            if (node.operator == TokenType.PLUS) return left + right
        } else if (node is AssignmentNode) {
            val value = evaluate(node.expression)
            env[node.variableName] = value
            return value
        }

        throw RuntimeException("Unknown AST node: $node")
    }
}
