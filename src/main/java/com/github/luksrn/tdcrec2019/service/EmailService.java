package com.github.luksrn.tdcrec2019.service;

import com.github.luksrn.tdcrec2019.dominio.Email;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void send(Email email){
        System.out.println("E-mail sent " + email);
    }
}
