package GUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

class Menu {
    private LinkedHashMap<String, RunExampleCommand> items;

    Menu() {
        items = new LinkedHashMap<>();
    }

    void addCommand(RunExampleCommand com) {
        items.put(com.getName(), com);
    }

    RunExampleCommand getCommand(String name) {
        return items.get(name);
    }

    Collection<String> getDescriptions() {
        List<String> result = new ArrayList<>();
        items.forEach((key, command) -> result.add(command.getName()));
        return result;
    }
}
