package com.nam.base.source.code.dtos.request;

import com.nam.base.source.code.enums.EmailTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
    private String to;
    private String verifyToken;
    private EmailTemplate emailTemplate;
    private Map<String, String> attributes;
}
