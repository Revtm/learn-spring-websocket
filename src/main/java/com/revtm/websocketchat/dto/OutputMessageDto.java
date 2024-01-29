package com.revtm.websocketchat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutputMessageDto {
    private String from;
    private String msg;
    private String time;
}
