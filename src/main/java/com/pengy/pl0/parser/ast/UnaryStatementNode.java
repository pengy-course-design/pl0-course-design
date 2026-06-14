package com.pengy.pl0.parser.ast;

public class UnaryStatementNode extends StatementNode {

    private final String variableName;
    private final String operator;

    public UnaryStatementNode(String variableName, String operator, int line, int column) {
        super(line, column);
        this.variableName = variableName;
        this.operator = operator;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getOperator() {
        return operator;
    }
}
