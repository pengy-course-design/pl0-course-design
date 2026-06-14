package com.pengy.pl0.ir;

import java.util.Collections;
import java.util.List;

public class IntermediateCodeResult {

    private final List<Quadruple> quadruples;

    public IntermediateCodeResult(List<Quadruple> quadruples) {
        this.quadruples = quadruples;
    }

    public List<Quadruple> getQuadruples() {
        return Collections.unmodifiableList(quadruples);
    }
}