package es.uv.uvlive.data.gateway.form;


public class MerchantRegisterForm {

    private String dni;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    /**
     * Constructor
     */
    public MerchantRegisterForm() {

    }

    /**
     * Gets DNI
     * @return dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * Sets DNI
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Gets first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets user name
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
