// generated with ast extension for cup
// version 0.8
// 18/0/2021 20:48:49


package rs.ac.bg.etf.pp1.ast;

public class ErrorStmt_iskaz_dodele extends Assign_Designator {

    public ErrorStmt_iskaz_dodele () {
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
        buffer.append("ErrorStmt_iskaz_dodele(\n");

        buffer.append(tab);
        buffer.append(") [ErrorStmt_iskaz_dodele]");
        return buffer.toString();
    }
}
