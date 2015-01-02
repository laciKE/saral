public enum Type {
	BOOL("logický", "i2", "0"), CHAR("písmeno", "i8", "0"), INT("neskutočné numeralio", "i32", "0"), FLOAT(
			"skutočné numeralio", "float", "0.0");

	private String name = "";
	private String code = "";
	private String def = "";

	Type(String name, String code, String def) {
		this.name = name;
		this.code = code;
		this.def = def;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getDefaultValue() {
		return this.def;
	}

}
