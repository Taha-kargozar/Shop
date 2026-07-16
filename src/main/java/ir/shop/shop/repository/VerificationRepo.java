package ir.shop.shop.repository;

import ir.shop.shop.model.User;
import ir.shop.shop.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepo extends JpaRepository<VerificationCode,Long> {

    Optional<VerificationCode> findByCode(String code);

    Optional<VerificationCode> findByUser(User user);

}
