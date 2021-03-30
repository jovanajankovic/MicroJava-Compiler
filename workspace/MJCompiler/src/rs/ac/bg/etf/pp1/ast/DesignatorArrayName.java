// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArrayName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String imeNiza;

    public DesignatorArrayName (String imeNiza) {
        this.imeNiza=imeNiza;
    }

    public String getImeNiza() {
        return imeNiza;
    }

    public void setImeNiza(String imeNiza) {
        this.imeNiza=imeNiza;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("DesignatorArrayName(\n");

        buffer.append(" "+tab+imeNiza);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArrayName]");
        return buffer.toString();
    }
}
