package com.pengy.pl0.web;

import com.pengy.pl0.ir.IntermediateCodeGenerator;
import com.pengy.pl0.ir.IntermediateCodeResult;
import com.pengy.pl0.lexer.Lexer;
import com.pengy.pl0.lexer.Token;
import com.pengy.pl0.parser.Parser;
import com.pengy.pl0.parser.ParserException;
import com.pengy.pl0.parser.ast.ProgramNode;
import com.pengy.pl0.semantic.SemanticAnalysisResult;
import com.pengy.pl0.semantic.SemanticAnalyzer;
import com.pengy.pl0.semantic.SemanticError;
import com.pengy.pl0.semantic.Symbol;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompileController {

    @PostMapping("/compile")
    public CompileResponse compile(@RequestBody CompileRequest request) {
        CompileResponse response = new CompileResponse();
        String source = request.getSource() == null ? "" : request.getSource();

        try {
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.tokenize();
            response.setTokens(tokens.stream().map(Token::toString).toList());

            Parser parser = new Parser(tokens);
            ProgramNode program = parser.parse();
            response.setAstSummary(buildAstSummary(program));

            SemanticAnalysisResult semanticResult = new SemanticAnalyzer().analyze(program);
            response.setSymbols(semanticResult.getSymbolTable().getSymbols().stream()
                    .map(this::formatSymbol)
                    .toList());
            response.setSemanticErrors(semanticResult.getErrors().stream()
                    .map(this::formatSemanticError)
                    .toList());

            if (!semanticResult.hasErrors()) {
                IntermediateCodeResult irResult = new IntermediateCodeGenerator().generate(program);
                response.setQuadruples(irResult.getQuadruples().stream()
                        .map(Object::toString)
                        .toList());
            }

            response.setSuccess(!semanticResult.hasErrors());
            return response;
        } catch (ParserException | IllegalArgumentException exception) {
            response.setSuccess(false);
            response.setSyntaxError(exception.getMessage());
            return response;
        }
    }

    private String buildAstSummary(ProgramNode program) {
        int declarationCount = program.getBlock().getDeclarations().size();
        String statementType = program.getBlock().getStatement() == null
                ? "empty"
                : program.getBlock().getStatement().getClass().getSimpleName();

        return "ProgramNode{declarations=" + declarationCount + ", statement=" + statementType + "}";
    }

    private String formatSymbol(Symbol symbol) {
        if (symbol.getValue() == null) {
            return symbol.getType() + " " + symbol.getName();
        }

        return symbol.getType() + " " + symbol.getName() + " = " + symbol.getValue();
    }

    private String formatSemanticError(SemanticError error) {
        return "第 " + error.getLine() + " 行，第 " + error.getColumn() + " 列：" + error.getMessage();
    }
}