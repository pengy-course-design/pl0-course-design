package com.pengy.pl0.parser.ast;

public class BinaryExpressionNode extends ExpressionNode {

    private final String operator;
    private final ExpressionNode left;
    private final ExpressionNode right;

    public BinaryExpressionNode(String operator, ExpressionNode left, ExpressionNode right, int line, int column) {
        super(line, column);
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }
}
