package com.nexon.board.controller;


import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
class StompController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    fun greeting(message:Message<String>): String {
        return message.payload
    }
}
