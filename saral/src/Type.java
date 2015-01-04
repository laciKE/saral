public enum Type {
	BOOL("logický", "i2", "0"), CHAR("písmeno", "i8", "0"), INT(
			"neskutočné numeralio", "i32", "0"), FLOAT("skutočné numeralio",
			"float", "0.0"), STRING("slovo", "i8", "0");

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

	public static int boolToValue(String boolName) {
		if (boolName.equals("ošaľ")) {
			return -1;
		}
		if (boolName.equals("pravda")) {
			return 1;
		}
		if (boolName.equals("skoroošaľ")) {
			return 0;
		}
		System.err.println("Warning: unknown bool value " + boolName);
		return 0;
	}

	public static int charToValue(String charName) {
		if (charName.length() != 3) {
			System.err.println("Warning: unknown char value " + charName);
			return 0;
		}
		Character ch = charName.charAt(1);
		int val = (int) ch.charValue();
		if (val > 255) {
			System.err.println("Warning: unsupported char value (too large intval): "
					+ charName);
			return 0;
		} else {
			return val;
		}
	}
}
