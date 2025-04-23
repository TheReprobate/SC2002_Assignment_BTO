package btosystem.classes;

/**
 * Abstract class User.
 */
public abstract class User {
    private String nric;
    private String name;
    private String password;
    private int age;
    private boolean married;

    /**
     * Constructor for User.
     */
    public User(String nric, String name, int age, boolean married) {
        this.nric = nric;
        this.name = name;
        this.age = age;
        this.married = married;
        this.password = "password";
    }

    public String getNric() {
        return nric;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }
}
