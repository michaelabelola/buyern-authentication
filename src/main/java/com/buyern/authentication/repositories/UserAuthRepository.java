package com.buyern.authentication.repositories;

import com.buyern.authentication.models.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    boolean existsByEmailOrPhoneAllIgnoreCase(String email, String phone);

    Optional<UserAuth> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update user_auth u set u.password = ?1 where u.uid = ?2")
    void updatePasswordByUid(String password, String uid);

}