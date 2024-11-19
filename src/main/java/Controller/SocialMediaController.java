package Controller;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final MessageService messageService;
    private final AccountService accountService;
    
    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //login and register
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        //messages
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);       
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

     private void registerHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if(newAccount != null){
            ctx.json(mapper.writeValueAsString(newAccount));
        }else{
            ctx.status(400);
        }   
     } 
    
    private void loginHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountLogin = accountService.addAccount(account);
        if(accountLogin != null){
            ctx.json(mapper.writeValueAsString(accountLogin));
        }else{
            ctx.status(400);
        }   
    } 
    private void postMessageHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Optional<Message> message = messageService.getMessageById(id);
        if (message.isPresent()) {
            ctx.json(message.get());
        } else {
            ctx.status(200).result("");
        }
    }


    private void deleteMessageById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id")); 

        Optional<Message> message = messageService.getMessageById(id);
    
        if (message.isPresent()) {
            messageService.deleteMessage(message.get());
            ctx.status(200).json(message.get());
        } else {
            ctx.status(200).result(""); 
        }
    }

    private void updateMessageById(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Message mappedMessage = mapper.readValue(ctx.body(), Message.class);
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        mappedMessage.setMessage_id(id);
        Message messageUpdated = messageService.updateMessage(mappedMessage);
        ctx.json(messageUpdated); 
    }

    private void getMessagesByAccountHandler(Context ctx){
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> message = messageService.getMessageById(accountID);
        ctx.json(message);
    }
}