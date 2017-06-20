package V1;

public class Instrucao{
	private String inst;
	
	Instrucao(String a){
		this.inst = a;
	}
	public String getInstrucao() {
		return inst;
	}
}

/* Garantir que instrução tenha 32 caracteres e seja formada apenas por zeros e uns */
