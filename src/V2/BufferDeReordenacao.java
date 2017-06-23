package V2;

public class BufferDeReordenacao {
	
	private celulaDeReordenacao[] buffer;
	private int numCelulasOcupadas;
	private int inicio;
	private int fim;
	private int tamanho;
	
	public BufferDeReordenacao (int tamanho) {
		buffer = new celulaDeReordenacao [tamanho];
		for (int n = 0; n < tamanho; n++){
			buffer[n] = new celulaDeReordenacao ();
			buffer[n].setBusy(false);
		}
		this.tamanho = tamanho;
		this.numCelulasOcupadas = 0;
		this.inicio = 0;
		this.fim = 0;
	}
	
	public void adicionaNoBuffer (Instrucao inst, int dest) {
			buffer[fim].setBusy(true);
			buffer[fim].setReady(false);
			buffer[fim].setEstado("Emitida");
			buffer[fim].setInstrucao(inst);
			buffer[fim].setDestino(dest);
			numCelulasOcupadas++;
			String opcode = inst.getInstrucao().substring(0, 6);
			String funct = inst.getInstrucao().substring(26);
			if(opcode == "100011" || opcode == "101011")		//Load/Store
				buffer[fim].setTempoDeExecucao(4);
			else if (opcode == "000000" && funct == "011000")	//Mult
				buffer[fim].setTempoDeExecucao(3);
			else 												//Outros
				buffer[fim].setTempoDeExecucao(1);
			fim = (fim+1)%tamanho;
	}
	
	public void removeDoBuffer () {
		buffer[inicio].setBusy(false);
		numCelulasOcupadas--;
		inicio = (inicio+1)%tamanho;
	}
	
	public int getTamanho () { return tamanho; }
	public int getPosic () { return fim;}
	public int getInicio () {return inicio; }
	public celulaDeReordenacao getItemBuffer (int x) { return buffer[x]; }
	
	public boolean isFull () {
		if ( numCelulasOcupadas == tamanho)
			return true;
		else
			return false;
	}

	public void setEstado(int i, String estado) {
		buffer[i].setEstado(estado);
	}

	public void decTempoDeExecucao(int m) {
		buffer[m].decTempoDeExecucao();
	}

	public void setValor(int posBuffer, int valor) {
		buffer[posBuffer].setValor(valor);
	}

	public void setReady(int posicBuffer, boolean b) {
		buffer[posicBuffer].setReady(b);
	}

	public void setBusy(int m, boolean b) {
		buffer[m].setBusy(b);
	}

	public void imprimeTodosOsValores() {
		System.out.println("Entrada | Ocupado | Instrução | Estado | Destino | Valor");
		for(int i=0; i<buffer.length; i++){
			if(buffer[i].isBusy())
				System.out.println("B"+i+" | "+buffer[i].isBusy()+" | "+buffer[i].getInstrucao().getInstrucao()+" | "+buffer[i].getEstado()+" | "+buffer[i].getDestino()+" | "+buffer[i].getValor());
		}
	}

	public boolean isEmpty() {
		if(numCelulasOcupadas == 0)
			return true;
		return false;
	}
	
}
