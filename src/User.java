public abstract class User {
	private String userId ="";
	private String email = "";
	private String password = "";
	private String role = "";
	
	abstract void register(String email, String password, String name);
	abstract void login(String email, String password);
}
