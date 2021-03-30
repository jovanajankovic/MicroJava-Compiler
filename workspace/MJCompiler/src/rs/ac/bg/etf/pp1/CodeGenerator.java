package rs.ac.bg.etf.pp1;

import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {
	
	private int varCount;
	private int paramCnt;
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	/****************************************************************************************/
	/*** ZA ISPIS RADI PROVERE! ***/
	Logger log = Logger.getLogger(getClass());
	private Stack<Integer> adresaPre	= new Stack<Integer>();
	private Stack<Integer> adresaPosle	= new Stack<Integer>();
	
	public void report_error(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	} 
	/***************************************************************************************/
	/*** METODA MAIN - ulazak u metodu, postavljanje pc, izlazak iz metode ***/
	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(MethodDeclName MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getNazivMetode())) {
			mainPc = Code.pc;
		}
		
		MethodTypeName.obj.setAdr(Code.pc);
		
		SyntaxNode methodNode = MethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	/***************************************************************************************/
	/*** DODELA VREDNOSTI ZA PROMENLJIVE/NIZOVE ***/
	public void visit(FactorNumber number) {
		Code.loadConst(number.getNumber());
	}

	public void visit(FactorChar karakter) {
		Code.loadConst(karakter.getKarakter());	
	}
	
	public void visit(FactorBool boolean1) {
		Code.loadConst(boolean1.getB1());	
	}
	
	public void visit(Assign_Designator_Klasa assign) {
		//na osnovu Obj.Var ili Obj.Elem zakljucuje da li da radi obican store, putstatic ili astore
		Code.store(assign.getDesignator().obj);
	}
	
	//za sada mi ne treba ovaj deo jer u njegovom parentu 
	//stavljam na stek
	public void visit(DesignatorIdentifikator ident) {
		
	}
	
	public void visit(DesignatorArrayName name) {
		Code.load(name.obj);
	}
	
	//sluzi za elemente nizova!!!
	public void visit(DesignatorArray array) {
	
	}
	public void visit(FactorDesignator designator) {
		//ovo stavlja u tabelu simbola ili obicnu promenljivu
		//ili stavlja element niza koji ima kind = Obj.Elem.
		//Taj deo da je Obj.Elem sam postavila u Semantickoj analizi kada sam obilazila element niza
		Code.load(designator.getDesignator().obj);
	}
	
	
	public void visit(FactorNewArray array) {
		Code.put(Code.newarray);
		if (array.getType().struct == Tab.charType) {
			Code.put(0); 
        } else { 
			Code.put(1);
        } 
		//report_info("Napravio novi niz" , null);
	}
	
	/***************************************************************************************/
	/*** INKREMENTIRANJE/DEKREMENTIRANJE ***/
	//INKREMENTIRANJE VREDNOSTI - obicna promenljiva i element niza
	public void visit(Increment inc) {
			if (inc.getDesignator().obj.getKind() == Obj.Elem) {
				Code.put(Code.dup2);
			}
			Code.load(inc.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(inc.getDesignator().obj);
	}
	
	//DEKREMENTIRANJE VREDNOSTI - obicna promenljiva i element niza
	public void visit(Decrement dec) {
		if (dec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(dec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(dec.getDesignator().obj);
	}
	
	/***************************************************************************************/
	/*** OPERACIJE READ I PRINT ***/
	public void visit(ReadStatement read) {	
		if(read.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(read.getDesignator().obj);
	}
	
		public void visit(PrintStatement print) {
			if(print.getExpr().struct == Tab.intType || print.getExpr().struct.getKind() == Struct.Bool) {
				//na onom tutorijalu je profesor stavio 5 za const
				Code.loadConst(5);
				Code.put(Code.print);
			} else if(print.getExpr().struct == Tab.charType) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
		}
		
		public void visit(PrintStatementComma print) {
			if(print.getExpr().struct == Tab.intType || print.getExpr().struct.getKind() == Struct.Bool) {
				Code.loadConst(print.getNum());
				Code.put(Code.print);
			} else if(print.getExpr().struct == Tab.charType) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
		}
		
	/***************************************************************************************/
	/*** OPERACIJE SABIRANJA/ODUZIMANJA ***/
	public void visit(AddoptListaViseOperacija add) {
		if(add.getAddop().obj.getName().equals("plus")) Code.put(Code.add);
		else Code.put(Code.sub);
	}
	
	public void visit(AddoptListaJednaOperacija add) {
		if(add.getAddop().obj.getName().equals("plus")) Code.put(Code.add);
		else Code.put(Code.sub);
	}
	
	public void visit(PlusOperacija plus) {
		plus.obj = new Obj(0, "plus", null);
	}
	
	public void visit(MinusOperacija minus) {
		minus.obj = new Obj(0, "minus", null);
	}
	
	//ukoliko sam dosla po grani gde je MINUS Term ili MINUS Term AddopList
	public void visit(Term_0 term) {
		if(term.getParent().getClass() == MinusExpr.class || term.getParent().getClass() == MinusExprLista.class) {
			Code.put(Code.neg);
		}
	}
	
	/****************************************************************************************/
	/*** OPERACIJE MNOZENJA/DELJENJA/MOD ***/
	
	public void visit(MulopFactor mulop) {
		if(mulop.getMulop().obj.getName().equals("mnozenje")) Code.put(Code.mul);
		else if(mulop.getMulop().obj.getName().equals("deljenje")) Code.put(Code.div);
		else Code.put(Code.rem);
	}
	
	public void visit(MulopFactorLista mulop) {
		if(mulop.getMulop().obj.getName().equals("mnozenje")) Code.put(Code.mul);
		else if(mulop.getMulop().obj.getName().equals("deljenje")) Code.put(Code.div);
		else Code.put(Code.rem);
	}
	
	public void visit(MulOperacija mul) {
		mul.obj = new Obj(0, "mnozenje", null);
	}
	
	public void visit(DivOperacija div) {
		div.obj = new Obj(0, "deljenje", null);
	}
	
	public void visit(PercentageOperacija per) {
		per.obj = new Obj(0, "mod", null);
	}
	
	public void visit(ViseTermova term) {
		if(term.getParent().getClass() == MinusExpr.class || term.getParent().getClass() == MinusExprLista.class) {
			Code.put(Code.neg);
		}
	}
	/****************************************************************************************/
	
	public void visit(EqOperacija op) {
		op.obj = new Obj(Code.eq, "jednako", null);
	}
	  
	public void visit(NeOperacija op) {
	    op.obj = new Obj(Code.ne, "razlicito", null);
	}
	  
	public void visit(GtOperacija op) {
		op.obj = new Obj(Code.gt, "vece", null);
	}
	  
	public void visit(Ltperacija op) {
		op.obj = new Obj(Code.lt, "manje", null);
	}
	  
	public void visit(LeOperacija op) {
		op.obj = new Obj(Code.le, "manjejednako", null);
	}
	  
	public void visit(GeOperacija op) {
		op.obj = new Obj(Code.ge, "vecejendako", null);
	}
	
	public void visit(CondFactRelation CondFactRelation) {
		// a > 3
		int op = CondFactRelation.getRelop().obj.getKind();
		Code.putFalseJump(op, 0);
		adresaPre.push(Code.pc - 2); // adresa adrese skoka
	}

	
	public void visit(CondFactBoolean CondFactBoolean) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		adresaPre.push(Code.pc - 2); // adresa adrese skoka
	}
	
	public void visit(DveTacke dveTacke) {
		Code.putJump(0);
		adresaPosle.push(Code.pc - 2);
		Code.fixup(adresaPre.pop());
	}
	
	public void visit(Ternarni_operator to) {
		Code.fixup(adresaPosle.pop());
	}

/*	@Override
	public void visit(VarDecl VarDecl) {
		varCount++;
	}

	@Override
	public void visit(FormalParamDecl FormalParam) {
		paramCnt++;
	}	
	
/*
	/*@Override
	public void visit(Const Const) {
		Code.load(new Obj(Obj.Con, "$", Const.struct, Const.getN1(), 0));
	}*/
	
	/*@Override
	public void visit(Designator Designator) {
		SyntaxNode parent = Designator.getParent();
		if (Assignment.class != parent.getClass() && FuncCall.class != parent.getClass()) {
			Code.load(Designator.obj);
		}
	}*/
	
/*
 * 
 * @Override
	public void visit(MethodTypeName MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		MethodTypeName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = MethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	@Override
	public void visit(VarDecl VarDecl) {
		varCount++;
	}

	@Override
	public void visit(FormalParamDecl FormalParam) {
		paramCnt++;
	}	
	
	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnExpr ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnNoExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Assignment Assignment) {
		Code.store(Assignment.getDesignator().obj);
	}
	
	@Override
	public void visit(Const Const) {
		Code.load(new Obj(Obj.Con, "$", Const.struct, Const.getN1(), 0));
	}
	
	@Override
	public void visit(Designator Designator) {
		SyntaxNode parent = Designator.getParent();
		if (Assignment.class != parent.getClass() && FuncCall.class != parent.getClass()) {
			Code.load(Designator.obj);
		}
	}
	
	@Override
	public void visit(FuncCall FuncCall) {
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc; 
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(PrintStmt PrintStmt) {
		Code.put(Code.const_5);
		Code.put(Code.print);
	}
	
	@Override
	public void visit(AddExpr AddExpr) {
		Code.put(Code.add);
	}
 * 
 * 
 * */
	
}
