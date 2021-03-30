// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class Assignment extends DesignatorStatement {

    private Assign_Designator Assign_Designator;

    public Assignment (Assign_Designator Assign_Designator) {
        this.Assign_Designator=Assign_Designator;
        if(Assign_Designator!=null) Assign_Designator.setParent(this);
    }

    public Assign_Designator getAssign_Designator() {
        return Assign_Designator;
    }

    public void setAssign_Designator(Assign_Designator Assign_Designator) {
        this.Assign_Designator=Assign_Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Assign_Designator!=null) Assign_Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Assign_Designator!=null) Assign_Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Assign_Designator!=null) Assign_Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Assignment(\n");

        if(Assign_Designator!=null)
            buffer.append(Assign_Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Assignment]");
        return buffer.toString();
    }
}
