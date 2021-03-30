// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class Character_Const extends Vrednost {

    private Character karakter;

    public Character_Const (Character karakter) {
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
        buffer.append("Character_Const(\n");

        buffer.append(" "+tab+karakter);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Character_Const]");
        return buffer.toString();
    }
}
