// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class DodelaVrednosti extends Dodela {

    private String constName;
    private Vrednost Vrednost;

    public DodelaVrednosti (String constName, Vrednost Vrednost) {
        this.constName=constName;
        this.Vrednost=Vrednost;
        if(Vrednost!=null) Vrednost.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Vrednost getVrednost() {
        return Vrednost;
    }

    public void setVrednost(Vrednost Vrednost) {
        this.Vrednost=Vrednost;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Vrednost!=null) Vrednost.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Vrednost!=null) Vrednost.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Vrednost!=null) Vrednost.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DodelaVrednosti(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Vrednost!=null)
            buffer.append(Vrednost.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DodelaVrednosti]");
        return buffer.toString();
    }
}
