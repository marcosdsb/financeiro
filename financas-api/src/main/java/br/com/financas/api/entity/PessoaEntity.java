package br.com.financas.api.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PESSOA")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class PessoaEntity {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator = "pessoa_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pessoa_id_seq", sequenceName = "pessoa_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "NOME")
	@NotNull
	@Size(min = 3, max=60)
	private String nome;
			
	@Column(name="ATIVO")
	@NotNull
	private boolean status;
	
	@Column(name = "LOGRADOURO")
	private String logradouro;

	@Column(name="NUMERO")
	private String numero;

	@Column(name="COMPLEMENTO")
	private String complemento;

	@Column(name="BAIRRO")
	private String bairro;

	@Column(name="CEP")
	private String cep;

	@Column(name="CIDADE")
	private String cidade;

	@Column(name="UF")
	private String uf;
	
	

}
