package V2;

public class BufferDeReordenacao {
	
	private celulaDeReordenacao[] buffer;
	private int numCelulasOcupadas;
	private int posic;
	
	public BufferDeReordenacao (int tamanho) {
		buffer = new celulaDeReordenacao [tamanho];
		for (int n = 0; n < tamanho; n++){
			buffer[n] = new celulaDeReordenacao ();
			buffer[n].setBusy(false);
		}
		posic = 0;
	}
	
	public void adicionaNoBuffer (Instrucao inst, int dest) {
			buffer[posic].setBusy(true);
			buffer[posic].setEstado("Emitida");
			buffer[posic].setInstrucao(inst);
			buffer[posic].setDestino(dest);
			String opcode = inst.getInstrucao().substring(0, 6);
			String funct = inst.getInstrucao().substring(26);
			if(opcode == "100011" || opcode == "101011")		//Load/Store
				buffer[posic].setTempoDeExecucao(4);
			else if (opcode == "000000" && funct == "011000")	//Mult
				buffer[posic].setTempoDeExecucao(3);
			else 												//Outros
				buffer[posic].setTempoDeExecucao(1);
			posic = (posic+1)%buffer.length;
	}
	
	public int getPosic () { return posic;}
	
	public boolean isFull () {
		if ( numCelulasOcupadas == buffer.length)
			return true;
		else
			return false;
	}
	
}
