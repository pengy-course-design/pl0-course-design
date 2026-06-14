package com.pengy.pl0.ir;

import com.pengy.pl0.lexer.Lexer;
import com.pengy.pl0.parser.Parser;
import com.pengy.pl0.parser.ast.ProgramNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntermediateCodeGeneratorTest {

    @Test
    void shouldGenerateQuadruplesForExtendedAssignments() {
        IntermediateCodeResult result = generate("""
                var x;
                begin
                  x := 1;
                  x += 2;
                  x -= 3;
                  x++;
                  x--;
                end.
                """);

        List<String> codes = result.getQuadruples().stream()
                .map(Quadruple::toString)
                .toList();

        assertEquals("(:=, 1, _, x)", codes.get(0));
        assertEquals("(+, x, 2, x)", codes.get(1));
        assertEquals("(-, x, 3, x)", codes.get(2));
        assertEquals("(+, x, 1, x)", codes.get(3));
        assertEquals("(-, x, 1, x)", codes.get(4));
    }

    @Test
    void shouldGenerateQuadruplesForForStatement() {
        IntermediateCodeResult result = generate("""
                var i;
                begin
                  for i := 1 to 3 do
                    i := i + 1;
                end.
                """);

        List<String> codes = result.getQuadruples().stream()
                .map(Quadruple::toString)
                .toList();

        assertTrue(codes.contains("(:=, 1, _, i)"));
        assertTrue(codes.contains("(label, _, _, L1)"));
        assertTrue(codes.contains("(j>, i, 3, L2)"));
        assertTrue(codes.contains("(j, _, _, L1)"));
        assertTrue(codes.contains("(label, _, _, L2)"));
    }

    private IntermediateCodeResult generate(String source) {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        ProgramNode program = parser.parse();
        return new IntermediateCodeGenerator().generate(program);
    }
}