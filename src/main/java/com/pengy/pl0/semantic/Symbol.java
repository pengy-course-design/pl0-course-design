package com.pengy.pl0.semantic;

public class Symbol {

    private final String name;
    private final SymbolType type;
    private final Integer value;

    public Symbol(String name, SymbolType type, Integer value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public SymbolType getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }
}