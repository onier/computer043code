/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animatino;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class PasswordUtils {

    @UseCase(id = 47, description = "a")
    public boolean validatePasswrod(String word) {
        return (word.matches(""));
    }

    @UseCase(id = 48)
    public String encryptPassword(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    @UseCase(id = 49, description = "b")
    public boolean checkForNewPassword(List<String> prevPasswords, String password) {
        return !prevPasswords.contains(password);
    }
}
