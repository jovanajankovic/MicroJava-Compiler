// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class VarJednaDeklaracija extends VarPomocnaLista {

    private VarDeoDeklaracije VarDeoDeklaracije;

    public VarJednaDeklaracija (VarDeoDeklaracije VarDeoDeklaracije) {
        this.VarDeoDeklaracije=VarDeoDeklaracije;
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.setParent(this);
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
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeoDeklaracije!=null) VarDeoDeklaracije.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarJednaDeklaracija(\n");

        if(VarDeoDeklaracije!=null)
            buffer.append(VarDeoDeklaracije.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarJednaDeklaracija]");
        return buffer.toString();
    }
}
