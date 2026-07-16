package ir.shop.shop.service;

import ir.shop.shop.model.User;

public interface VerificationService {

    String createCode(User user);

    boolean verifyCode(User user,String code);

}
