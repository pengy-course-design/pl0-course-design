package com.pengy.pl0.semantic;

import com.pengy.pl0.lexer.Lexer;
import com.pengy.pl0.parser.Parser;
import com.pengy.pl0.parser.ast.ProgramNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SemanticAnalyzerTest {

    @Test
    void shouldBuildSymbolTableForDeclarations() {
        SemanticAnalysisResult result = analyze("""
                const a = 10;
                var x, y;
                begin
                  x := a + 1;
                end.
                """);

        assertFalse(result.hasErrors());
        assertEquals(3, result.getSymbolTable().getSymbols().size());
    }

    @Test
    void shouldReportUndeclaredVariable() {
        SemanticAnalysisResult result = analyze("""
                begin
                  x += 1;
                end.
                """);

        assertTrue(result.hasErrors());
        assertTrue(result.getErrors().get(0).getMessage().contains("变量未声明"));
    }

    @Test
    void shouldReportConstAssignment() {
        SemanticAnalysisResult result = analyze("""
                const a = 1;
                begin
                  a := 2;
                end.
                """);

        assertTrue(result.hasErrors());
        assertTrue(result.getErrors().get(0).getMessage().contains("常量不能被赋值"));
    }

    @Test
    void shouldReportDuplicateDeclaration() {
        SemanticAnalysisResult result = analyze("""
                var x, x;
                begin
                end.
                """);

        assertTrue(result.hasErrors());
        assertTrue(result.getErrors().get(0).getMessage().contains("重复声明"));
    }

    @Test
    void shouldValidateForStatementControlVariable() {
        SemanticAnalysisResult result = analyze("""
                var i;
                begin
                  for i := 1 to 10 do
                    i := i + 1;
                end.
                """);

        assertFalse(result.hasErrors());
    }

    private SemanticAnalysisResult analyze(String source) {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        ProgramNode program = parser.parse();
        return new SemanticAnalyzer().analyze(program);
    }
}