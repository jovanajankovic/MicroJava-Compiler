// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class FactorChar extends Factor {

    private Character karakter;

    public FactorChar (Character karakter) {
        this.karakter=karakter;
    }

    public Character getKarakter() {
        return karakter;
    }

    public void setKarakter(Character karakter) {
        this.karakter=karakter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorChar(\n");

        buffer.append(" "+tab+karakter);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorChar]");
        return buffer.toString();
    }
}
