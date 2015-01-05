import java.util.List;

public class Function {
	private String name;
	private Type type;
	private String functionId;
	private List<Variable> args;
	private boolean procedure = false;
	private boolean external = false;

	public Function(String name, Type type, String id, List<Variable> args) {
		this(name, type, id, args, false, false);
	}

	public Function(String name, Type type, String id, List<Variable> args,
			boolean procedure, boolean external) {
		this.name = name;
		this.type = type;
		this.functionId = id;
		this.args = args;
		this.procedure = procedure;
		this.external = external;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getId() {
		return this.functionId;
	}

	public void setId(String id) {
		this.functionId = id;
	}

	public boolean isProcedure() {
		return this.procedure;
	}
}