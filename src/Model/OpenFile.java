package Model;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.Set;

public class OpenFile implements IStmt {
    private String varFile;
    private String fileName;

    public OpenFile(String var, String filename) {
        this.varFile = var;
        this.fileName = filename;
    }

    @Override
    public PrgState execute(PrgState state) throws FileAlreadyExistsException, FileNotFoundException {
        IDictionaryWithoutKey<Integer, Pair<String, BufferedReader>> fileTable = state.getFileTable();
        for(Integer desc : fileTable.keys()) {
            String name = fileTable.get(desc).getKey();
            if(name.equals(fileName))
                throw new FileAlreadyExistsException("A file with the same name as " + fileName + " is already opened.");
        }

        BufferedReader bufferR = new BufferedReader(new FileReader(fileName));
        Integer newDesc = fileTable.put(new Pair<>(fileName, bufferR));
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        symTable.put(varFile, newDesc);

        return null;
    }

    @Override
    public String toString() {
        return "OpenFile " + fileName + " and mark it as " + varFile;
    }
}
