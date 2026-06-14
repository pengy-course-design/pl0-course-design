package com.pengy.pl0.parser;

import com.pengy.pl0.lexer.Token;
import com.pengy.pl0.lexer.TokenType;
import com.pengy.pl0.parser.ast.AssignmentStatementNode;
import com.pengy.pl0.parser.ast.BinaryExpressionNode;
import com.pengy.pl0.parser.ast.BlockNode;
import com.pengy.pl0.parser.ast.CompoundStatementNode;
import com.pengy.pl0.parser.ast.ConstDeclarationNode;
import com.pengy.pl0.parser.ast.EmptyStatementNode;
import com.pengy.pl0.parser.ast.ExpressionNode;
import com.pengy.pl0.parser.ast.ForStatementNode;
import com.pengy.pl0.parser.ast.IdentifierExpressionNode;
import com.pengy.pl0.parser.ast.NumberExpressionNode;
import com.pengy.pl0.parser.ast.ProgramNode;
import com.pengy.pl0.parser.ast.StatementNode;
import com.pengy.pl0.parser.ast.UnaryStatementNode;
import com.pengy.pl0.parser.ast.VarDeclarationNode;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ProgramNode parse() {
        BlockNode block = parseBlock();
        consume(TokenType.PERIOD, "程序必须以 '.' 结束");
        consume(TokenType.EOF, "'.' 后面不应该再有内容");
        return new ProgramNode(block, 1, 1);
    }

    private BlockNode parseBlock() {
        Token start = peek();
        BlockNode block = new BlockNode(start.getLine(), start.getColumn());

        if (match(TokenType.CONST)) {
            parseConstDeclarations(block);
        }

        if (match(TokenType.VAR)) {
            parseVarDeclarations(block);
        }

        block.setStatement(parseStatement());
        return block;
    }

    private void parseConstDeclarations(BlockNode block) {
        do {
            Token name = consume(TokenType.IDENTIFIER, "const 后面应为常量名");
            consume(TokenType.EQUAL, "常量名后面应为 '='");
            Token value = consume(TokenType.NUMBER, "常量赋值应为数字");

            block.addDeclaration(new ConstDeclarationNode(
                    name.getLexeme(),
                    Integer.parseInt(value.getLexeme()),
                    name.getLine(),
                    name.getColumn()
            ));
        } while (match(TokenType.COMMA));

        consume(TokenType.SEMICOLON, "const 声明应以 ';' 结束");
    }

    private void parseVarDeclarations(BlockNode block) {
        do {
            Token name = consume(TokenType.IDENTIFIER, "var 后面应为变量名");
            block.addDeclaration(new VarDeclarationNode(
                    name.getLexeme(),
                    name.getLine(),
                    name.getColumn()
            ));
        } while (match(TokenType.COMMA));

        consume(TokenType.SEMICOLON, "var 声明应以 ';' 结束");
    }

    private StatementNode parseStatement() {
        if (check(TokenType.IDENTIFIER)) {
            return parseIdentifierStatement();
        }

        if (match(TokenType.BEGIN)) {
            return parseCompoundStatement(previous());
        }

        if (match(TokenType.FOR)) {
            return parseForStatement(previous());
        }

        Token token = peek();
        return new EmptyStatementNode(token.getLine(), token.getColumn());
    }

    private StatementNode parseIdentifierStatement() {
        Token name = consume(TokenType.IDENTIFIER, "应为变量名");

        if (match(TokenType.ASSIGN)) {
            ExpressionNode expression = parseExpression();
            return new AssignmentStatementNode(
                    name.getLexeme(),
                    ":=",
                    expression,
                    name.getLine(),
                    name.getColumn()
            );
        }

        if (match(TokenType.PLUS_ASSIGN)) {
            ExpressionNode expression = parseExpression();
            return new AssignmentStatementNode(
                    name.getLexeme(),
                    "+=",
                    expression,
                    name.getLine(),
                    name.getColumn()
            );
        }

        if (match(TokenType.MINUS_ASSIGN)) {
            ExpressionNode expression = parseExpression();
            return new AssignmentStatementNode(
                    name.getLexeme(),
                    "-=",
                    expression,
                    name.getLine(),
                    name.getColumn()
            );
        }

        if (match(TokenType.INCREMENT)) {
            return new UnaryStatementNode(
                    name.getLexeme(),
                    "++",
                    name.getLine(),
                    name.getColumn()
            );
        }

        if (match(TokenType.DECREMENT)) {
            return new UnaryStatementNode(
                    name.getLexeme(),
                    "--",
                    name.getLine(),
                    name.getColumn()
            );
        }

        throw error(peek(), "变量名后面应为 ':='、'+='、'-='、'++' 或 '--'");
    }

    private CompoundStatementNode parseCompoundStatement(Token beginToken) {
        CompoundStatementNode compound = new CompoundStatementNode(
                beginToken.getLine(),
                beginToken.getColumn()
        );

        if (!check(TokenType.END)) {
            compound.addStatement(parseStatement());

            while (match(TokenType.SEMICOLON)) {
                if (check(TokenType.END)) {
                    break;
                }
                compound.addStatement(parseStatement());
            }
        }

        consume(TokenType.END, "begin 后面缺少 end");
        return compound;
    }

    private ForStatementNode parseForStatement(Token forToken) {
        Token variable = consume(TokenType.IDENTIFIER, "for 后面应为循环变量");
        consume(TokenType.ASSIGN, "for 循环变量后面应为 ':='");

        ExpressionNode startExpression = parseExpression();

        consume(TokenType.TO, "for 起始表达式后面应为 to");

        ExpressionNode endExpression = parseExpression();

        consume(TokenType.DO, "for 结束表达式后面应为 do");

        StatementNode body = parseStatement();

        return new ForStatementNode(
                variable.getLexeme(),
                startExpression,
                endExpression,
                body,
                forToken.getLine(),
                forToken.getColumn()
        );
    }

    private ExpressionNode parseExpression() {
        ExpressionNode expression = parseTerm();

        while (match(TokenType.PLUS) || match(TokenType.MINUS)) {
            Token operator = previous();
            ExpressionNode right = parseTerm();

            expression = new BinaryExpressionNode(
                    operator.getLexeme(),
                    expression,
                    right,
                    operator.getLine(),
                    operator.getColumn()
            );
        }

        return expression;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode expression = parseFactor();

        while (match(TokenType.TIMES) || match(TokenType.SLASH)) {
            Token operator = previous();
            ExpressionNode right = parseFactor();

            expression = new BinaryExpressionNode(
                    operator.getLexeme(),
                    expression,
                    right,
                    operator.getLine(),
                    operator.getColumn()
            );
        }

        return expression;
    }

    private ExpressionNode parseFactor() {
        if (match(TokenType.NUMBER)) {
            Token number = previous();
            return new NumberExpressionNode(
                    Integer.parseInt(number.getLexeme()),
                    number.getLine(),
                    number.getColumn()
            );
        }

        if (match(TokenType.IDENTIFIER)) {
            Token identifier = previous();
            return new IdentifierExpressionNode(
                    identifier.getLexeme(),
                    identifier.getLine(),
                    identifier.getColumn()
            );
        }

        if (match(TokenType.LEFT_PAREN)) {
            ExpressionNode expression = parseExpression();
            consume(TokenType.RIGHT_PAREN, "表达式缺少右括号 ')'");
            return expression;
        }

        throw error(peek(), "应为数字、变量名或括号表达式");
    }

    private boolean match(TokenType type) {
        if (!check(type)) {
            return false;
        }

        advance();
        return true;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }

        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return type == TokenType.EOF;
        }

        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }

        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParserException error(Token token, String message) {
        return new ParserException(
                "语法错误 第 " + token.getLine() + " 行，第 " + token.getColumn() + " 列：" + message
        );
    }
}