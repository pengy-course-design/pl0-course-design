package com.pengy.pl0.lexer;

public enum TokenType {
    IDENTIFIER,
    NUMBER,

    CONST,
    VAR,
    PROCEDURE,
    BEGIN,
    END,
    IF,
    THEN,
    WHILE,
    DO,
    CALL,
    ODD,
    FOR,
    TO,

    PLUS,
    MINUS,
    TIMES,
    SLASH,

    PLUS_ASSIGN,
    MINUS_ASSIGN,
    INCREMENT,
    DECREMENT,

    EQUAL,
    NOT_EQUAL,
    LESS,
    LESS_EQUAL,
    GREATER,
    GREATER_EQUAL,

    ASSIGN,
    COMMA,
    SEMICOLON,
    PERIOD,
    LEFT_PAREN,
    RIGHT_PAREN,

    EOF
}
