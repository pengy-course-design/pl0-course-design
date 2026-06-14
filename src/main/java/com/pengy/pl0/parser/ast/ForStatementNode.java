package com.pengy.pl0.parser.ast;

public class ForStatementNode extends StatementNode {

    private final String variableName;
    private final ExpressionNode startExpression;
    private final ExpressionNode endExpression;
    private final StatementNode body;

    public ForStatementNode(String variableName,
                            ExpressionNode startExpression,
                            ExpressionNode endExpression,
                            StatementNode body,
                            int line,
                            int column) {
        super(line, column);
        this.variableName = variableName;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.body = body;
    }

    public String getVariableName() {
        return variableName;
    }

    public ExpressionNode getStartExpression() {
        return startExpression;
    }

    public ExpressionNode getEndExpression() {
        return endExpression;
    }

    public StatementNode getBody() {
        return body;
    }
}
