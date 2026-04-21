package flawless.beauty.repository;

import flawless.beauty.domain.FlawlessResetPassword;
import flawless.beauty.domain.FlawlessUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlawlessResetPasswordRepository
        extends JpaRepository<FlawlessResetPassword, Long> {

    FlawlessResetPassword findByToken(String token);

    FlawlessResetPassword findByUsuario(FlawlessUsuario usuario);
}
