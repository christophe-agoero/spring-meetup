package fr.agoero.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static fr.agoero.core.exception.common.ValidationConstants.NOT_BLANK;
import static fr.agoero.core.exception.common.ValidationConstants.PATTERN;

@Entity
@Table(name = "character")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Character {

    @Transient
    public static final String CODE_REGEX = "(\\S{3})(-)(\\S{2})";

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_seq_name")
    @SequenceGenerator(name = "character_seq_name", sequenceName = "character_id_seq", allocationSize = 1)
    private int id;

    @Basic
    @Column(name = "firstname")
    private String firstname;

    @Basic
    @Column(name = "lastname", nullable = false)
    @NotBlank(message = NOT_BLANK)
    private String lastname;

    @Basic
    @Column(name = "code", unique = true, nullable = false)
    @NotBlank(message = NOT_BLANK)
    @Pattern(regexp = CODE_REGEX, message = PATTERN)
    private String code;

}
