// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class Void_Povratna extends Povratna {

    public Void_Povratna () {
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
        buffer.append("Void_Povratna(\n");

        buffer.append(tab);
        buffer.append(") [Void_Povratna]");
        return buffer.toString();
    }
}
