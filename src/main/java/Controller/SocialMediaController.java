package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
// TODO: Import the Models
// TODO: Import the Service files

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

            // process user registration
            app.post("/register", this::registerUserHandler);
            // process user login
            app.post("/login", this::loginUserHandler);
            // create new message
            app.post("messages", this::newMessageHandler);
            // retrieve all messages
            app.get("messages", this::getAllMessagesHandler);
            // get message by ID
            app.get("/messages/{message_id}", this::getMessageByIdHandler);
            // delete message by id
            app.delete("/message/{message_id}", this::deleteMessageByIdHandler);
            // update a message text by ID
            app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
            // get all messages by a specific user
            app.get("/accounts/{account_id}/messages", this::getUsersMessagesHandler);



        return app;
    }

    /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    
    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = ctx.body();
        Account account = objectMapper.readValue(jsonString, Account.class);

        if (account.getUsername() == null || account.getUsername().isBlank()){
            ctx.status(400);
            return;
        }

        if (account.getPassword() == null || account.getPassword().length() < 4){
            ctx.status(400);
            return;
        }

        AccountService acctService = new AccountService();

        Account existingAccount = acctService.getAccountByUsername(account.getUsername());
        
        if (existingAccount != null) {
            ctx.status(400);
            return;
        }

        Account newAccount = acctService.registerNewUser(account);

        if (newAccount != null) {
            ctx.status(200).json(newAccount);
        } else {
            ctx.status(400);
        }
    };

    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = ctx.body();
        Account account = objectMapper.readValue(jsonString, Account.class);

        if (account.getUsername() == null || account.getUsername().isBlank()){
            ctx.status(400);
            return;
        }

        if (account.getPassword() == null || account.getPassword().length() < 4){
            ctx.status(400);
            return;
        }

        AccountService acctService = new AccountService();
        Account existingAccount = acctService.loginUser(account.getUsername(), account.getPassword());

        if (existingAccount != null) {
            ctx.status(200).json(existingAccount);
        } else {
            ctx.status(401);
        }

    };

    private void getUsersMessagesHandler(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));

        AccountService acctService = new AccountService();
        List<Message> messages = acctService.getUsersMessages(account_id);

        ctx.status(200).json(messages);

        };

    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = ctx.body();
        Message message = objectMapper.readValue(jsonString,Message.class);

        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255){
            ctx.status(400);
            return;
        }

        AccountService acctService = new AccountService();
        Account existingAccount = acctService.getAccountById(message.getPosted_by());
        
        if (existingAccount == null) {
            ctx.status(400);
            return;
        }

        MessageService messageService = new MessageService();
        Message newMessage = messageService.createNewMessage(message);

        if(newMessage != null) {
            ctx.status(200).json(newMessage);
        } else {
            ctx.status(400);
        }
    };

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        MessageService messageService = new MessageService();
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200).json(messages);
    };

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));  // Get message_id from path parameter

        
        MessageService messageService = new MessageService();
        Message foundMessage = messageService.getMessageById(messageId);

        if (foundMessage != null) {
            ctx.status(200).json(foundMessage);
        } else {
            ctx.status(200).result("");
        }    };

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };





    // Write out the logic for each endpoint here, calling the neccessary service method, like this:
        /**
            private void postBookHandler(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Book book = mapper.readValue(ctx.body(), Book.class);
                Book addedBook = bookService.addBook(book);
                if(addedBook!=null){
                    ctx.json(mapper.writeValueAsString(addedBook));
                }else{
                    ctx.status(400);
                }
            }
        */


}