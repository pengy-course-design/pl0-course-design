package com.pengy.pl0.parser;

import com.pengy.pl0.lexer.Lexer;
import com.pengy.pl0.parser.ast.BlockNode;
import com.pengy.pl0.parser.ast.CompoundStatementNode;
import com.pengy.pl0.parser.ast.ForStatementNode;
import com.pengy.pl0.parser.ast.ProgramNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ParserTest {

    @Test
    void shouldParseAssignmentAndExtendedAssignmentStatements() {
        ProgramNode program = parse("""
                var x;
                begin
                  x := 1;
                  x += 2;
                  x -= 3;
                  x++;
                  x--;
                end.
                """);

        BlockNode block = program.getBlock();

        assertEquals(1, block.getDeclarations().size());
        assertInstanceOf(CompoundStatementNode.class, block.getStatement());

        CompoundStatementNode compound = (CompoundStatementNode) block.getStatement();

        assertEquals(5, compound.getStatements().size());
    }

    @Test
    void shouldParseForStatement() {
        ProgramNode program = parse("""
                var i;
                begin
                  for i := 1 to 10 do
                    i := i + 1;
                end.
                """);

        CompoundStatementNode compound = (CompoundStatementNode) program.getBlock().getStatement();

        assertEquals(1, compound.getStatements().size());
        assertInstanceOf(ForStatementNode.class, compound.getStatements().get(0));
    }

    private ProgramNode parse(String source) {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        return parser.parse();
    }
}