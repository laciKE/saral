import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;

public class SymbolTable {
	private List<Map<String, Variable>> variables = null; // tables with
															// variables

	private List<Map<String, Function>> functions = null; // tables with
															// functions

	public SymbolTable() {
		this.variables = new LinkedList<Map<String, Variable>>();
		this.functions = new LinkedList<Map<String, Function>>();
	}

	public void addTable() {
		this.variables.add(0, new HashMap<String, Variable>());
		functions.add(0, new HashMap<String, Function>());
	}

	public void removeTable() {
		this.variables.remove(0);
		functions.remove(0);
	}

	public void addVariable(Variable var) {
		Map<String, Variable> table = variables.get(0);
		table.put(var.getName(), var);
	}

	public Variable getVariable(String identifier) {
		Map<String, Variable> table = this.getTableWithVariable(identifier);
		return table.get(identifier);
	}

	public boolean containsVariable(String identifier) {
		if (this.getTableWithVariable(identifier) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean currentTableContainsVariable(String identifier) {
		return variables.get(0).containsKey(identifier);
	}

	protected Map<String, Variable> getTableWithVariable(String identifier) {
		for (Map<String, Variable> table : variables) {
			if (table.containsKey(identifier)) {
				return table;
			}
		}

		return null;
	}

	public void addFunction(Function f) {
		Map<String, Function> table = functions.get(0);
		table.put(f.getName(), f);
	}

	public Function getFunction(String identifier) {
		Map<String, Function> table = this.getTableWithFunction(identifier);
		return table.get(identifier);
	}

	public boolean containsFunction(String identifier) {
		if (this.getTableWithFunction(identifier) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean currentTableContainsFunction(String identifier) {
		return functions.get(0).containsKey(identifier);
	}

	protected Map<String, Function> getTableWithFunction(String identifier) {
		for (Map<String, Function> table : functions) {
			if (table.containsKey(identifier)) {
				return table;
			}
		}

		return null;
	}
}
