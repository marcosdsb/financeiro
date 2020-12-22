package br.com.financas.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "CATEGORIA")
public class CategoriaEntity {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator = "categoria_id_seq",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "categoria_id_seq", sequenceName = "categoria_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "DESCRICAO")
	@NotNull
	private String descricao;

}
