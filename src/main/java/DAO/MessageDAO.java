package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public Message createNewMessage(Message message) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet pk = ps.getGeneratedKeys();

            while(pk.next()){
                int generated_account_id = pk.getInt(1);
                return new Message(generated_account_id, message.getPosted_by(), message.getMessage_text(), message.time_posted_epoch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Message newMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(newMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public Message getMessageById(int message_id) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int posted_by = rs.getInt("posted_by");
                String message_text = rs.getString("message_text");
                Long time_posted_epoch = rs.getLong("time_posted_epoch");
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message deleteMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
    
        try {
            String selectSql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement selectPs = connection.prepareStatement(selectSql);
            selectPs.setInt(1, message_id);
    
            ResultSet rs = selectPs.executeQuery();
    
            if (rs.next()) {
                int posted_by = rs.getInt("posted_by");
                String message_text = rs.getString("message_text");
                Long time_posted_epoch = rs.getLong("time_posted_epoch");
    
                String deleteSql = "DELETE FROM Message WHERE message_id = ?";
                PreparedStatement deletePs = connection.prepareStatement(deleteSql);
                deletePs.setInt(1, message_id);
                deletePs.executeUpdate();
    
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }
    

    public Message updateMessageById(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2,message.getMessage_id());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int message_id = rs.getInt("message_id");
                int posted_by = rs.getInt("posted_by");
                String message_text = rs.getString("message_text");
                Long time_posted_epoch = rs.getLong("time_posted_epoch");
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
