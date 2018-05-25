import java.io.*;
import java.util.ArrayList;

public class Account implements Serializable {
    private static Account currentUser;
    private String username;
    private String password;
    private boolean admin;

    private Account() {

    }

     Account(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        Data.data.accounts.add(this);
    }

    public static void register(String username, String password, boolean admin) throws Exception {
        try{
            for (Account account : Data.data.accounts) {
                if (account.username.equals(username))
                    throw new UsernameInUseException();
            }
        }catch (NullPointerException ex) {

        }
        Data.data.accounts.add(new Account(username, password, admin));
        login(username, password);
    }

    public static void login(String username, String password) throws Exception {
        for (Account account : Data.data.accounts) {
            if (account.username.equals(username)) {
                if (account.password.equals(password)) {
                    currentUser = account;
                    return;
                } else
                    throw new IncorrectPasswordException();
            }
        }
        throw new NonexistentAccountException();
    }

    public static void logout() {
        currentUser = null;
    }

    public static void clear() {
        Data.data.accounts = new ArrayList<Account>();
    }

    public static Account get() {
        return currentUser;
    }

    public static Account get(int id) {
        return Data.data.accounts.get(id);
    }

    public static Account get(String username) throws Exception {
        for (Account account : Data.data.accounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        throw new AccountNotFoundException();
    }

    public static Account getCurrentUser() {
        return currentUser;
    }

    public int getId() {
        return Data.data.accounts.indexOf(this);
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) throws Exception{
        if (this == currentUser)
            password = newPassword;
        else
            throw new NoPermissionException();
    }
}

class UsernameInUseException extends Exception {
    UsernameInUseException() {
        super("The username is already in use");
    }
}

class NonexistentAccountException extends Exception {
    NonexistentAccountException() {
        super("Nonexistent account");
    }
}

class IncorrectPasswordException extends Exception {
    IncorrectPasswordException() {
        super("Incorrect password");
    }
}

class AccountNotFoundException extends Exception {
    AccountNotFoundException() {
        super("Account not found");
    }
}