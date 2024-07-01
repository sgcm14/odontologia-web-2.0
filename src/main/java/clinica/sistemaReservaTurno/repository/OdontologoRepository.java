package clinica.sistemaReservaTurno.repository;
import clinica.sistemaReservaTurno.entity.Odontologo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    Optional<Odontologo> findByNumeroMatricula(String numeroMatricula);
}
