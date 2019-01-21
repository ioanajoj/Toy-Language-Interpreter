package GUI;

import Controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.util.ArrayList;

public class ControllerWindow2 {

    @FXML private Button OneStepButton;
    @FXML private Label main_label;
    @FXML private TextField noProgramStates;
    @FXML private ListView<String> ExecutionStack;
    @FXML private ListView<String> Output;
    @FXML private ListView<String> ProgramStates;
    @FXML private TableView<Pair<String, Integer>> SymbolTable;
    @FXML private TableView<Pair<Integer, String>> FileTable;
    @FXML private TableView<Pair<Integer, Integer>> HeappMemory;
    @FXML private TableView<Pair<Integer, Integer>> LockTable;
    @FXML private TableColumn<Pair<String, Integer>, String> IDLockTable;
    @FXML private TableColumn<Pair<String, Integer>, String> PrgStateIDLockTable;
    @FXML private TableColumn<Pair<String, Integer>, String> VarNameSymbolTable;
    @FXML private TableColumn<Pair<String, Integer>, String> ValueSymbolTable;
    @FXML private TableColumn<Pair<String, Integer>, String> identifierFileTable;
    @FXML private TableColumn<Pair<String, Integer>, String> FileNameFileTable;
    @FXML private TableColumn<Pair<String, Integer>, String> addressHeapMemory;
    @FXML private TableColumn<Pair<String, Integer>, String> valueHeapMemory;
    private Controller prg_controller;
    private Integer prgStateIndex;

    public ControllerWindow2() {

    }

    @FXML public void initialize() {

        VarNameSymbolTable.setCellValueFactory(new PropertyValueFactory<>("key"));
        ValueSymbolTable.setCellValueFactory(new PropertyValueFactory<>("value"));

        identifierFileTable.setCellValueFactory(new PropertyValueFactory<>("key"));
        FileNameFileTable.setCellValueFactory(new PropertyValueFactory<>("value"));

        addressHeapMemory.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueHeapMemory.setCellValueFactory(new PropertyValueFactory<>("value"));

        IDLockTable.setCellValueFactory(new PropertyValueFactory<>("key"));
        PrgStateIDLockTable.setCellValueFactory(new PropertyValueFactory<>("value"));

        // set handler for one step button
        OneStepButton.setOnAction(event -> oneStepHandler());

        // set handler for ProgramStates List
        ProgramStates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> processChanged(newValue));
    }

    void setData(Controller data) {
        prg_controller = data;
        prg_controller.startEvalGUI();
        main_label.setText(data.getConfirmation());
        prgStateIndex = 0;
        updateAllVisuals();
    }

    private void oneStepHandler() {
        prg_controller.oneStepGUI();
        updateAllVisuals();
    }

    private void updateAllVisuals() {
        noProgramStates.setText(prg_controller.getNoProgramStates());
        if(!prg_controller.getNoProgramStates().equals("0")) {
            /* PROGRAM STATES */
            ArrayList<String> prgStrings = prg_controller.getProgramsString();
            ObservableList<String> prgList = FXCollections.observableArrayList(prgStrings);
            ProgramStates.setItems(prgList);
            /* update the rest */
            updateOtherVisuals();
        }
        else {
            ProgramStates.getItems().clear();
        }
    }

    private void updateOtherVisuals() {
        if(prgStateIndex > Integer.parseInt(prg_controller.getNoProgramStates())-1) {
            prgStateIndex = Integer.parseInt(prg_controller.getNoProgramStates())-1;
        }
        /* SET NUMBER OF PROGRAM STATES */
        noProgramStates.setText(prg_controller.getNoProgramStates());
        if(!prg_controller.getNoProgramStates().equals("0")) {
            /* EXECUTION STACK */
            ArrayList<String> stackStrings = prg_controller.getExecStackString(prgStateIndex);
            ObservableList<String> stackList = FXCollections.observableArrayList(stackStrings);
            ExecutionStack.setItems(stackList);

            /* SYMBOL TABLE */
            ArrayList<Pair<String, Integer>> symbolTableValues = prg_controller.getSymbolTblPairs(prgStateIndex);
            ObservableList<Pair<String, Integer>> symbolTable = FXCollections.observableArrayList(symbolTableValues);
            SymbolTable.setItems(symbolTable);

            /* FILE TABLE */
            ArrayList<Pair<Integer, String>> fileTableValues = prg_controller.getFileTablePairs(prgStateIndex);
            ObservableList<Pair<Integer, String>> fileTable = FXCollections.observableArrayList(fileTableValues);
            FileTable.setItems(fileTable);

            /* HEAP MEMORY */
            ArrayList<Pair<Integer, Integer>> heapValues = prg_controller.getHeapPairs(prgStateIndex);
            ObservableList<Pair<Integer, Integer>> heapTable = FXCollections.observableArrayList(heapValues);
            HeappMemory.setItems(heapTable);

            /* OUTPUT */
            ArrayList<String> outStrings = prg_controller.getOutString(prgStateIndex);
            ObservableList<String> outList = FXCollections.observableArrayList(outStrings);
            Output.setItems(outList);

            /* LOCK TABLE */
            ArrayList<Pair<Integer, Integer>> lockTable = prg_controller.getLockTableString(prgStateIndex);
            ObservableList<Pair<Integer, Integer>> lockTablee = FXCollections.observableArrayList(lockTable);
            LockTable.setItems(lockTablee);

        }
        else {
            prg_controller.endEvalGUI();
            ExecutionStack.getItems().clear();
            ProgramStates.getItems().clear();
            SymbolTable.getItems().clear();
            HeappMemory.getItems().clear();
            FileTable.getItems().clear();
        }
    }

    private void processChanged(String newValue) {
        if(newValue != null) {
            prgStateIndex = prg_controller.getPrgStateIndex(Integer.parseInt(newValue));
            updateOtherVisuals();
        }
    }
}
