//package CUI;
//
//import Controller.Controller;
//import Model.*;
//import Model.Containers.*;
//import Model.Expressions.ConstExpr;
//import Model.Expressions.VarExpr;
//import Model.Statements.AssignStmt;
//import Model.Statements.CompStmt;
//import Model.Statements.IStmt;
//import Model.Statements.PrintStmt;
//import Repository.ListRepository;
//
//public class Interpreter {
//    public static void main(String[] args) {
//        /*
//        Example 1
//        v = 2;
//        Print(v)
//        */
//        ListRepository repo1 = new ListRepository("logfile.txt");
//        Controller ctr1 = new Controller(repo1);
//        IStmt ex1 = new CompStmt(new AssignStmt("v",new ConstExpr(2)), new PrintStmt(new VarExpr("v")));
//        PrgState prg1 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex1);
//        repo1.addProgram(prg1);
//
//        /*
//        Example 2
//        a = 2+3*5;
//        b = a+1;
//        Print(b);
//        */
//        ListRepository repo2 = new ListRepository("logfile.txt");
//        Controller ctr2 = new Controller(repo2);
//        ArithmExpr multiply_3_5 = new ArithmExpr('*', new ConstExpr(3), new ConstExpr(5));
//        ArithmExpr add_2_3_5 = new ArithmExpr('+', new ConstExpr(2), multiply_3_5);
//        AssignStmt assign_a = new AssignStmt("a", add_2_3_5);
//        ArithmExpr add_to_b = new ArithmExpr('+', new VarExpr("a"), new ConstExpr(1));
//        AssignStmt assign_b = new AssignStmt("b", add_to_b);
//        CompStmt comp_2_1 = new CompStmt(assign_a, assign_b);
//        PrintStmt print_2 = new PrintStmt(new VarExpr("b"));
//        IStmt ex2 = new CompStmt(comp_2_1, print_2);
//        PrgState prg2 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex2);
//        repo2.addProgram(prg2);
//
//        /*
//        Example 3
//        a = 2-2;
//        (If a Then v = 2 Else v = 3);
//        Print(v)
//        */
//        ListRepository repo3 = new ListRepository("logfile.txt");
//        Controller ctr3 = new Controller(repo3);
//        AssignStmt assign_a_3 = new AssignStmt("a", new ArithmExpr('-', new ConstExpr(2), new ConstExpr(2)));
//        IfStmt if_stmt_3 = new IfStmt(new VarExpr("a"), new AssignStmt("v", new ConstExpr(2)), new AssignStmt("v", new ConstExpr(3)));
//        PrintStmt print_stmt_3 = new PrintStmt(new VarExpr("v"));
//        IStmt ex3  = new CompStmt(assign_a_3, new CompStmt(if_stmt_3, print_stmt_3));
//        PrgState prg3 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex3);
//        repo3.addProgram(prg3);
//
//        /*
//        Example 4
//        Missing Variable Exception Example
//        v = 2;
//        print(a)
//        */
//        ListRepository repo4 = new ListRepository("logfile.txt");
//        Controller ctr4 = new Controller(repo4);
//        IStmt ex4 = new CompStmt(new AssignStmt("v", new ConstExpr(2)), new PrintStmt(new VarExpr("a")));
//        PrgState prg4 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex4);
//        repo4.addProgram(prg4);
//
//        /*
//        Example 5
//        Division By Zero Exception Example
//        v = 2;
//        a = 3/(v-2)
//        print(a)
//        */
//        ListRepository repo5 = new ListRepository("logfile.txt");
//        Controller ctr5 = new Controller(repo5);
//        AssignStmt ass = new AssignStmt("v", new ConstExpr(2));
//        AssignStmt ass2 = new AssignStmt("a", new ArithmExpr('/', new ConstExpr(3), new ArithmExpr('-', new VarExpr("v"), new ConstExpr(2))));
//        PrintStmt print_5 = new PrintStmt(new VarExpr("a"));
//        CompStmt comp_5_1 = new CompStmt(ass, ass2);
//        IStmt ex5 = new CompStmt(comp_5_1, print_5);
//        PrgState prg5 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex5);
//        repo5.addProgram(prg5);
//
//        /*
//        Example 6 -> Files
//        openRFile(var_f,"test.in");
//        readFile(var_f,var_c);print(var_c);
//        (if var_c then readFile(var_f,var_c);print(var_c)
//        else print(0));
//        closeRFile(var_f)
//         */
//        ListRepository repo6 = new ListRepository("logfile.txt");
//        Controller ctr6 = new Controller(repo6);
//        OpenFile open = new OpenFile("var_f", "test.in");
//        Expr fileExpr = new VarExpr("var_f");
//        ReadFile read = new ReadFile(fileExpr, "var_c");
//        VarExpr c_expr = new VarExpr("var_c");
//        PrintStmt print = new PrintStmt(c_expr);
//        CompStmt read_print = new CompStmt(read, print);
//        CompStmt line12 = new CompStmt(open, read_print);
//        IfStmt ifStmt = new IfStmt(c_expr, read_print, new PrintStmt(new ConstExpr(0)));
//        CloseFile close = new CloseFile(fileExpr);
//        CompStmt comp = new CompStmt(line12, ifStmt);
//        IStmt ex6 = new CompStmt(comp, close);
//        PrgState prg6 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex6);
//        repo6.addProgram(prg6);
//
//        /*
//        Example 7 -> Files
//        openRFile(var_f,"test.in");
//        readFile(var_f+2,var_c);print(var_c);
//        (if var_c then readFile(var_f,var_c);print(var_c)
//        else print(0));
//        closeRFile(var_f)
//         */
//        ListRepository repo7 = new ListRepository("logfile.txt");
//        Controller ctr7 = new Controller(repo7);
//        OpenFile open7 = new OpenFile("var_f", "test.in");
//        Expr fileExpr7 = new ArithmExpr('+', new VarExpr("var_f"), new ConstExpr(2));
//        ReadFile read7 = new ReadFile(fileExpr7, "var_c");
//        VarExpr c_expr_7 = new VarExpr("var_c");
//        PrintStmt print7 = new PrintStmt(c_expr_7);
//        CompStmt read_print_7 = new CompStmt(read7, print7);
//        CompStmt line12_7 = new CompStmt(open7, read_print_7);
//        IfStmt ifStmt_7 = new IfStmt(c_expr, read_print_7, new PrintStmt(new ConstExpr(0)));
//        CloseFile close_7 = new CloseFile(fileExpr);
//        CompStmt comp_7 = new CompStmt(line12_7, ifStmt_7);
//        IStmt ex7 = new CompStmt(comp_7, close_7);
//        PrgState prg7 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex7);
//        repo7.addProgram(prg7);
//
//        /*
//        Example 8 -> new Command
//        v=10;new(v,20);new(a,22);print(v)
//        v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
//         */
//        ListRepository repo8 = new ListRepository("logfile.txt");
//        Controller ctr8 = new Controller(repo8);
//        IStmt v = new AssignStmt("v", new ConstExpr(10));
//        IStmt newSt = new NewStmt("v", new ConstExpr(20));
//        IStmt newSt2 = new NewStmt("a", new ConstExpr(22));
//        PrintStmt print8 = new PrintStmt(new VarExpr("v"));
//        IStmt comp7_1 = new CompStmt(v, newSt);
//        IStmt comp7_2 = new CompStmt(newSt2, print8);
//        IStmt ex8 = new CompStmt(comp7_1, comp7_2);
//        PrgState prg8 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex8);
//        repo8.addProgram(prg8);
//
//        /*
//        Example 9 -> new Command, readHeap
//        v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
//         */
//        ListRepository repo9 = new ListRepository("logfile.txt");
//        Controller ctr9 = new Controller(repo9);
//        IStmt v_9 = new AssignStmt("v", new ConstExpr(10));
//        IStmt newSt_9 = new NewStmt("v", new ConstExpr(20));
//        IStmt newSt2_9 = new NewStmt("a", new ConstExpr(22));
//        PrintStmt print9_1 = new PrintStmt(new ArithmExpr('+', new ConstExpr(100), new ReadHeap("v")));
//        PrintStmt print9_2 = new PrintStmt(new ArithmExpr('+', new ConstExpr(100), new ReadHeap("a")));
//        IStmt comp9_1 = new CompStmt(v_9, newSt_9);
//        IStmt comp9_2 = new CompStmt(comp9_1, newSt2_9);
//        IStmt comp9_3 = new CompStmt(print9_1, print9_2);
//        IStmt ex9 = new CompStmt(comp9_2, comp9_3);
//        PrgState prg9 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex9);
//        repo9.addProgram(prg9);
//
//        /*
//        Example 10 -> new Command, readHeap, writeHeap
//        v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
//         */
//        ListRepository repo10 = new ListRepository("logfile.txt");
//        Controller ctr10 = new Controller(repo10);
//        IStmt v_10 = new AssignStmt("v", new ConstExpr(10));
//        IStmt newSt_10 = new NewStmt("v", new ConstExpr(20));
//        IStmt newSt2_10 = new NewStmt("a", new ConstExpr(22));
//        IStmt write_a_10 = new WriteHeap("a", new ConstExpr(30));
//        PrintStmt print10_1 = new PrintStmt(new VarExpr("a"));
//        PrintStmt print10_2 = new PrintStmt(new ReadHeap("a"));
//        IStmt ass_a_10 = new AssignStmt("a", new ConstExpr(0));
//        IStmt comp10_1 = new CompStmt(v_10, newSt_10);
//        IStmt comp10_2 = new CompStmt(comp10_1, newSt2_10);
//        IStmt comp10_3 = new CompStmt(comp10_2, write_a_10);
//        IStmt comp10_4 = new CompStmt(comp10_3, print10_1);
//        IStmt comp10_5 = new CompStmt(print10_2, ass_a_10);
//        IStmt ex10 = new CompStmt(comp10_4, comp10_5);
//        PrgState prg10 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex10);
//        repo10.addProgram(prg10);
//
//        /*
//        Example 11
//        v = 10 + (2<6);
//        Print(v)
//        */
//        ListRepository repo11 = new ListRepository("logfile.txt");
//        Controller ctr11 = new Controller(repo11);
//        IStmt ex11 = new CompStmt(new AssignStmt("v",new ArithmExpr('+', new ConstExpr(10), new BooleanExpr(">", new ConstExpr(2), new ConstExpr(6)))), new PrintStmt(new VarExpr("v")));
//        PrgState prg11 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex11);
//        repo11.addProgram(prg11);
//
//        /*
//        Example 12
//        v = 6;
//        while(v-4) {
//            print(v)
//            v = v-1
//        }
//        print(v)
//        */
//        ListRepository repo12 = new ListRepository("logfile.txt");
//        Controller ctr12 = new Controller(repo12);
//        IStmt inWhile12 = new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("v", new ArithmExpr('-', new VarExpr("v"), new ConstExpr(1))));
//        IStmt whileSt12 = new WhileStmt(new ArithmExpr('-', new VarExpr("v"), new ConstExpr(4)), inWhile12);
//        IStmt comp12 = new CompStmt(new AssignStmt("v", new ConstExpr(6)), whileSt12);
//        IStmt ex12 = new CompStmt(comp12, new PrintStmt(new VarExpr("v")));
//        PrgState prg12 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex12);
//        repo12.addProgram(prg12);
//
//        /*
//        Example 13 -> Infinite Loop Exception
//        v = 6;
//        while(v) {
//            print(v)
//        }
//        */
//        ListRepository repo13 = new ListRepository("logfile.txt");
//        Controller ctr13 = new Controller(repo13);
//        IStmt ex13 = new WhileStmt(new ConstExpr(6), new PrintStmt(new ConstExpr(3)));
//        PrgState prg13 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex13);
//        repo13.addProgram(prg13);
//
//        /*
//        Example 14 -> Opened Files
//        openRFile(var_f,"test.in");
//        openRFile(var_f2,"test.in");
//         */
//        ListRepository repo14 = new ListRepository("logfile.txt");
//        Controller ctr14 = new Controller(repo14);
//        OpenFile open14_1 = new OpenFile("var_f", "test.in");
//        OpenFile open14_2 = new OpenFile("var_f2", "test2.in");
//        IStmt ex14 = new CompStmt(open14_1, open14_2);
//        PrgState prg14 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex14);
//        repo14.addProgram(prg14);
//
//        /*
//        Example 15 fork
//        v=10;new(a,22);
//        fork(wH(a,30);v=32;print(v);print(rH(a)));
//        print(v);print(rH(a))
//        */
//        ListRepository repo15 = new ListRepository("logfile.txt");
//        Controller ctr15 = new Controller(repo15);
//        CompStmt beginStmt15 = new CompStmt(new AssignStmt("v",new ConstExpr(10)), new NewStmt("a", new ConstExpr(22)));
//        CompStmt child1_15 = new CompStmt(new WriteHeap("a", new ConstExpr(30)), new AssignStmt("v", new ConstExpr(32)));
//        CompStmt child15 = new CompStmt(child1_15, new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadHeap("a"))));
//        ForkStmt fork15 = new ForkStmt(child15);
//        CompStmt parent15 = new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadHeap("a")));
//        CompStmt comp1_15 = new CompStmt(beginStmt15, fork15);
//        IStmt ex15 = new CompStmt(comp1_15, parent15);
//        PrgState prg15 = new PrgState(new MStack<>(), new MDictionary<>(), new MList<>(), new FileTable(), new Heap(), ex15);
//        repo15.addProgram(prg15);
//
//        /* ============================================= */
//
//        TextMenu textMenu = new TextMenu();
//        textMenu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
//        textMenu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
//        textMenu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
//        textMenu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
//        textMenu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
//        textMenu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
//        textMenu.addCommand(new RunExampleCommand("7", ex7.toString(), ctr7));
//        textMenu.addCommand(new RunExampleCommand("8", ex8.toString(), ctr8));
//        textMenu.addCommand(new RunExampleCommand("9", ex9.toString(), ctr9));
//        textMenu.addCommand(new RunExampleCommand("10", ex10.toString(), ctr10));
//        textMenu.addCommand(new RunExampleCommand("11", ex11.toString(), ctr11));
//        textMenu.addCommand(new RunExampleCommand("12", ex12.toString(), ctr12));
//        textMenu.addCommand(new RunExampleCommand("13", ex13.toString(), ctr13));
//        textMenu.addCommand(new RunExampleCommand("14", ex14.toString(), ctr14));
//        textMenu.addCommand(new RunExampleCommand("15", ex15.toString(), ctr15));
//        textMenu.show();
//    }
//}
