// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class VarViseDeklaracija extends VarPomocnaLista {

    private VarPomocnaLista VarPomocnaLista;
    private VarDeoDeklaracije VarDeoDeklaracije;

    public VarViseDeklaracija (VarPomocnaLista VarPomocnaLista, VarDeoDeklaracije VarDeoDeklaracije) {
        this.VarPomocnaLista=VarPomocnaLista;
        if(VarPomocnaLista!=null) VarPomocnaLista.setParent(this);
        this.VarDeoDeklaracije=VarDeoDeklaracije;
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.setParent(this);
    }

    public VarPomocnaLista getVarPomocnaLista() {
        return VarPomocnaLista;
    }

    public void setVarPomocnaLista(VarPomocnaLista VarPomocnaLista) {
        this.VarPomocnaLista=VarPomocnaLista;
    }

    public VarDeoDeklaracije getVarDeoDeklaracije() {
        return VarDeoDeklaracije;
    }

    public void setVarDeoDeklaracije(VarDeoDeklaracije VarDeoDeklaracije) {
        this.VarDeoDeklaracije=VarDeoDeklaracije;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarPomocnaLista!=null) VarPomocnaLista.accept(visitor);
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarPomocnaLista!=null) VarPomocnaLista.traverseTopDown(visitor);
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarPomocnaLista!=null) VarPomocnaLista.traverseBottomUp(visitor);
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarViseDeklaracija(\n");

        if(VarPomocnaLista!=null)
            buffer.append(VarPomocnaLista.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeoDeklaracije!=null)
            buffer.append(VarDeoDeklaracije.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarViseDeklaracija]");
        return buffer.toString();
    }
}
