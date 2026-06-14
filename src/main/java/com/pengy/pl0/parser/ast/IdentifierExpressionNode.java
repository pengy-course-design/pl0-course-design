package com.pengy.pl0.parser.ast;

public class IdentifierExpressionNode extends ExpressionNode {

    private final String name;

    public IdentifierExpressionNode(String name, int line, int column) {
        super(line, column);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
