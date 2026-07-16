package ir.shop.shop.service;

import ir.shop.shop.exception.VerificationExpiredException;
import ir.shop.shop.model.User;
import ir.shop.shop.model.VerificationCode;
import ir.shop.shop.repository.VerificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepo verificationRepo;

    @Override
    public String createCode(User user) {

        String code = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        VerificationCode verificationCode =
                VerificationCode.builder()
                        .code(code)
                        .expireTime(LocalDateTime.now().plusMinutes(5))
                        .Used(false)
                        .user(user)
                        .build();

        verificationRepo.save(verificationCode);

        return code;

    }

    @Override
    public boolean verifyCode(User user, String code) {

        VerificationCode verificationCode =
                verificationRepo.findByUser(user)
                        .orElseThrow(VerificationExpiredException::new);

        if(verificationCode.isUsed()){
            throw new VerificationExpiredException();
        }

        if(verificationCode.getExpireTime()
                .isBefore(LocalDateTime.now())){

            throw new VerificationExpiredException();

        }

        if(!verificationCode.getCode().equals(code)){
            return false;
        }

        verificationCode.setUsed(true);

        verificationRepo.save(verificationCode);

        return true;

    }

}
