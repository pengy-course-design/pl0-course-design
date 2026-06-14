package com.pengy.pl0.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompoundStatementNode extends StatementNode {

    private final List<StatementNode> statements = new ArrayList<>();

    public CompoundStatementNode(int line, int column) {
        super(line, column);
    }

    public void addStatement(StatementNode statement) {
        statements.add(statement);
    }

    public List<StatementNode> getStatements() {
        return Collections.unmodifiableList(statements);
    }
}