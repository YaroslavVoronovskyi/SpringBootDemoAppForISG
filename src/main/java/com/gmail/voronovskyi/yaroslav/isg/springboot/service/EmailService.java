package com.gmail.voronovskyi.yaroslav.isg.springboot.service;

import com.gmail.voronovskyi.yaroslav.isg.springboot.model.EmailDetails;

public interface EmailService {

    void sendSimpleMail(EmailDetails details);
}
