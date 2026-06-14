package com.pengy.pl0.parser.ast;

public abstract class DeclarationNode extends AstNode {

    private final String name;

    protected DeclarationNode(String name, int line, int column) {
        super(line, column);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
