package com.pengy.pl0.ir;

import com.pengy.pl0.parser.ast.AssignmentStatementNode;
import com.pengy.pl0.parser.ast.BinaryExpressionNode;
import com.pengy.pl0.parser.ast.CompoundStatementNode;
import com.pengy.pl0.parser.ast.EmptyStatementNode;
import com.pengy.pl0.parser.ast.ExpressionNode;
import com.pengy.pl0.parser.ast.ForStatementNode;
import com.pengy.pl0.parser.ast.IdentifierExpressionNode;
import com.pengy.pl0.parser.ast.NumberExpressionNode;
import com.pengy.pl0.parser.ast.ProgramNode;
import com.pengy.pl0.parser.ast.StatementNode;
import com.pengy.pl0.parser.ast.UnaryStatementNode;

import java.util.ArrayList;
import java.util.List;

public class IntermediateCodeGenerator {

    private final List<Quadruple> quadruples = new ArrayList<>();
    private int tempIndex = 1;
    private int labelIndex = 1;

    public IntermediateCodeResult generate(ProgramNode program) {
        quadruples.clear();
        tempIndex = 1;
        labelIndex = 1;

        generateStatement(program.getBlock().getStatement());

        return new IntermediateCodeResult(new ArrayList<>(quadruples));
    }

    private void generateStatement(StatementNode statement) {
        if (statement == null || statement instanceof EmptyStatementNode) {
            return;
        }

        if (statement instanceof CompoundStatementNode compoundStatement) {
            for (StatementNode child : compoundStatement.getStatements()) {
                generateStatement(child);
            }
            return;
        }

        if (statement instanceof AssignmentStatementNode assignmentStatement) {
            generateAssignment(assignmentStatement);
            return;
        }

        if (statement instanceof UnaryStatementNode unaryStatement) {
            generateUnary(unaryStatement);
            return;
        }

        if (statement instanceof ForStatementNode forStatement) {
            generateFor(forStatement);
        }
    }

    private void generateAssignment(AssignmentStatementNode statement) {
        String value = generateExpression(statement.getExpression());
        String variable = statement.getVariableName();

        switch (statement.getOperator()) {
            case ":=" -> emit(":=", value, "_", variable);
            case "+=" -> emit("+", variable, value, variable);
            case "-=" -> emit("-", variable, value, variable);
            default -> throw new IllegalArgumentException("不支持的赋值运算符: " + statement.getOperator());
        }
    }

    private void generateUnary(UnaryStatementNode statement) {
        String variable = statement.getVariableName();

        switch (statement.getOperator()) {
            case "++" -> emit("+", variable, "1", variable);
            case "--" -> emit("-", variable, "1", variable);
            default -> throw new IllegalArgumentException("不支持的一元运算符: " + statement.getOperator());
        }
    }

    private void generateFor(ForStatementNode statement) {
        String variable = statement.getVariableName();
        String start = generateExpression(statement.getStartExpression());
        String end = generateExpression(statement.getEndExpression());
        String startLabel = newLabel();
        String endLabel = newLabel();

        emit(":=", start, "_", variable);
        emit("label", "_", "_", startLabel);
        emit("j>", variable, end, endLabel);
        generateStatement(statement.getBody());
        emit("+", variable, "1", variable);
        emit("j", "_", "_", startLabel);
        emit("label", "_", "_", endLabel);
    }

    private String generateExpression(ExpressionNode expression) {
        if (expression instanceof NumberExpressionNode numberExpression) {
            return String.valueOf(numberExpression.getValue());
        }

        if (expression instanceof IdentifierExpressionNode identifierExpression) {
            return identifierExpression.getName();
        }

        if (expression instanceof BinaryExpressionNode binaryExpression) {
            String left = generateExpression(binaryExpression.getLeft());
            String right = generateExpression(binaryExpression.getRight());
            String temp = newTemp();
            emit(binaryExpression.getOperator(), left, right, temp);
            return temp;
        }

        throw new IllegalArgumentException("不支持的表达式节点: " + expression.getClass().getSimpleName());
    }

    private void emit(String operator, String arg1, String arg2, String result) {
        quadruples.add(new Quadruple(operator, arg1, arg2, result));
    }

    private String newTemp() {
        return "t" + tempIndex++;
    }

    private String newLabel() {
        return "L" + labelIndex++;
    }
}