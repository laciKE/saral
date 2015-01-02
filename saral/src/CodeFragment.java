public class CodeFragment {
	private String code;
	private String register;
	private Type type;

	public CodeFragment() {
		this.code = "";
		this.register = null;
		this.type = null;
	}

	public void addCode(String code) {
		this.code += code;
	}

	public void addCode(CodeFragment fragment) {
		this.code += fragment.toString();
	}

	public void appendCode(CodeFragment fragment) {
		this.addCode(fragment);
		this.setRegister(fragment.getRegister());
		this.setType(fragment.getType());
	}

	public String toString() {
		return this.code;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getRegister() {
		return this.register;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}
}
