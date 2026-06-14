package com.pengy.pl0.parser.ast;

public abstract class StatementNode extends AstNode {

    protected StatementNode(int line, int column) {
        super(line, column);
    }
}
