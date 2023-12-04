package com.developersstack.edumanage.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {

    public String encrypt(String rowPassword){
      return   BCrypt.hashpw(rowPassword, BCrypt.gensalt(10));
    }

    public boolean checkPassword(String rowPasswword, String hashPassword){
        return BCrypt.checkpw(rowPasswword, hashPassword);
    }
}
