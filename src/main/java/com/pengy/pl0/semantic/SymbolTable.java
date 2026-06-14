package com.pengy.pl0.semantic;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SymbolTable {

    private final Map<String, Symbol> symbols = new LinkedHashMap<>();

    public boolean define(Symbol symbol) {
        if (symbols.containsKey(symbol.getName())) {
            return false;
        }

        symbols.put(symbol.getName(), symbol);
        return true;
    }

    public Optional<Symbol> resolve(String name) {
        return Optional.ofNullable(symbols.get(name));
    }

    public Collection<Symbol> getSymbols() {
        return symbols.values();
    }
}