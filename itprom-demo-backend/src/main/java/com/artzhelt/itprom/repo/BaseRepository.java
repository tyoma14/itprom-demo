package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Artyom Zheltyshev on 27.10.2023
 * Базовый репозиторий
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaSpecificationExecutor<T>, JpaRepository<T, Long> {

}
