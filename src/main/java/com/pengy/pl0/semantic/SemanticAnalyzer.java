package com.pengy.pl0.semantic;

import com.pengy.pl0.parser.ast.AssignmentStatementNode;
import com.pengy.pl0.parser.ast.BinaryExpressionNode;
import com.pengy.pl0.parser.ast.BlockNode;
import com.pengy.pl0.parser.ast.CompoundStatementNode;
import com.pengy.pl0.parser.ast.ConstDeclarationNode;
import com.pengy.pl0.parser.ast.DeclarationNode;
import com.pengy.pl0.parser.ast.EmptyStatementNode;
import com.pengy.pl0.parser.ast.ExpressionNode;
import com.pengy.pl0.parser.ast.ForStatementNode;
import com.pengy.pl0.parser.ast.IdentifierExpressionNode;
import com.pengy.pl0.parser.ast.NumberExpressionNode;
import com.pengy.pl0.parser.ast.ProgramNode;
import com.pengy.pl0.parser.ast.StatementNode;
import com.pengy.pl0.parser.ast.UnaryStatementNode;
import com.pengy.pl0.parser.ast.VarDeclarationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SemanticAnalyzer {

    private final SymbolTable symbolTable = new SymbolTable();
    private final List<SemanticError> errors = new ArrayList<>();

    public SemanticAnalysisResult analyze(ProgramNode program) {
        errors.clear();
        analyzeBlock(program.getBlock());
        return new SemanticAnalysisResult(symbolTable, errors);
    }

    private void analyzeBlock(BlockNode block) {
        for (DeclarationNode declaration : block.getDeclarations()) {
            analyzeDeclaration(declaration);
        }

        analyzeStatement(block.getStatement());
    }

    private void analyzeDeclaration(DeclarationNode declaration) {
        Symbol symbol;

        if (declaration instanceof ConstDeclarationNode constDeclaration) {
            symbol = new Symbol(
                    constDeclaration.getName(),
                    SymbolType.CONST,
                    constDeclaration.getValue()
            );
        } else if (declaration instanceof VarDeclarationNode varDeclaration) {
            symbol = new Symbol(
                    varDeclaration.getName(),
                    SymbolType.VAR,
                    null
            );
        } else {
            return;
        }

        if (!symbolTable.define(symbol)) {
            addError("重复声明标识符: " + declaration.getName(), declaration);
        }
    }

    private void analyzeStatement(StatementNode statement) {
        if (statement == null || statement instanceof EmptyStatementNode) {
            return;
        }

        if (statement instanceof CompoundStatementNode compoundStatement) {
            for (StatementNode child : compoundStatement.getStatements()) {
                analyzeStatement(child);
            }
            return;
        }

        if (statement instanceof AssignmentStatementNode assignmentStatement) {
            analyzeAssignmentStatement(assignmentStatement);
            return;
        }

        if (statement instanceof UnaryStatementNode unaryStatement) {
            analyzeUnaryStatement(unaryStatement);
            return;
        }

        if (statement instanceof ForStatementNode forStatement) {
            analyzeForStatement(forStatement);
        }
    }

    private void analyzeAssignmentStatement(AssignmentStatementNode statement) {
        Optional<Symbol> symbol = symbolTable.resolve(statement.getVariableName());

        if (symbol.isEmpty()) {
            addError("变量未声明: " + statement.getVariableName(), statement);
        } else if (symbol.get().getType() == SymbolType.CONST) {
            addError("常量不能被赋值: " + statement.getVariableName(), statement);
        }

        analyzeExpression(statement.getExpression());
    }

    private void analyzeUnaryStatement(UnaryStatementNode statement) {
        Optional<Symbol> symbol = symbolTable.resolve(statement.getVariableName());

        if (symbol.isEmpty()) {
            addError("变量未声明: " + statement.getVariableName(), statement);
        } else if (symbol.get().getType() == SymbolType.CONST) {
            addError("常量不能执行自增或自减: " + statement.getVariableName(), statement);
        }
    }

    private void analyzeForStatement(ForStatementNode statement) {
        Optional<Symbol> symbol = symbolTable.resolve(statement.getVariableName());

        if (symbol.isEmpty()) {
            addError("for 循环变量未声明: " + statement.getVariableName(), statement);
        } else if (symbol.get().getType() == SymbolType.CONST) {
            addError("for 循环变量不能是常量: " + statement.getVariableName(), statement);
        }

        analyzeExpression(statement.getStartExpression());
        analyzeExpression(statement.getEndExpression());
        analyzeStatement(statement.getBody());
    }

    private void analyzeExpression(ExpressionNode expression) {
        if (expression == null || expression instanceof NumberExpressionNode) {
            return;
        }

        if (expression instanceof IdentifierExpressionNode identifierExpression) {
            if (symbolTable.resolve(identifierExpression.getName()).isEmpty()) {
                addError("变量未声明: " + identifierExpression.getName(), identifierExpression);
            }
            return;
        }

        if (expression instanceof BinaryExpressionNode binaryExpression) {
            analyzeExpression(binaryExpression.getLeft());
            analyzeExpression(binaryExpression.getRight());
        }
    }

    private void addError(String message, com.pengy.pl0.parser.ast.AstNode node) {
        errors.add(new SemanticError(message, node.getLine(), node.getColumn()));
    }
}