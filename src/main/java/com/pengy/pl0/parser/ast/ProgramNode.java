package com.pengy.pl0.parser.ast;

public class ProgramNode extends AstNode {

    private final BlockNode block;

    public ProgramNode(BlockNode block, int line, int column) {
        super(line, column);
        this.block = block;
    }

    public BlockNode getBlock() {
        return block;
    }
}
