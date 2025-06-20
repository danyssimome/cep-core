package com.pgu.cepcore.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Эфемерную сущность, которая служит для обогащения комплексных события бизнес данными
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexEventMetadata {

    /**
     * Идентификатор метаданных комплексного события
     */
    private UUID id;

    /**
     * Данные обогащения комплексного события
     */
    private String information;
}
