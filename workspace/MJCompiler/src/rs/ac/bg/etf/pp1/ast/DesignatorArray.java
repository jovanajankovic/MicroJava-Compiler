// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private DesignatorArrayName DesignatorArrayName;
    private Expr Expr;

    public DesignatorArray (DesignatorArrayName DesignatorArrayName, Expr Expr) {
        this.DesignatorArrayName=DesignatorArrayName;
        if(DesignatorArrayName!=null) DesignatorArrayName.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorArrayName getDesignatorArrayName() {
        return DesignatorArrayName;
    }

    public void setDesignatorArrayName(DesignatorArrayName DesignatorArrayName) {
        this.DesignatorArrayName=DesignatorArrayName;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArrayName!=null) DesignatorArrayName.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArrayName!=null) DesignatorArrayName.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArrayName!=null) DesignatorArrayName.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        if(DesignatorArrayName!=null)
            buffer.append(DesignatorArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
