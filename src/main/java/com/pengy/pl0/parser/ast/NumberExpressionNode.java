package com.pengy.pl0.parser.ast;

public class NumberExpressionNode extends ExpressionNode {

    private final int value;

    public NumberExpressionNode(int value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
