package com.pengy.pl0.parser.ast;

public class ConstDeclarationNode extends DeclarationNode {

    private final int value;

    public ConstDeclarationNode(String name, int value, int line, int column) {
        super(name, line, column);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
