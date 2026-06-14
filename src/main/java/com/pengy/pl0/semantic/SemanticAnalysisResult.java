package com.pengy.pl0.semantic;

import java.util.Collections;
import java.util.List;

public class SemanticAnalysisResult {

    private final SymbolTable symbolTable;
    private final List<SemanticError> errors;

    public SemanticAnalysisResult(SymbolTable symbolTable, List<SemanticError> errors) {
        this.symbolTable = symbolTable;
        this.errors = errors;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public List<SemanticError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}