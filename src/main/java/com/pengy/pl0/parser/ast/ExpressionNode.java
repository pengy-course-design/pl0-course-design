package com.pengy.pl0.parser.ast;

public abstract class ExpressionNode extends AstNode {

    protected ExpressionNode(int line, int column) {
        super(line, column);
    }
}
