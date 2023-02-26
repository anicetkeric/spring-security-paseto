package com.bootlabs.springsecuritypaseto.entities;

import com.bootlabs.springsecuritypaseto.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * <h2>GroupRole</h2>
 *
 * @author aek
 *         <p>
 *         Description: group role for application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "app_role")
public class GroupRole implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private RoleEnum code;

	private String description;
}
