// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class MinusExprLista extends Expr1 {

    private Term Term;
    private AddopList AddopList;

    public MinusExprLista (Term Term, AddopList AddopList) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AddopList=AddopList;
        if(AddopList!=null) AddopList.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public AddopList getAddopList() {
        return AddopList;
    }

    public void setAddopList(AddopList AddopList) {
        this.AddopList=AddopList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(AddopList!=null) AddopList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AddopList!=null) AddopList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AddopList!=null) AddopList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MinusExprLista(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddopList!=null)
            buffer.append(AddopList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MinusExprLista]");
        return buffer.toString();
    }
}
