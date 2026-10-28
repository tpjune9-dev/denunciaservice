package com.ticliahuanca.denunciaservice.service;

import com.ticliahuanca.denunciaservice.entity.Denuncia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface DenunciaService {
    Denuncia registrar(Denuncia denuncia);
    Page<Denuncia> obtenerPaginadas(Pageable pageable);
    Optional<Denuncia> obtenerPorId(Integer id);
    List<Denuncia> obtenerPorDni(String dni);
    Denuncia atenderDenuncia(Integer id);
}
