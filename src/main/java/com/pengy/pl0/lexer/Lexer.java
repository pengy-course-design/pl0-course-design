package com.pengy.pl0.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("const", TokenType.CONST);
        KEYWORDS.put("var", TokenType.VAR);
        KEYWORDS.put("procedure", TokenType.PROCEDURE);
        KEYWORDS.put("begin", TokenType.BEGIN);
        KEYWORDS.put("end", TokenType.END);
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("then", TokenType.THEN);
        KEYWORDS.put("while", TokenType.WHILE);
        KEYWORDS.put("do", TokenType.DO);
        KEYWORDS.put("call", TokenType.CALL);
        KEYWORDS.put("odd", TokenType.ODD);
        KEYWORDS.put("for", TokenType.FOR);
        KEYWORDS.put("to", TokenType.TO);
    }

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int index = 0;
    private int line = 1;
    private int column = 1;

    public Lexer(String source) {
        this.source = source == null ? "" : source;
    }

    public List<Token> tokenize() {
        tokens.clear();
        index = 0;
        line = 1;
        column = 1;

        while (!isAtEnd()) {
            char current = peek();

            if (Character.isWhitespace(current)) {
                consumeWhitespace();
            } else if (Character.isLetter(current)) {
                readIdentifierOrKeyword();
            } else if (Character.isDigit(current)) {
                readNumber();
            } else {
                readSymbol();
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    private void readIdentifierOrKeyword() {
        int startColumn = column;
        StringBuilder builder = new StringBuilder();

        while (!isAtEnd() && (Character.isLetterOrDigit(peek()) || peek() == '_')) {
            builder.append(advance());
        }

        String lexeme = builder.toString();
        TokenType type = KEYWORDS.getOrDefault(lexeme.toLowerCase(), TokenType.IDENTIFIER);
        tokens.add(new Token(type, lexeme, line, startColumn));
    }

    private void readNumber() {
        int startColumn = column;
        StringBuilder builder = new StringBuilder();

        while (!isAtEnd() && Character.isDigit(peek())) {
            builder.append(advance());
        }

        tokens.add(new Token(TokenType.NUMBER, builder.toString(), line, startColumn));
    }

    private void readSymbol() {
        int startColumn = column;
        char current = advance();

        switch (current) {
            case '+':
                if (match('=')) {
                    tokens.add(new Token(TokenType.PLUS_ASSIGN, "+=", line, startColumn));
                } else if (match('+')) {
                    tokens.add(new Token(TokenType.INCREMENT, "++", line, startColumn));
                } else {
                    tokens.add(new Token(TokenType.PLUS, "+", line, startColumn));
                }
                break;
            case '-':
                if (match('=')) {
                    tokens.add(new Token(TokenType.MINUS_ASSIGN, "-=", line, startColumn));
                } else if (match('-')) {
                    tokens.add(new Token(TokenType.DECREMENT, "--", line, startColumn));
                } else {
                    tokens.add(new Token(TokenType.MINUS, "-", line, startColumn));
                }
                break;
            case '*':
                tokens.add(new Token(TokenType.TIMES, "*", line, startColumn));
                break;
            case '/':
                tokens.add(new Token(TokenType.SLASH, "/", line, startColumn));
                break;
            case '=':
                tokens.add(new Token(TokenType.EQUAL, "=", line, startColumn));
                break;
            case '#':
                tokens.add(new Token(TokenType.NOT_EQUAL, "#", line, startColumn));
                break;
            case '<':
                if (match('=')) {
                    tokens.add(new Token(TokenType.LESS_EQUAL, "<=", line, startColumn));
                } else {
                    tokens.add(new Token(TokenType.LESS, "<", line, startColumn));
                }
                break;
            case '>':
                if (match('=')) {
                    tokens.add(new Token(TokenType.GREATER_EQUAL, ">=", line, startColumn));
                } else {
                    tokens.add(new Token(TokenType.GREATER, ">", line, startColumn));
                }
                break;
            case ':':
                if (match('=')) {
                    tokens.add(new Token(TokenType.ASSIGN, ":=", line, startColumn));
                } else {
                    throw error("':' 后面应为 '='");
                }
                break;
            case ',':
                tokens.add(new Token(TokenType.COMMA, ",", line, startColumn));
                break;
            case ';':
                tokens.add(new Token(TokenType.SEMICOLON, ";", line, startColumn));
                break;
            case '.':
                tokens.add(new Token(TokenType.PERIOD, ".", line, startColumn));
                break;
            case '(':
                tokens.add(new Token(TokenType.LEFT_PAREN, "(", line, startColumn));
                break;
            case ')':
                tokens.add(new Token(TokenType.RIGHT_PAREN, ")", line, startColumn));
                break;
            default:
                throw error("无法识别的字符: " + current);
        }
    }

    private void consumeWhitespace() {
        while (!isAtEnd() && Character.isWhitespace(peek())) {
            advance();
        }
    }

    private boolean match(char expected) {
        if (isAtEnd() || peek() != expected) {
            return false;
        }
        advance();
        return true;
    }

    private char peek() {
        return source.charAt(index);
    }

    private char advance() {
        char current = source.charAt(index++);
        if (current == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
        return current;
    }

    private boolean isAtEnd() {
        return index >= source.length();
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException("词法错误 第 " + line + " 行，第 " + column + " 列：" + message);
    }
}
