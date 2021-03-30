// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class Number_Const extends Vrednost {

    private Integer broj;

    public Number_Const (Integer broj) {
        this.broj=broj;
    }

    public Integer getBroj() {
        return broj;
    }

    public void setBroj(Integer broj) {
        this.broj=broj;
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
        buffer.append("Number_Const(\n");

        buffer.append(" "+tab+broj);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Number_Const]");
        return buffer.toString();
    }
}
