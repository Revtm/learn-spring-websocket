package com.revtm.websocketchat.controller;

import com.revtm.websocketchat.dto.InputMessageDto;
import com.revtm.websocketchat.dto.OutputMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessageDto send(InputMessageDto inputMessage){
        String time = LocalDateTime.now().toString();
        return OutputMessageDto.builder()
                .from(inputMessage.getFrom())
                .msg(inputMessage.getMsg())
                .time(time)
                .build();
    }
}
