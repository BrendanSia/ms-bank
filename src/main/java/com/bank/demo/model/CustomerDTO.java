package com.bank.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private UUID id;
    private String name;
    private Integer age;
    private String accountNo;
}
