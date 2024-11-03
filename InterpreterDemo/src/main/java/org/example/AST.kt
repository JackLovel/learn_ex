package org.example

abstract class ASTNode

internal class NumberNode(@JvmField var value: Int) : ASTNode()

internal class VariableNode(@JvmField var name: String) : ASTNode()

internal class BinaryOperationNode(@JvmField var left: ASTNode, @JvmField var right: ASTNode, @JvmField var operator: TokenType) : ASTNode()

internal class AssignmentNode(@JvmField var variableName: String, @JvmField var expression: ASTNode) : ASTNode()

