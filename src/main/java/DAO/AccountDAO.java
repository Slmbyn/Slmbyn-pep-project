package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


public class AccountDAO {
    
    public Account registerNewUser(Account account) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Account (username, password) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            ResultSet pk = ps.getGeneratedKeys();

            while(pk.next()){
                int generated_account_id = pk.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account loginUser(Account account) {
        return null;
    }

    public List<Message> getUsersMessages(Account account) {
        return null;
    }
}
