package es.uv.uvlive.session;

import java.util.ArrayList;

/**
 * Created by alextfos on 07/12/2016.
 */

public class Session {
    private static Session session;

    private User currentUser;
    private ArrayList<String> loginAllowed;

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    private Session() {
        loginAllowed = new ArrayList<>();
        loginAllowed.add(0,"Alumno");
        loginAllowed.add(1,"Profesor");
        loginAllowed.add(2,"Admin");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void loginSuccessfull(int typeLogin) {
        switch (typeLogin) {
            case 0:
                currentUser = new Student();
                break;
            case 1:
                currentUser = new Teacher();
                break;
            case 2:
                currentUser = new Admin();
                break;
        }
    }
}
