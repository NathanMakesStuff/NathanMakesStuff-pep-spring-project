package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountService accountService;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Message sendMessage(Message newMessage) {
      
        if(accountService.accountExists(newMessage.getPosted_by()) && newMessage.getMessage_text() != "" && 
                newMessage.getMessage_text().length() < 255)  {
            Message posted = messageRepository.save(newMessage);
            return posted;
        } else return null;


    }

    public Message getMessageById(Integer messageID) {
        Optional<Message> retrievedMessage = messageRepository.findById(messageID);
        if(retrievedMessage.isPresent()) return retrievedMessage.get();
        else return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getAllUserMessages(Integer accountID) {
        return messageRepository.findByPostedBy(accountID);
    }

    @Transactional
    public Integer deleteMessageByID(Integer messageID) {
        return messageRepository.deleteMessageByID(messageID);
    }

    @Transactional
    public Integer updateMessage(String newText, Integer messageID) {
        return messageRepository.updateMessage(newText, messageID);
    }
}
