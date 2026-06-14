package com.pengy.pl0.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {

    @Test
    void shouldTokenizeExtendedAssignmentOperators() {
        Lexer lexer = new Lexer("""
                var x;
                begin
                  x := 1;
                  x += 2;
                  x -= 3;
                  x++;
                  x--;
                end.
                """);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.VAR, tokens.get(0).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals(TokenType.SEMICOLON, tokens.get(2).getType());

        assertEquals(TokenType.ASSIGN, tokens.get(5).getType());
        assertEquals(TokenType.PLUS_ASSIGN, tokens.get(9).getType());
        assertEquals(TokenType.MINUS_ASSIGN, tokens.get(13).getType());
        assertEquals(TokenType.INCREMENT, tokens.get(17).getType());
        assertEquals(TokenType.DECREMENT, tokens.get(20).getType());
    }

    @Test
    void shouldTokenizeForStatementKeywords() {
        Lexer lexer = new Lexer("""
                var i;
                begin
                  for i := 1 to 10 do
                    i := i + 1;
                end.
                """);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.FOR, tokens.get(4).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(5).getType());
        assertEquals(TokenType.ASSIGN, tokens.get(6).getType());
        assertEquals(TokenType.TO, tokens.get(8).getType());
        assertEquals(TokenType.DO, tokens.get(10).getType());
    }
}
