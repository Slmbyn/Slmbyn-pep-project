package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
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
        app.get("example-endpoint", this::exampleHandler);

        // List all the needed endpoints here like this:
            /**
                app.get("/books", this::getAllBooksHandler);
                app.post("/books", this::postBookHandler);
                app.get("/authors", this::getAllAuthorsHandler);
                app.post("/authors", this::postAuthorHandler);
                app.get("/books/available", this::getAvailableBooksHandler);
                app.start(8080);
            */

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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    
    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = ctx.body();
        Account account = objectMapper.readValue(jsonString, Account.class);

        if (account.getUsername() == null || account.getUsername().isBlank()){
            ctx.status(400).result("Username cant be blank");
            return;
        }

        if (account.getPassword() == null || account.getPassword().length() < 4){
            ctx.status(400).result("Password too short");
            return;
        }

        AccountService acctService = new AccountService();

        Account existingAccount = acctService.getAccountByUsername(account.getUsername());
        
        if (existingAccount != null) {
            ctx.status(400).result("Username already taken");
            return;
        }

        Account newAccount = acctService.registerNewUser(account);

        if (newAccount != null) {
            ctx.status(200).json(newAccount);
        } else {
            ctx.status(400).result("Acct creation failed");
        }
    };

    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        // TODO: Write Logic Here
    };

    private void getUsersMessagesHandler(Context ctx) throws JsonProcessingException {
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