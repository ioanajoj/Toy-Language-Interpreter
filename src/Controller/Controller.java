package Controller;

import Model.*;
import Repository.IRepository;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    // de ce in controller?
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
        System.out.println("*======================*");
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

    private PrgState oneStepEval(PrgState state) throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }   //return used for later assignments

    public void allStepEval() throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException {
        PrgState prg = repo.getCurrentPrg();
        repo.clearFile();
        int index = repo.getCurrentIndex();
        while(!prg.getExeStack().isEmpty()) {
//            oneStepEval(prg);
            prg.oneStep();
            prg.getHeapMemory().setContent(ConservativeGarbageCollector(
                    prg.getSymTable().values(),
                    prg.getHeapMemory().getContent()));
//            repo.logPrgState(index);
            displayState(prg);
        }
        prg.closeAllFiles();
//        repo.logPrgState(index);
        displayState(prg);
    }

    private void oneStepPerPrg() throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException, InterruptedException {
        List<PrgState> programs = repo.getPrograms();

        // create list of callable programs
        List<Callable<PrgState>> callList = programs.stream()
                .map((PrgState state) ->  (Callable<PrgState>) state::oneStep)
                .collect(Collectors.toList());

        // execute all programStates and remove the completed ones
        List<PrgState> newPrograms = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // set new ids
        newPrograms.forEach(p->p.setId(repo.getNewId()));

        // if program is completed, close all files
        if(programs.size()==1 && programs.get(0).getExeStack().isEmpty())
            programs.get(0).closeAllFiles();

        // log all programs
        repo.logAll();

        // remove completed programs
        programs = programs.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());

        // add the new programs to the list
        programs.addAll(newPrograms);

        // update the list of programs in repo
        repo.setPrograms(programs);
    }

    public void evaluateProgram() throws InterruptedException, IOException, MissingVariableException, DivisionByZeroException, InfiniteLoopException {
        repo.clearFile();
        executor = Executors.newFixedThreadPool(3);
        List<PrgState> programs = repo.getPrograms();
        while(programs.size() > 0) {
            oneStepPerPrg();
            programs.forEach(prg->prg.getHeapMemory().setContent(ConservativeGarbageCollector(
                    prg.getSymTable().values(),
                    prg.getHeapMemory().getContent())));
        }
        executor.shutdownNow();
    }
}
