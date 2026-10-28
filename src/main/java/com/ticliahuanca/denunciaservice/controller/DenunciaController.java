package com.ticliahuanca.denunciaservice.controller;

import com.ticliahuanca.denunciaservice.entity.Denuncia;
import com.ticliahuanca.denunciaservice.service.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
@CrossOrigin(origins = "*")
public class DenunciaController {

    @Autowired
    private DenunciaService service;

    // 1. POST /denuncias/
    @PostMapping
    public ResponseEntity<Denuncia> registrar(@RequestBody Denuncia denuncia) {
        return new ResponseEntity<>(service.registrar(denuncia), HttpStatus.CREATED);
    }

    // 2. GET /denuncias/?offset=0&limit=5
    @GetMapping
    public ResponseEntity<List<Denuncia>> obtenerPaginadas(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {
        if (offset < 0 || limit < 1) {
            return ResponseEntity.badRequest().build();
        }

        int page = offset / limit;
        Page<Denuncia> denunciasPage = service.obtenerPaginadas(PageRequest.of(page, limit));
        return ResponseEntity.ok(denunciasPage.getContent());
    }

    // 3. GET /denuncias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. GET /denuncias/ciudadano/{dni}
    @GetMapping("/ciudadano/{dni}")
    public ResponseEntity<List<Denuncia>> obtenerPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(service.obtenerPorDni(dni));
    }

    // 5. PUT /denuncias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> atender(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.atenderDenuncia(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
