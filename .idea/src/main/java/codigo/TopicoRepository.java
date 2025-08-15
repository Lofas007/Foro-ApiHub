package codigo;

//import codigo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository<Topico> extends JpaRepository<Topico, Long> {
        Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
}