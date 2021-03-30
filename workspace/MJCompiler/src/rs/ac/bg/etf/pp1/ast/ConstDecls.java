// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class ConstDecls extends List {

    private List List;
    private ConstDecl ConstDecl;

    public ConstDecls (List List, ConstDecl ConstDecl) {
        this.List=List;
        if(List!=null) List.setParent(this);
        this.ConstDecl=ConstDecl;
        if(ConstDecl!=null) ConstDecl.setParent(this);
    }

    public List getList() {
        return List;
    }

    public void setList(List List) {
        this.List=List;
    }

    public ConstDecl getConstDecl() {
        return ConstDecl;
    }

    public void setConstDecl(ConstDecl ConstDecl) {
        this.ConstDecl=ConstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(List!=null) List.accept(visitor);
        if(ConstDecl!=null) ConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(List!=null) List.traverseTopDown(visitor);
        if(ConstDecl!=null) ConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(List!=null) List.traverseBottomUp(visitor);
        if(ConstDecl!=null) ConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecls(\n");

        if(List!=null)
            buffer.append(List.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecl!=null)
            buffer.append(ConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecls]");
        return buffer.toString();
    }
}
