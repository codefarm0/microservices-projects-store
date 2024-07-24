package com.codefarm.idmaster.service;

import com.codefarm.idmaster.model.GeneratedId;
import com.codefarm.idmaster.model.IdWithTimestamp;
import com.codefarm.idmaster.repository.GeneratedIdRepository;
import com.codefarm.idmaster.service.generator.SequentialIdGenerator;
import com.codefarm.idmaster.service.generator.SnowflakeIdGenerator;
import com.codefarm.idmaster.service.generator.UUIDv7Generator;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UniqueIdService {

    @Autowired
    private UUIDv7Generator uuidv7Generator;

    @Autowired
    private SequentialIdGenerator sequentialIdGenerator;

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Autowired
    private GeneratedIdRepository generatedIdRepository;

    public IdWithTimestamp generateUUIDv7() {
        String id = uuidv7Generator.generateUUIDv7();
        // 8-4-4-12
        GeneratedId generatedId = new GeneratedId();
        generatedId.setId(id);
        generatedId.setApproach("uuidv7");
        generatedId.setTimestamp(LocalDateTime.now());
        generatedIdRepository.save(generatedId);
        return new IdWithTimestamp(generatedId.getId(), generatedId.getTimestamp());
    }







    public IdWithTimestamp generateSequentialId() {
        String id = sequentialIdGenerator.generateSequentialId();
        GeneratedId generatedId = new GeneratedId();
        generatedId.setId(id);
        generatedId.setApproach("sequential");
        generatedId.setTimestamp(LocalDateTime.now());
        generatedIdRepository.save(generatedId);
        return new IdWithTimestamp(generatedId.getId(), generatedId.getTimestamp());
    }

    public IdWithTimestamp generateSnowflakeId() {
        String id = snowflakeIdGenerator.generateSnowflakeId().toString();
        GeneratedId generatedId = new GeneratedId();
        generatedId.setId(id);
        generatedId.setApproach("snowflake");
        generatedId.setTimestamp(LocalDateTime.now());
        generatedIdRepository.save(generatedId);
        return new IdWithTimestamp(generatedId.getId(), generatedId.getTimestamp());
    }

    public List<IdWithTimestamp> getLast5Ids(String approach) {
        return generatedIdRepository.findTop5ByApproachOrderByIdDesc(approach)
                .stream()
                .map(id -> new IdWithTimestamp(id.getId(), id.getTimestamp()))
                .toList();
    }
}
