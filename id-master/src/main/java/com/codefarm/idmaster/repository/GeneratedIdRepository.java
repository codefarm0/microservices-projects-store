package com.codefarm.idmaster.repository;

import com.codefarm.idmaster.model.GeneratedId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratedIdRepository extends JpaRepository<GeneratedId, String> {
    List<GeneratedId> findTop5ByApproachOrderByIdDesc(String approach);
}
