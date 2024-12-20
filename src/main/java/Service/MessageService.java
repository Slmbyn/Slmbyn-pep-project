package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;


public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createNewMessage(Message message){
        return messageDAO.createNewMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessageById(int message_id, Message message) {
        return messageDAO.updateMessageById(message_id, message);
    }
}
