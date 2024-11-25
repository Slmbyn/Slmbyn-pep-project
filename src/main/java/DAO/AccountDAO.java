package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account getAccountByUsername(String username){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int account_id = rs.getInt("account_id");
                String acct_username = rs.getString("username");
                String password = rs.getString("password");
                return new Account(account_id, acct_username, password);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account loginUser(String username, String password) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int acctId = rs.getInt("account_id");
                String acctUsername = rs.getString("username");
                String acctPassword = rs.getString("password");
                return new Account(acctId, acctUsername, acctPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getUsersMessages(int account_id) {
        List<Message> messages = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int message_id = rs.getInt("message_id");
                int posted_by = rs.getInt("posted_by");
                String message_text = rs.getString("message_text");
                Long time_posted_epoch = rs.getLong("time_posted_epoch");
                Message newMessage = new Message(message_id, posted_by, message_text, time_posted_epoch);
                messages.add(newMessage);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public Account getAccountById(int account_id){
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String acct_username = rs.getString("username");
                String password = rs.getString("password");
                return new Account(account_id, acct_username, password);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
