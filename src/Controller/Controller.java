package Controller;

import Model.*;
import Model.Containers.*;
import Model.Containers.MDictionary;
import Model.Containers.IDictionary;
import Model.Containers.IList;
import Model.Containers.IStack;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.InfiniteLoopException;
import Model.Exceptions.MissingVariableException;
import Model.Statements.IStmt;
import Repository.IRepository;
import javafx.scene.control.Alert;
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

    private void garbageCollector() {
        MDictionary<String, Integer> sharedSymTable = new MDictionary<>();
        repo.getPrograms().forEach(prg-> prg.getSymTable().keys().forEach(k->sharedSymTable.put(k,prg.getSymTable().get(k))));
        repo.getPrograms().forEach(prg->prg.getHeapMemory()
                .setContent(ConservativeGarbageCollector(sharedSymTable.values(), prg.getHeapMemory().getContent())));
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
        System.out.println("*======================*");
        System.out.println("Execution Stack:");
        IStack<IStmt> exeStack = state.getExeStack();
        if(!exeStack.toString().equals(""))
            System.out.println(exeStack.toString());

        System.out.println("Symbol Table:");
        IDictionary<String, Integer> symTable = state.getSymTable();
        System.out.println(symTable.toString());

        System.out.println("Output:");
        IList<Integer> output = state.getOut();
        System.out.println(output.toString());

        System.out.println("FileTable:");
        IFileTable<Integer, Pair<String, BufferedReader>> fileTable = state.getFileTable();
        System.out.println(fileTable.toString());
        System.out.println();

        System.out.println("Heap Memory:");
        IHeap<Integer, Integer> heapMemory = state.getHeapMemory();
        System.out.println(heapMemory.toString());
        System.out.println();
    }

    private PrgState oneStepEval(PrgState state) throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException {
        IStack<IStmt> stack = state.getExeStack();
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }   //return used for later assignments

    public void allStepEval() throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException {
        PrgState prg = repo.getCurrentPrg();
        repo.clearFile();
        while(!prg.getExeStack().isEmpty()) {
            prg.oneStep();
            prg.getHeapMemory().setContent(ConservativeGarbageCollector(
                    prg.getSymTable().values(),
                    prg.getHeapMemory().getContent()));
            displayState(prg);
        }
        prg.closeAllFiles();
        displayState(prg);
    }

    private void oneStepPerPrg() throws InterruptedException {
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

        // garbage collector
        garbageCollector();

        // log all programs
        repo.logAll();

        // remove completed programs
        programs = programs.stream().filter(prg->!prg.isCompleted()).collect(Collectors.toList());

        // add the new programs to the list
        programs.addAll(newPrograms);

        // update the list of programs in repo
        repo.setPrograms(programs);
    }

    public void evaluateProgram() throws InterruptedException {
        repo.clearFile();
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programs = repo.getPrograms();
        while(programs.size() > 0) {
            oneStepPerPrg();
        }
        executor.shutdownNow();
    }

    /* GUI */
    public void startEvalGUI() {
        repo.clearFile();
        executor = Executors.newFixedThreadPool(50);
    }

    public void endEvalGUI() {
        executor.shutdownNow();
    }

    public void oneStepGUI() {
        List<PrgState> programs = repo.getPrograms();

        // remove completed programs
        programs = programs.stream().filter(prg->!prg.isCompleted()).collect(Collectors.toList());

        // create list of callable programs
        List<Callable<PrgState>> callList = programs.stream()
                .map((PrgState state) ->  (Callable<PrgState>) state::oneStep)
                .collect(Collectors.toList());

        // execute all programStates and collect newly created ones
        List<PrgState> newPrograms = null;
        try {
            newPrograms = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Exception thrown by toy language");
                            alert.setHeaderText("Oupsie");
                            alert.setContentText(e.toString().split(":")[e.toString().split(":").length-1]);
                            alert.showAndWait();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (newPrograms != null) {
            // set new ids for the new programs
            newPrograms.forEach(p->p.setId(repo.getNewId()));
            // add the new programs to the list
            programs.addAll(newPrograms);
        }

        // update the list of programs in repo
        repo.setPrograms(programs);

        // if program is completed, close all files
        if(programs.size()==1 && programs.get(0).getExeStack().isEmpty())
            programs.get(0).closeAllFiles();

        // garbage garbage
        garbageCollector();

        // log all programs
        repo.logAll();
    }

    public String getConfirmation() { return "Confirmation"; }

    public String getNoProgramStates() { return repo.getNoProgramStates(); }

    public ArrayList<String> getExecStackString(Integer prgStateIndex) {
        return repo.getExecStackString(prgStateIndex);
    }

    public ArrayList<String> getOutString(Integer prgStateIndex) {
        return repo.getOutString(prgStateIndex);
    }

    public ArrayList<String> getProgramsString() {
        return repo.getProgramsString();
    }

    public ArrayList<Pair<String, Integer>> getSymbolTblPairs(Integer prgStateIndex) {
        return repo.getSymbolTblPairs(prgStateIndex);
    }

    public ArrayList<Pair<Integer, String>> getFileTablePairs(Integer prgStateIndex) {
        return repo.getFileTablePairs(prgStateIndex);
    }

    public ArrayList<Pair<Integer, Integer>> getHeapPairs(Integer prgStateIndex) {
        return repo.getHeapPairs(prgStateIndex);
    }

    public Integer getPrgStateIndex(int id) {
        return repo.getPrgStateIndex(id);
    }

    public ArrayList<Pair<Integer, Integer>> getLockTableString(Integer prgStateIndex) {
        return repo.getLockTableString(prgStateIndex);
    }

    public ArrayList<Triplet<Integer, Integer, String>> getCyclicBarrierAll(Integer prgStateIndex) {
        return repo.getCyclicBarrierAll(prgStateIndex);
    }
}
