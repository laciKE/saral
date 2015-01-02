public class Variable {
	private String name;
	private Type type;
	private String register;
	private boolean constant = false;

	public Variable(String name, Type type, String register) {
        this.name=name;
        this.type=type;
        this.register=register;
    }
	
	public Variable(String name, Type type, String register, boolean constant) {
        this.name=name;
        this.type=type;
        this.register=register;
        this.constant = constant;
	}	
	public boolean isConstant(){
		return constant;
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

	public String getRegister() {
		return this.register;
	}

	public void setRegister(String register) {
		this.register = register;
	}
}
