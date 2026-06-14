package com.pengy.pl0.parser.ast;

public class AssignmentStatementNode extends StatementNode {

    private final String variableName;
    private final String operator;
    private final ExpressionNode expression;

    public AssignmentStatementNode(String variableName, String operator, ExpressionNode expression, int line, int column) {
        super(line, column);
        this.variableName = variableName;
        this.operator = operator;
        this.expression = expression;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
