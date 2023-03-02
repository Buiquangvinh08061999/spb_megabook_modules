package com.cg.repository;

import com.cg.domain.dto.PublisherDTO;
import com.cg.domain.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT NEW com.cg.domain.dto.PublisherDTO(" +
                "p.id, " +
                "p.title" +
            ") " +
            "FROM Publisher AS p " +
            "WHERE p.deleted = false " +
            "ORDER BY p.id DESC"
    )
    List<PublisherDTO> findAllPublisherDTOSByDeletedIsFalse();

    Boolean existsByTitle(String title);
}
