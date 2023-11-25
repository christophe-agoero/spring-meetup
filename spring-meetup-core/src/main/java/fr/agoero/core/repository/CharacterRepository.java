package fr.agoero.core.repository;

import fr.agoero.core.domain.Character;
import fr.agoero.core.repository.common.CommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CommonRepository<Character, Integer>, JpaRepository<Character, Integer>,
        JpaSpecificationExecutor<Character> {

    boolean existsByCode(String code);
}
