package com.api.emailsenderapi.controller;

import com.api.emailsenderapi.model.Email;
import com.api.emailsenderapi.model.EmailContent;
import com.api.emailsenderapi.model.dto.EmailDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/default")
public interface EmailControllerOperation {

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    Email saveEmail(@RequestBody @Valid Email email);
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    EmailDTO findEmailByAddress(@RequestParam String email);
    @GetMapping("/getById")
    @ResponseStatus(HttpStatus.OK)
    EmailDTO findEmailById(@RequestParam Long id);
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    EmailDTO updateEmail(@RequestParam Long id, @RequestBody Email email);
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    void deleteEmail(@RequestParam Long id);

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    boolean sendEmails(@RequestBody EmailContent emailContent);

}
