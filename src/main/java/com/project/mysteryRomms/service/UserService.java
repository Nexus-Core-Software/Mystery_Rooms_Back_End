package com.project.mysteryRomms.service;

import com.project.mysteryRomms.model.entity.PasswordResetToken;
import com.project.mysteryRomms.model.entity.User;
import com.project.mysteryRomms.repository.PasswordResetTokenRepository;
import com.project.mysteryRomms.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createPasswordResetToken(User getterUser) {
        Optional<User> user = repositoryUser.findByEmail(getterUser.getEmail());
        if (user.isEmpty()) {
            return null;
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user.get());
        tokenRepository.save(resetToken);
        return token;
    }

    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            return false;
        }
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        repositoryUser.save(user);
        tokenRepository.delete(resetToken);
        return true;
    }
}
