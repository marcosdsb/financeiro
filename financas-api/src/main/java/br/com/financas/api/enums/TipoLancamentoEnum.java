package br.com.financas.api.enums;

public enum TipoLancamentoEnum {
	
	RECEITA("Receita") ,
	DESPESA("Despesa");
	
	private String descricao;

	private TipoLancamentoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	
	

}
