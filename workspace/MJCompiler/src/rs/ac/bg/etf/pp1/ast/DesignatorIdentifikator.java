// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentifikator extends Designator {

    private String imePromenljive;

    public DesignatorIdentifikator (String imePromenljive) {
        this.imePromenljive=imePromenljive;
    }

    public String getImePromenljive() {
        return imePromenljive;
    }

    public void setImePromenljive(String imePromenljive) {
        this.imePromenljive=imePromenljive;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIdentifikator(\n");

        buffer.append(" "+tab+imePromenljive);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentifikator]");
        return buffer.toString();
    }
}
