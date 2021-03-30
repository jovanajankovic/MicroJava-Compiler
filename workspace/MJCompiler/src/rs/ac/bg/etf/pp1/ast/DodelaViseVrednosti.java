// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class DodelaViseVrednosti extends Dodela {

    private Dodela Dodela;
    private String identifikator;
    private Vrednost Vrednost;

    public DodelaViseVrednosti (Dodela Dodela, String identifikator, Vrednost Vrednost) {
        this.Dodela=Dodela;
        if(Dodela!=null) Dodela.setParent(this);
        this.identifikator=identifikator;
        this.Vrednost=Vrednost;
        if(Vrednost!=null) Vrednost.setParent(this);
    }

    public Dodela getDodela() {
        return Dodela;
    }

    public void setDodela(Dodela Dodela) {
        this.Dodela=Dodela;
    }

    public String getIdentifikator() {
        return identifikator;
    }

    public void setIdentifikator(String identifikator) {
        this.identifikator=identifikator;
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
        if(Dodela!=null) Dodela.accept(visitor);
        if(Vrednost!=null) Vrednost.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Dodela!=null) Dodela.traverseTopDown(visitor);
        if(Vrednost!=null) Vrednost.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Dodela!=null) Dodela.traverseBottomUp(visitor);
        if(Vrednost!=null) Vrednost.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DodelaViseVrednosti(\n");

        if(Dodela!=null)
            buffer.append(Dodela.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+identifikator);
        buffer.append("\n");

        if(Vrednost!=null)
            buffer.append(Vrednost.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DodelaViseVrednosti]");
        return buffer.toString();
    }
}
