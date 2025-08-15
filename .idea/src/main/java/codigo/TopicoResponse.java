package codigo;
import java.time.LocalDateTime;

    public record TopicoResponse(
            Long id,
            String titulo,
            String mensaje,
            LocalDateTime fechaCreacion,
            String estado,
            String autor,
            String curso
    ) {}

