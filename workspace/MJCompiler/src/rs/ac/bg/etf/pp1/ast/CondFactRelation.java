// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class CondFactRelation extends CondFact {

    private Expr1 Expr1;
    private Relop Relop;
    private Expr1 Expr11;

    public CondFactRelation (Expr1 Expr1, Relop Relop, Expr1 Expr11) {
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.Expr11=Expr11;
        if(Expr11!=null) Expr11.setParent(this);
    }

    public Expr1 getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr1 Expr1) {
        this.Expr1=Expr1;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public Expr1 getExpr11() {
        return Expr11;
    }

    public void setExpr11(Expr1 Expr11) {
        this.Expr11=Expr11;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr1!=null) Expr1.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(Expr11!=null) Expr11.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(Expr11!=null) Expr11.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(Expr11!=null) Expr11.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFactRelation(\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr11!=null)
            buffer.append(Expr11.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFactRelation]");
        return buffer.toString();
    }
}
