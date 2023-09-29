package com.example.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount){
        Account registeredAccount = accountService.addAccount(newAccount);
        if(registeredAccount != null) return ResponseEntity.ok(registeredAccount);
        else return ResponseEntity.status(409).body(null);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginHandler(@RequestBody Account account){
        return accountService.login(account);
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        Message sentMessage = messageService.sendMessage(newMessage);
        if(sentMessage != null) return ResponseEntity.ok(sentMessage);
        else return ResponseEntity.status(400).body(null);
    }

    @DeleteMapping(value = "/messages/{deletedMessageID}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer deletedMessageID){
        return ResponseEntity.ok().body(messageService.deleteMessageByID(deletedMessageID));
                
    }

    @GetMapping(value = "/messages/{retrievedMessageID}") 
    public ResponseEntity<Message> retrieveMessage(@PathVariable Integer retrievedMessageID) {
        Message retrievedMessage = messageService.getMessageById(retrievedMessageID);
        return ResponseEntity.ok(retrievedMessage);
    }

    @GetMapping(value = "/messages") 
    public ResponseEntity<List<Message>> retrieveAllMessages() {
        List<Message> retrievedMessage = messageService.getAllMessages();
        return ResponseEntity.ok(retrievedMessage);
    }

    @GetMapping(value = "/accounts/{accountID}/messages") 
    public ResponseEntity<List<Message>> retrieveAllUserMessages(@PathVariable Integer accountID) {
        List<Message> retrievedMessage = messageService.getAllUserMessages(accountID);
        return ResponseEntity.ok(retrievedMessage);
    }

    @PatchMapping(value = "/messages/{updatedMessageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable("updatedMessageId") Integer updatedMessageID, @RequestBody Message newMessage) {
        String newText = newMessage.getMessage_text();
        if(messageService.getMessageById(updatedMessageID)!= null && newText != "" && newText.length() < 255)
            return ResponseEntity.ok().body(messageService.updateMessage(newText, updatedMessageID));
        else return ResponseEntity.badRequest().body(null);
    }
}
