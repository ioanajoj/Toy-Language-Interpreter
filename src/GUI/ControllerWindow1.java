package GUI;

import Controller.Controller;
import Model.*;
import Model.Containers.*;
import Model.Expressions.*;
import Model.Statements.*;
import Repository.ListRepository;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerWindow1 {

    private ArrayList<String> stringSet = new ArrayList<String>();
    private Menu menu = new Menu();
    private Controller prg_controller = null;

    @FXML private ListView<String> programs_listView;
    @FXML private TextArea code_textArea;
    @FXML private Button run_button;
    @FXML private Label taskDetail;

    public ControllerWindow1() {

    }

    @FXML
    void initialize() {
        assert programs_listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'window1.fxml'.";

        /*
        Example 1
        v = 2;
        Print(v)
        */
        ListRepository repo1 = new ListRepository("logfile.txt");
        Controller ctr1 = new Controller(repo1);
        IStmt ex1 = new CompStmt(new AssignStmt("v",new ConstExpr(2)), new PrintStmt(new VarExpr("v")));
        PrgState prg1 = new PrgState("Example 1", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex1);
        repo1.addProgram(prg1);

        /*
        Example 2
        a = 2+3*5;
        b = a+1;
        Print(b);
        */
        ListRepository repo2 = new ListRepository("logfile.txt");
        Controller ctr2 = new Controller(repo2);
        ArithmExpr multiply_3_5 = new ArithmExpr('*', new ConstExpr(3), new ConstExpr(5));
        ArithmExpr add_2_3_5 = new ArithmExpr('+', new ConstExpr(2), multiply_3_5);
        AssignStmt assign_a = new AssignStmt("a", add_2_3_5);
        ArithmExpr add_to_b = new ArithmExpr('+', new VarExpr("a"), new ConstExpr(1));
        AssignStmt assign_b = new AssignStmt("b", add_to_b);
        CompStmt comp_2_1 = new CompStmt(assign_a, assign_b);
        PrintStmt print_2 = new PrintStmt(new VarExpr("b"));
        IStmt ex2 = new CompStmt(comp_2_1, print_2);
        PrgState prg2 = new PrgState("Example 2", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex2);
        repo2.addProgram(prg2);

        /*
        Example 3
        a = 2-2;
        (If a Then v = 2 Else v = 3);
        Print(v)
        */
        ListRepository repo3 = new ListRepository("logfile.txt");
        Controller ctr3 = new Controller(repo3);
        AssignStmt assign_a_3 = new AssignStmt("a", new ArithmExpr('-', new ConstExpr(2), new ConstExpr(2)));
        IfStmt if_stmt_3 = new IfStmt(new VarExpr("a"), new AssignStmt("v", new ConstExpr(2)), new AssignStmt("v", new ConstExpr(3)));
        PrintStmt print_stmt_3 = new PrintStmt(new VarExpr("v"));
        IStmt ex3  = new CompStmt(assign_a_3, new CompStmt(if_stmt_3, print_stmt_3));
        PrgState prg3 = new PrgState("Example 3", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex3);
        repo3.addProgram(prg3);

        /*
        Example 4
        Missing Variable Exception Example
        v = 2;
        print(a)
        */
        ListRepository repo4 = new ListRepository("logfile.txt");
        Controller ctr4 = new Controller(repo4);
        IStmt ex4 = new CompStmt(new AssignStmt("v", new ConstExpr(2)), new PrintStmt(new VarExpr("a")));
        PrgState prg4 = new PrgState("Missing Variable Exception", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex4);
        repo4.addProgram(prg4);

        /*
        Example 5
        Division By Zero Exception Example
        v = 2;
        a = 3/(v-2)
        print(a)
        */
        ListRepository repo5 = new ListRepository("logfile.txt");
        Controller ctr5 = new Controller(repo5);
        AssignStmt ass = new AssignStmt("v", new ConstExpr(2));
        AssignStmt ass2 = new AssignStmt("a", new ArithmExpr('/', new ConstExpr(3), new ArithmExpr('-', new VarExpr("v"), new ConstExpr(2))));
        PrintStmt print_5 = new PrintStmt(new VarExpr("a"));
        CompStmt comp_5_1 = new CompStmt(ass, ass2);
        IStmt ex5 = new CompStmt(comp_5_1, print_5);
        PrgState prg5 = new PrgState("Division By Zero Exception", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex5);
        repo5.addProgram(prg5);

        /*
        Example 6 -> Files
        openRFile(var_f,"test.in");
        readFile(var_f,var_c);print(var_c);
        (if var_c then readFile(var_f,var_c);print(var_c)
        else print(0));
        closeRFile(var_f)
         */
        ListRepository repo6 = new ListRepository("logfile.txt");
        Controller ctr6 = new Controller(repo6);
        OpenFile open = new OpenFile("var_f", "test.in");
        Expr fileExpr = new VarExpr("var_f");
        ReadFile read = new ReadFile(fileExpr, "var_c");
        VarExpr c_expr = new VarExpr("var_c");
        PrintStmt print = new PrintStmt(c_expr);
        CompStmt read_print = new CompStmt(read, print);
        CompStmt line12 = new CompStmt(open, read_print);
        IfStmt ifStmt = new IfStmt(c_expr, read_print, new PrintStmt(new ConstExpr(0)));
        CloseFile close = new CloseFile(fileExpr);
        CompStmt comp = new CompStmt(line12, ifStmt);
        IStmt ex6 = new CompStmt(comp, close);
        PrgState prg6 = new PrgState("Files Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex6);
        repo6.addProgram(prg6);

        /*
        Example 7 -> Files
        openRFile(var_f,"test.in");
        readFile(var_f+2,var_c);print(var_c);
        (if var_c then readFile(var_f,var_c);print(var_c)
        else print(0));
        closeRFile(var_f)
         */
        ListRepository repo7 = new ListRepository("logfile.txt");
        Controller ctr7 = new Controller(repo7);
        OpenFile open7 = new OpenFile("var_f", "test.in");
        Expr fileExpr7 = new ArithmExpr('+', new VarExpr("var_f"), new ConstExpr(2));
        ReadFile read7 = new ReadFile(fileExpr7, "var_c");
        VarExpr c_expr_7 = new VarExpr("var_c");
        PrintStmt print7 = new PrintStmt(c_expr_7);
        CompStmt read_print_7 = new CompStmt(read7, print7);
        CompStmt line12_7 = new CompStmt(open7, read_print_7);
        IfStmt ifStmt_7 = new IfStmt(c_expr_7, read_print_7, new PrintStmt(new ConstExpr(0)));
        CloseFile close_7 = new CloseFile(fileExpr7);
        CompStmt comp_7 = new CompStmt(line12_7, ifStmt_7);
        IStmt ex7 = new CompStmt(comp_7, close_7);
        PrgState prg7 = new PrgState("File Not Found Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex7);
        repo7.addProgram(prg7);

        /*
        Example 8 -> new Command
        v=10;new(v,20);new(a,22);print(v)
        v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
         */
        ListRepository repo8 = new ListRepository("logfile.txt");
        Controller ctr8 = new Controller(repo8);
        IStmt v = new AssignStmt("v", new ConstExpr(10));
        IStmt newSt = new NewStmt("v", new ConstExpr(20));
        IStmt newSt2 = new NewStmt("a", new ConstExpr(22));
        PrintStmt print8 = new PrintStmt(new VarExpr("v"));
        IStmt comp7_1 = new CompStmt(v, newSt);
        IStmt comp7_2 = new CompStmt(newSt2, print8);
        IStmt ex8 = new CompStmt(comp7_1, comp7_2);
        PrgState prg8 = new PrgState("New Command Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex8);
        repo8.addProgram(prg8);

        /*
        Example 9 -> new Command, readHeap
        v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
         */
        ListRepository repo9 = new ListRepository("logfile.txt");
        Controller ctr9 = new Controller(repo9);
        IStmt v_9 = new AssignStmt("v", new ConstExpr(10));
        IStmt newSt_9 = new NewStmt("v", new ConstExpr(20));
        IStmt newSt2_9 = new NewStmt("a", new ConstExpr(22));
        PrintStmt print9_1 = new PrintStmt(new ArithmExpr('+', new ConstExpr(100), new ReadHeap("v")));
        PrintStmt print9_2 = new PrintStmt(new ArithmExpr('+', new ConstExpr(100), new ReadHeap("a")));
        IStmt comp9_1 = new CompStmt(v_9, newSt_9);
        IStmt comp9_2 = new CompStmt(comp9_1, newSt2_9);
        IStmt comp9_3 = new CompStmt(print9_1, print9_2);
        IStmt ex9 = new CompStmt(comp9_2, comp9_3);
        PrgState prg9 = new PrgState("New Command Example 2", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex9);
        repo9.addProgram(prg9);

        /*
        Example 10 -> new Command, readHeap, writeHeap
        v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
         */
        ListRepository repo10 = new ListRepository("logfile.txt");
        Controller ctr10 = new Controller(repo10);
        IStmt v_10 = new AssignStmt("v", new ConstExpr(10));
        IStmt newSt_10 = new NewStmt("v", new ConstExpr(20));
        IStmt newSt2_10 = new NewStmt("a", new ConstExpr(22));
        IStmt write_a_10 = new WriteHeap("a", new ConstExpr(30));
        PrintStmt print10_1 = new PrintStmt(new VarExpr("a"));
        PrintStmt print10_2 = new PrintStmt(new ReadHeap("a"));
        IStmt ass_a_10 = new AssignStmt("a", new ConstExpr(0));
        IStmt comp10_1 = new CompStmt(v_10, newSt_10);
        IStmt comp10_2 = new CompStmt(comp10_1, newSt2_10);
        IStmt comp10_3 = new CompStmt(comp10_2, write_a_10);
        IStmt comp10_4 = new CompStmt(comp10_3, print10_1);
        IStmt comp10_5 = new CompStmt(print10_2, ass_a_10);
        IStmt ex10 = new CompStmt(comp10_4, comp10_5);
        PrgState prg10 = new PrgState("New Command Example 3", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex10);
        repo10.addProgram(prg10);

        /*
        Example 11
        v = 10 + (2<6);
        Print(v)
        */
        ListRepository repo11 = new ListRepository("logfile.txt");
        Controller ctr11 = new Controller(repo11);
        IStmt ex11 = new CompStmt(new AssignStmt("v",new ArithmExpr('+', new ConstExpr(10), new BooleanExpr(">", new ConstExpr(2), new ConstExpr(6)))), new PrintStmt(new VarExpr("v")));
        PrgState prg11 = new PrgState("Comparison Expression Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex11);
        repo11.addProgram(prg11);

        /*
        Example 12
        v = 6;
        while(v-4) {
            print(v)
            v = v-1
        }
        print(v)
        */
        ListRepository repo12 = new ListRepository("logfile.txt");
        Controller ctr12 = new Controller(repo12);
        IStmt inWhile12 = new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("v", new ArithmExpr('-', new VarExpr("v"), new ConstExpr(1))));
        IStmt whileSt12 = new WhileStmt(new ArithmExpr('-', new VarExpr("v"), new ConstExpr(4)), inWhile12);
        IStmt comp12 = new CompStmt(new AssignStmt("v", new ConstExpr(6)), whileSt12);
        IStmt ex12 = new CompStmt(comp12, new PrintStmt(new VarExpr("v")));
        PrgState prg12 = new PrgState("While Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex12);
        repo12.addProgram(prg12);

        /*
        Example 13 -> Infinite Loop Exception
        v = 6;
        while(v) {
            print(v)
        }
        */
        ListRepository repo13 = new ListRepository("logfile.txt");
        Controller ctr13 = new Controller(repo13);
        IStmt ex13 = new WhileStmt(new ConstExpr(6), new PrintStmt(new ConstExpr(3)));
        PrgState prg13 = new PrgState("Infinite Loop Exception", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex13);
        repo13.addProgram(prg13);

        /*
        Example 14 -> Opened Files
        openRFile(var_f,"test.in");
        openRFile(var_f2,"test.in");
         */
        ListRepository repo14 = new ListRepository("logfile.txt");
        Controller ctr14 = new Controller(repo14);
        OpenFile open14_1 = new OpenFile("var_f", "test.in");
        OpenFile open14_2 = new OpenFile("var_f2", "test2.in");
        IStmt ex14 = new CompStmt(open14_1, open14_2);
        PrgState prg14 = new PrgState("Open File Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex14);
        repo14.addProgram(prg14);

        /*
        Example 15 fork
        v=10;new(a,22);
        fork(wH(a,30);v=32;print(v);print(rH(a)));
        print(v);print(rH(a))
        */
        ListRepository repo15 = new ListRepository("logfile.txt");
        Controller ctr15 = new Controller(repo15);
        CompStmt beginStmt15 = new CompStmt(new AssignStmt("v",new ConstExpr(10)), new NewStmt("a", new ConstExpr(22)));
        CompStmt child1_15 = new CompStmt(new WriteHeap("a", new ConstExpr(30)), new AssignStmt("v", new ConstExpr(32)));
        CompStmt child15 = new CompStmt(child1_15, new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadHeap("a"))));
        ForkStmt fork15 = new ForkStmt(child15);
        CompStmt parent15 = new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadHeap("a")));
        CompStmt comp1_15 = new CompStmt(beginStmt15, fork15);
        IStmt ex15 = new CompStmt(comp1_15, parent15);
        PrgState prg15 = new PrgState("Fork Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex15);
        repo15.addProgram(prg15);

        /*
        Example 16 fork
        v=10;new(a,22);
        fork{wH(a,30);v=32;print(v);a=0};
        print(v);print(rH(a))
        */
        ListRepository repo16 = new ListRepository("logfile.txt");
        Controller ctr16 = new Controller(repo16);
        CompStmt beginStmt16 = new CompStmt(new AssignStmt("v",new ConstExpr(10)), new NewStmt("a", new ConstExpr(22)));
        CompStmt child1_16 = new CompStmt(new WriteHeap("a", new ConstExpr(30)), new AssignStmt("v", new ConstExpr(32)));
        CompStmt child16 = new CompStmt(child1_16, new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("a", new ConstExpr(0))));
        ForkStmt fork16 = new ForkStmt(child16);
        CompStmt parent16 = new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadHeap("a")));
        CompStmt comp1_16 = new CompStmt(beginStmt16, fork16);
        IStmt ex16 = new CompStmt(comp1_16, parent16);
        PrgState prg16 = new PrgState("Fork Example 2", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex16);
        repo16.addProgram(prg16);

        /* Examplu minune
        */
        ListRepository repo17 = new ListRepository("logfile.txt");
        Controller ctr17 = new Controller(repo17);

        NewStmt new_v1 = new NewStmt("v1", new ConstExpr(20));
        NewStmt new_v2 = new NewStmt("v2", new ConstExpr(30));
        NewLock newlock_x = new NewLock("x");

        // child 2
        Lock lock_x_1 = new Lock("x");
        ReadHeap rh_2_v1 = new ReadHeap("v1");
        WriteHeap wh_2_v1 = new WriteHeap("v1", new ArithmExpr('-', rh_2_v1, new ConstExpr(1)));
        Unlock unlock_x_1 = new Unlock("x");
        CompStmt comp_17_1 = new CompStmt(lock_x_1, wh_2_v1);
        CompStmt comp_17_2 = new CompStmt(comp_17_1, unlock_x_1);
        ForkStmt child_17_2= new ForkStmt(comp_17_2);

        // child 1
        Lock lock_x_2 = new Lock("x");
        ReadHeap rh_2_v1_2 = new ReadHeap("v1");
        WriteHeap wh_2_v1_2 = new WriteHeap("v1", new ArithmExpr('+', rh_2_v1_2, new ConstExpr(1)));
        Unlock unlock_x_2 = new Unlock("x");
        CompStmt comp_17_3 = new CompStmt(lock_x_2, wh_2_v1_2);
        CompStmt comp_17_4 = new CompStmt(comp_17_3, unlock_x_2);

        ForkStmt child_17_1 = new ForkStmt(new CompStmt(child_17_2, comp_17_4));

        // continuare parinte
        NewLock newlock_q = new NewLock("q");

        // child 4
        Lock lock_q_1 = new Lock("q");
        ReadHeap rh_2_v2_1 = new ReadHeap("v2");
        WriteHeap wh_2_v2_1 = new WriteHeap("v2", new ArithmExpr('+', rh_2_v2_1, new ConstExpr(5)));
        Unlock unlock_q_1 = new Unlock("q");
        CompStmt comp_17_5 = new CompStmt(lock_q_1, wh_2_v2_1);
        CompStmt comp_17_6 = new CompStmt(comp_17_5, unlock_q_1);
        ForkStmt child_17_4 = new ForkStmt(comp_17_6);

        // child 3
        AssignStmt assign_m = new AssignStmt("m", new ConstExpr(100));
        Lock lock_q_2 = new Lock("q");
        CompStmt lock_assign_m = new CompStmt(assign_m, lock_q_2);
        ReadHeap rh_2_v2_2 = new ReadHeap("v2");
        WriteHeap wh_2_v2_2 = new WriteHeap("v2", new ArithmExpr('+', rh_2_v2_2, new ConstExpr(1)));
        Unlock unlock_q_2 = new Unlock("q");
        CompStmt comp_17_7 = new CompStmt(lock_assign_m, wh_2_v2_2);
        CompStmt comp_17_8 = new CompStmt(comp_17_7, unlock_q_2);
        ForkStmt child_17_3 = new ForkStmt(new CompStmt(child_17_4, comp_17_8));

        // next stuff
        AssignStmt assign_z_1 = new AssignStmt("z", new ConstExpr(200));
        AssignStmt assign_z_2 = new AssignStmt("z", new ConstExpr(300));
        CompStmt comp_z_1 = new CompStmt(assign_z_1, assign_z_2);
        AssignStmt assign_z_3 = new AssignStmt("z", new ConstExpr(400));
        CompStmt comp_z_2 = new CompStmt(comp_z_1, assign_z_3);

        Lock lock_x_3 = new Lock("x");
        PrintStmt print_17_1 = new PrintStmt(new ReadHeap("v1"));
        Unlock unlock_x_3 = new Unlock("x");
        Lock lock_q_3 = new Lock("q");
        PrintStmt print_17_2 = new PrintStmt(new ReadHeap("v2"));
        Unlock unlock_q_3 = new Unlock("q");

        // summed up
        CompStmt comp_17_9 = new CompStmt(new_v1, new_v2);
        CompStmt comp_17_10 = new CompStmt(comp_17_9, newlock_x);
        CompStmt comp_17_11 = new CompStmt(comp_17_10, child_17_1);
        CompStmt comp_17_12 = new CompStmt(comp_17_11, newlock_q);
        CompStmt comp_17_13 = new CompStmt(comp_17_12, child_17_3);
        CompStmt comp_17_14 = new CompStmt(comp_17_13, comp_z_2);

        CompStmt comp_lock_x = new CompStmt(lock_x_3, print_17_1);
        CompStmt comp_unlock_x = new CompStmt(comp_lock_x, unlock_x_3);
        CompStmt comp_17_15 = new CompStmt(comp_17_14, comp_unlock_x);
        CompStmt comp_lock_y = new CompStmt(lock_q_3, print_17_2);
        CompStmt comp_unlock_q = new CompStmt(comp_lock_y, unlock_q_3);

        IStmt ex17 = new CompStmt(comp_17_15, comp_unlock_q);
        PrgState prg17 = new PrgState("Lock Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex17);
        repo17.addProgram(prg17);

        /* Example 18
        v=20;
        (for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
        print(v*10)
         */
        ListRepository repo18 = new ListRepository("logfile.txt");
        Controller ctr18 = new Controller(repo18);

        AssignStmt assign_v_18 = new AssignStmt("v", new ConstExpr(20));
        ForkStmt fork_18 = new ForkStmt(
                new CompStmt(new PrintStmt(new VarExpr("v")),
                        new AssignStmt("v", new ArithmExpr('+', new VarExpr("v"),
                                new ConstExpr(1)))));
        For forr = new For(new ConstExpr(0), new ConstExpr(3),
                new ArithmExpr('+', new VarExpr("v"),
                        new ConstExpr(1)), fork_18);
        PrintStmt print_18 = new PrintStmt(new ArithmExpr('*', new VarExpr("v"), new ConstExpr(10)));

        CompStmt comp_18_1 = new CompStmt(assign_v_18, forr);
        IStmt ex18 = new CompStmt(comp_18_1, print_18);

        PrgState prg18 = new PrgState("For Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex18);
        repo18.addProgram(prg18);

        /*
        Example Cyclic Barrier 19
        new(v1,2);new(v2,3);new(v3,4);newBarrier(cnt,rH(v2));
        fork(await(cnt);wh(v1,rh(v1)*10));print(rh(v1)));
        fork(await(cnt);wh(v2,rh(v2)*10));wh(v2,rh(v2)*10));print(rh(v2)));
        await(cnt);
        print(rH(v3))
         */
        ListRepository repo19 = new ListRepository("logfile.txt");
        Controller ctr19 = new Controller(repo19);

        NewStmt new_19_1 = new NewStmt("v1", new ConstExpr(2));
        NewStmt new_19_2 = new NewStmt("v2", new ConstExpr(3));
        NewStmt new_19_3 = new NewStmt("v3", new ConstExpr(4));
        NewBarrier new_b_19 = new NewBarrier("cnt", new ReadHeap("v2"));

        Await await_19_1 = new Await("cnt");
        WriteHeap writeHeap_19_1 = new WriteHeap("v1", new ArithmExpr('*', new ReadHeap("v1"), new ConstExpr(10)));
        PrintStmt print_19_1 = new PrintStmt(new ReadHeap("v1"));
        CompStmt comp_19_fork_1 = new CompStmt(await_19_1, writeHeap_19_1);
        ForkStmt fork_19_1 = new ForkStmt(new CompStmt(comp_19_fork_1, print_19_1));

        Await await_19_2 = new Await("cnt");
        WriteHeap writeHeap_19_2 = new WriteHeap("v2", new ArithmExpr('*', new ReadHeap("v2"), new ConstExpr(10)));
        WriteHeap writeHeap_19_3 = new WriteHeap("v2", new ArithmExpr('*', new ReadHeap("v2"), new ConstExpr(10)));
        PrintStmt print_19_2 = new PrintStmt(new ReadHeap("v2"));
        CompStmt comp_19_fork_2 = new CompStmt(await_19_2, writeHeap_19_2);
        CompStmt comp_19_fork_3 = new CompStmt(comp_19_fork_2, writeHeap_19_3);
        ForkStmt fork_19_2 = new ForkStmt(new CompStmt(comp_19_fork_3, print_19_2));

        Await await_19_3 = new Await("cnt");
        PrintStmt print_19_3 = new PrintStmt(new ReadHeap("v3"));

        CompStmt comp_19_1 = new CompStmt(new_19_1, new_19_2);
        CompStmt comp_19_2 = new CompStmt(new_19_3, new_b_19);
        CompStmt comp_19_3 = new CompStmt(comp_19_1, comp_19_2);
        CompStmt comp_19_4 = new CompStmt(fork_19_1, fork_19_2);
        CompStmt comp_19_5 = new CompStmt(comp_19_3, comp_19_4);
        CompStmt comp_19_6 = new CompStmt(comp_19_5, await_19_3);
        IStmt ex19 = new CompStmt(comp_19_6, print_19_3);

        PrgState prg19 = new PrgState("Cyclic Barrier Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex19);
        repo19.addProgram(prg19);

        /*
        a=1;b=2;c=5;
        switch(a*10)
         (case (b*c) print(a);print(b))
         (case (10) print(100);print(200))
         (default print(300));
        print(300)
         */
        ListRepository repo20 = new ListRepository("logfile.txt");
        Controller ctr20 = new Controller(repo20);
        AssignStmt a_20_1 = new AssignStmt("a", new ConstExpr(1));
        AssignStmt a_20_2 = new AssignStmt("b", new ConstExpr(2));
        AssignStmt a_20_3 = new AssignStmt("c", new ConstExpr(5));
        Expr expr_1 = new ArithmExpr('*', new VarExpr("a"), new ConstExpr(10));
        Expr expr_2 = new ArithmExpr('*', new VarExpr("b"), new VarExpr("c"));
        Expr expr_3 = new ConstExpr(10);
        IStmt stmt_1 = new CompStmt(new PrintStmt(new VarExpr("a")), new PrintStmt(new VarExpr("b")));
        IStmt stmt_2 = new CompStmt(new PrintStmt(new ConstExpr(100)), new PrintStmt(new ConstExpr(200)));
        IStmt stmt_3 = new PrintStmt(new ConstExpr(300));
        Switch switch_20 = new Switch(expr_1, expr_2, stmt_1, expr_3, stmt_2, stmt_3);
        PrintStmt print_20 = new PrintStmt(new ConstExpr(300));

        CompStmt comp_20_1 = new CompStmt(a_20_1, a_20_2);
        CompStmt comp_20_2 = new CompStmt(a_20_3, switch_20);
        CompStmt comp_20_3 = new CompStmt(comp_20_1, comp_20_2);
        IStmt ex20 = new CompStmt(comp_20_3, print_20);
        PrgState prg20 = new PrgState("Switch Example", new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new Heap(), new LockTable(), new CyclicBarrier(), ex20);
        repo20.addProgram(prg20);

        menu.addCommand(new RunExampleCommand(prg1.getName(), ex1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand(prg2.getName(), ex2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand(prg3.getName(), ex3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand(prg4.getName(), ex4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand(prg5.getName(), ex5.toString(), ctr5));
        menu.addCommand(new RunExampleCommand(prg6.getName(), ex6.toString(), ctr6));
        menu.addCommand(new RunExampleCommand(prg7.getName(), ex7.toString(), ctr7));
        menu.addCommand(new RunExampleCommand(prg8.getName(), ex8.toString(), ctr8));
        menu.addCommand(new RunExampleCommand(prg9.getName(), ex9.toString(), ctr9));
        menu.addCommand(new RunExampleCommand(prg10.getName(), ex10.toString(), ctr10));
        menu.addCommand(new RunExampleCommand(prg11.getName(), ex11.toString(), ctr11));
        menu.addCommand(new RunExampleCommand(prg12.getName(), ex12.toString(), ctr12));
        menu.addCommand(new RunExampleCommand(prg13.getName(), ex13.toString(), ctr13));
        menu.addCommand(new RunExampleCommand(prg14.getName(), ex14.toString(), ctr14));
        menu.addCommand(new RunExampleCommand(prg15.getName(), ex15.toString(), ctr15));
        menu.addCommand(new RunExampleCommand(prg16.getName(), ex16.toString(), ctr16));
        menu.addCommand(new RunExampleCommand(prg17.getName(), ex17.toString(), ctr17));
        menu.addCommand(new RunExampleCommand(prg18.getName(), ex18.toString(), ctr18));
        menu.addCommand(new RunExampleCommand(prg19.getName(), ex19.toString(), ctr19));
        menu.addCommand(new RunExampleCommand(prg20.getName(), ex20.toString(), ctr20));

        stringSet.addAll(menu.getDescriptions());

        ObservableList<String> programsList = FXCollections.observableArrayList(stringSet);
        programs_listView.getItems().addAll(programsList);

        programs_listView.getSelectionModel().selectedItemProperty().addListener(this::exampleChanged);

        run_button.setOnAction(event -> runButtonPushed());
    }

    private void exampleChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String newText = newValue == null ? "null" : newValue;
        String newDescription = menu.getCommand(newText).getDescription();
        code_textArea.setText(newDescription);
        prg_controller = menu.getCommand(newText).getController();
        taskDetail.setText("");
    }

    private void runButtonPushed() {
        if (prg_controller == null)
            taskDetail.setText("Select a program to run.");
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("window2.fxml"));
            try {
                Parent root = loader.load();
                ControllerWindow2 controller = loader.<ControllerWindow2>getController();
                controller.setData(prg_controller);

                Stage stage = new Stage();
                stage.setTitle("Program Viewer");
                stage.setScene(new Scene(root, 730, 600));
                stage.show();

                // set close handler
                stage.setOnCloseRequest(event -> prg_controller.endEvalGUI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
