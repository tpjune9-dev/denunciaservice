package com.ticliahuanca.denunciaservice.service.impl;

import com.ticliahuanca.denunciaservice.entity.Denuncia;
import com.ticliahuanca.denunciaservice.repository.DenunciaRepository;
import com.ticliahuanca.denunciaservice.service.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DenunciaServiceImpl implements DenunciaService {

    @Autowired
    private DenunciaRepository repository;

    @Override
    public Denuncia registrar(Denuncia denuncia) {
        if (denuncia.getFecha() == null) {
            denuncia.setFecha(LocalDateTime.now());
        }
        denuncia.setEstado(1); // El estado inicia siempre en 1 (no atendida)
        return repository.save(denuncia);
    }

    @Override
    public Page<Denuncia> obtenerPaginadas(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Denuncia> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Denuncia> obtenerPorDni(String dni) {
        return repository.findByDni(dni);
    }

    @Override
    public Denuncia atenderDenuncia(Integer id) {
        Denuncia denuncia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Denuncia no encontrada con id: " + id));
        denuncia.setEstado(0); // Pasa de 1 a 0 (atendida)
        return repository.save(denuncia);
    }
}
