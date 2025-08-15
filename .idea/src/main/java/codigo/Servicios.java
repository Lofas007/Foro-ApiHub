package codigo;

import codigo.TopicoRequest;
import codigo.TopicoResponse;
import codigo.Topico;
import codigo.TopicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository repository;

    @Transactional
    public TopicoResponse crearTopico(TopicoRequest request) {
        repository.findByTituloAndMensaje(request.titulo(), request.mensaje())
                .ifPresent(t -> {
                    throw new RuntimeException("Tópico duplicado");
                });

        Topico topico = Topico.builder()
                .titulo(request.titulo())
                .mensaje(request.mensaje())
                .autor(request.autor())
                .curso(request.curso())
                .fechaCreacion(LocalDateTime.now())
                .estado("ABIERTO")
                .build();

        repository.save(topico);

        return mapToResponse(topico);
    }

    public List<TopicoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TopicoResponse detalle(Long id) {
        Topico t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        return mapToResponse(t);
    }

    @Transactional
    public TopicoResponse actualizar(Long id, TopicoRequest request) {
        Topico t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        t.setTitulo(request.titulo());
        t.setMensaje(request.mensaje());
        t.setAutor(request.autor());
        t.setCurso(request.curso());
        return mapToResponse(t);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No encontrado");
        }
        repository.deleteById(id);
    }

    private TopicoResponse mapToResponse(Topico t) {
        return new TopicoResponse(t.getId(), t.getTitulo(), t.getMensaje(),
                t.getFechaCreacion(), t.getEstado(), t.getAutor(), t.getCurso());
    }
}

