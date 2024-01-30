package com.revtm.websocketchat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revtm.websocketchat.dto.InputMessageDto;
import com.revtm.websocketchat.dto.OutputMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ObjectMapper objectMapper;
    @Autowired
    public ChatController(SimpMessageSendingOperations simpMessageSendingOperations, ObjectMapper objectMapper) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessageDto send(InputMessageDto inputMessage) throws Exception {
        String time = LocalDate.now().toString();
        return OutputMessageDto.builder()
                .from("App")
                .msg("Thank You for the message, " + inputMessage.getFrom())
                .time(time)
                .build();
    }

    @MessageMapping("/private-chat")
    @SendToUser("/queue/reply")
    public OutputMessageDto sendSpecific(@Payload String message, Principal principal) throws Exception {
        InputMessageDto inputMessage = objectMapper.readValue(message, InputMessageDto.class);
        String time = LocalDate.now().toString();
        return OutputMessageDto.builder()
                .from("App")
                .msg("Thank You for the message, " + inputMessage.getFrom())
                .time(time)
                .build();
    }
}
