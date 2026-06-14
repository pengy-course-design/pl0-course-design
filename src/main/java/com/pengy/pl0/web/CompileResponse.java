package com.pengy.pl0.web;

import java.util.ArrayList;
import java.util.List;

public class CompileResponse {

    private boolean success;
    private List<String> tokens = new ArrayList<>();
    private String astSummary;
    private List<String> symbols = new ArrayList<>();
    private List<String> semanticErrors = new ArrayList<>();
    private List<String> quadruples = new ArrayList<>();
    private String syntaxError;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public String getAstSummary() {
        return astSummary;
    }

    public void setAstSummary(String astSummary) {
        this.astSummary = astSummary;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    public List<String> getSemanticErrors() {
        return semanticErrors;
    }

    public void setSemanticErrors(List<String> semanticErrors) {
        this.semanticErrors = semanticErrors;
    }

    public List<String> getQuadruples() {
        return quadruples;
    }

    public void setQuadruples(List<String> quadruples) {
        this.quadruples = quadruples;
    }

    public String getSyntaxError() {
        return syntaxError;
    }

    public void setSyntaxError(String syntaxError) {
        this.syntaxError = syntaxError;
    }
}