    package com.doanet.api.repository;

    import com.doanet.api.entity.Ong;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface OngRepository extends JpaRepository<Ong, Long> {

      @Query("SELECT o FROM Ong o WHERE id = :id AND o.user.isActive = true")
      Optional<Ong> findByIdActive(@Param("id") Long id);

      @Query("SELECT o FROM Ong o WHERE o.cnpj = :cnpj AND o.user.isActive = true")
      Optional<Ong> findByCnpjActive(@Param("cnpj") String cnpj);

      @Query("SELECT o FROM Ong o WHERE o.user.isActive = true")
      Page<Ong> findAllActive(Pageable pageable);

      Optional<Ong> findByCnpj(String cnpj);
    }
