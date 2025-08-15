package codigo;

import codigo.TopicoRequest;
import codigo.TopicoResponse;
import codigo.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService service;

    @PostMapping
    public TopicoResponse crear(@RequestBody @Valid TopicoRequest request) {
        return service.crearTopico(request);
    }

    @GetMapping
    public List<TopicoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TopicoResponse detalle(@PathVariable Long id) {
        return service.detalle(id);
    }

    @PutMapping("/{id}")
    public TopicoResponse actualizar(@PathVariable Long id,
                                     @RequestBody @Valid TopicoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

