// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class VarDeclOpstaSmena extends VarDecl {

    private Type Type;
    private VarPomocnaLista VarPomocnaLista;

    public VarDeclOpstaSmena (Type Type, VarPomocnaLista VarPomocnaLista) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarPomocnaLista=VarPomocnaLista;
        if(VarPomocnaLista!=null) VarPomocnaLista.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarPomocnaLista getVarPomocnaLista() {
        return VarPomocnaLista;
    }

    public void setVarPomocnaLista(VarPomocnaLista VarPomocnaLista) {
        this.VarPomocnaLista=VarPomocnaLista;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarPomocnaLista!=null) VarPomocnaLista.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarPomocnaLista!=null) VarPomocnaLista.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarPomocnaLista!=null) VarPomocnaLista.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclOpstaSmena(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarPomocnaLista!=null)
            buffer.append(VarPomocnaLista.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclOpstaSmena]");
        return buffer.toString();
    }
}
