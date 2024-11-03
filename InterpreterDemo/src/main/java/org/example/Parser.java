package org.example;

import java.util.List;

class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    ASTNode parse() {
        Token currentToken = tokens.get(pos);
        if (currentToken.getType() == TokenType.INEDNTIFIER &&
                peek().getType() == TokenType.ASSIGN
        ) {
            return parseAssignment();
        }

        return parseExpression();
    }

    private ASTNode parseAssignment() {
        String variableName = tokens.get(pos++).getValue();
        pos++;
        ASTNode expression = parseExpression();
        return new AssignmentNode(variableName, expression);
    }

    private ASTNode parseExpression() {
        ASTNode left = parsePrimary();
        if (pos < tokens.size() && tokens.get(pos).getType() == TokenType.PLUS) {
            pos++; // skip '+'
            ASTNode right = parsePrimary();
            return new BinaryOperationNode(left, right, TokenType.PLUS);
        }
        return left;
    }


    private ASTNode parsePrimary() {
        Token token = tokens.get(pos++);
        if (token.getType() == TokenType.NUMBER) {
            return new NumberNode(Integer.parseInt(token.getValue()));
        } else if (token.getType() == TokenType.INEDNTIFIER) {
            return new VariableNode(token.getValue());
        }

        throw new RuntimeException("unknown token " + token);
    }

    private Token peek() {
        return tokens.get(pos + 1);
    }
}
