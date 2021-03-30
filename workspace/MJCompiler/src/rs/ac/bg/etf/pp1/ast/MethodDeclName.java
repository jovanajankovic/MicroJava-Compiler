// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Povratna Povratna;
    private String nazivMetode;

    public MethodDeclName (Povratna Povratna, String nazivMetode) {
        this.Povratna=Povratna;
        if(Povratna!=null) Povratna.setParent(this);
        this.nazivMetode=nazivMetode;
    }

    public Povratna getPovratna() {
        return Povratna;
    }

    public void setPovratna(Povratna Povratna) {
        this.Povratna=Povratna;
    }

    public String getNazivMetode() {
        return nazivMetode;
    }

    public void setNazivMetode(String nazivMetode) {
        this.nazivMetode=nazivMetode;
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
        if(Povratna!=null) Povratna.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Povratna!=null) Povratna.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Povratna!=null) Povratna.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclName(\n");

        if(Povratna!=null)
            buffer.append(Povratna.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+nazivMetode);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclName]");
        return buffer.toString();
    }
}
