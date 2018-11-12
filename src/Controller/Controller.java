package Controller;

import Model.*;
import Repository.IRepository;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    /*
        PRINT FLAG for compound statements!!
    */

    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    private HashMap<Integer, Integer> ConservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer, Integer> heapMemory) {
        Map<Integer, Integer> map = heapMemory.entrySet().stream()
                .filter(e->symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for(Integer key : map.keySet())
            hashMap.put(key, map.get(key));
        return hashMap;
    }

    private void displayState(PrgState state) {
        System.out.println("State:");
        System.out.println("Execution Stack:");
        MyIStack<IStmt> exeStack = state.getExeStack();
        if(!exeStack.toString().equals(""))
            System.out.println(exeStack.toString());

        System.out.println("Symbol Table:");
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        System.out.println(symTable.toString());

        System.out.println("Output:");
        MyIList<Integer> output = state.getOut();
        System.out.println(output.toString());

        System.out.println("FileTable:");
        IDictionaryWithoutKey<Integer, Pair<String, BufferedReader>> fileTable = state.getFileTable();
        System.out.println(fileTable.toString());
        System.out.println();

        System.out.println("Heap Memory:");
        IDictionaryWithoutKey<Integer, Integer> heapMemory = state.getHeapMemory();
        System.out.println(heapMemory.toString());
        System.out.println();
    }

    private PrgState oneStepEval(PrgState state) throws MissingVariableException, DivisionByZeroException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    } //return used for later assignments

    public void allStepEval() throws MissingVariableException, DivisionByZeroException, IOException {
        PrgState prg = repo.getCurrentPrg();
        repo.clearFile();
        int index = repo.getCurrentIndex();
        while(!prg.getExeStack().isEmpty()) {
            oneStepEval(prg);
            prg.getHeapMemory().setContent(ConservativeGarbageCollector(
                    prg.getSymTable().values(),
                    prg.getHeapMemory().getContent()));
            repo.logPrgState(index);
            displayState(prg);
        }
    }
}
