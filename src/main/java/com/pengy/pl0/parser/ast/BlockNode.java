package com.pengy.pl0.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockNode extends AstNode {

    private final List<DeclarationNode> declarations = new ArrayList<>();
    private StatementNode statement;

    public BlockNode(int line, int column) {
        super(line, column);
    }

    public void addDeclaration(DeclarationNode declaration) {
        declarations.add(declaration);
    }

    public List<DeclarationNode> getDeclarations() {
        return Collections.unmodifiableList(declarations);
    }

    public StatementNode getStatement() {
        return statement;
    }

    public void setStatement(StatementNode statement) {
        this.statement = statement;
    }
}