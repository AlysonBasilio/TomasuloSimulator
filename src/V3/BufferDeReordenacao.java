package V3;

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
			buffer[n].setTempoDeExecucao(0);
		}
		this.tamanho = tamanho;
		this.numCelulasOcupadas = 0;
		this.inicio = 0;
		this.fim = 0;
	}
	
	public void setAddress(int posic, int address) { buffer[posic].setAddress(address); }
	
	public void adicionaNoBuffer (Instrucao inst, int dest) {
			buffer[fim].setBusy(true);
			buffer[fim].setReady(false);
			buffer[fim].setEstado("Emitida");
			buffer[fim].setInstrucao(inst);
			buffer[fim].setDestino(dest);
			buffer[fim].setTempoDeExecucao(1);
			numCelulasOcupadas++;
			fim = (fim+1)%tamanho;
	}
	
	public void removeDoBuffer () {
		buffer[inicio].setBusy(false);
		buffer[inicio].setValor(0);
		numCelulasOcupadas--;
		inicio = (inicio+1)%tamanho;
	}
	
	public int getNumCelulas () { return numCelulasOcupadas; }
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
	
	public boolean isEmpty () {
		if ( numCelulasOcupadas == 0)
			return true;
		else
			return false;
	}

	public void setEstado(int i, String estado) { buffer[i].setEstado(estado); }
	
	public void setTempoExecucao (int i, int tempo) { buffer[i].setTempoDeExecucao(tempo); }
	
	public void decTempoDeExecucao() {
		for (int m = 0; m < tamanho; m++){
			//System.out.println("Tempo de Execucao B"+m+" = "+buffer[m].getTempoDeExecucao());
			if (buffer[m].getTempoDeExecucao() > 0)
				buffer[m].decTempoDeExecucao();
			//System.out.println("Tempo de Execucao B"+m+" = "+buffer[m].getTempoDeExecucao());
		}
	}

	public void setValor(int posBuffer, int valor) {
		buffer[posBuffer].setValor(valor);
	}

	public void setReady(int posicBuffer, boolean b) {
		buffer[posicBuffer].setReady(b);
	}

	public void setBusy(int m, boolean b) {
		buffer[m].setBusy(b);
		if(!b){
			numCelulasOcupadas--;
		}
	}

	public void imprimeTodosOsValores() {
		System.out.println("Entrada | Ocupado | Instrução | Estado | Destino | Valor");
		for(int i=0; i<buffer.length; i++){
			if(buffer[i].isBusy())
				System.out.println("B"+i+" | "+buffer[i].isBusy()+" | "+buffer[i].getInstrucao().getInstrucao()+" | "+buffer[i].getEstado()+" | "+buffer[i].getDestino()+" | "+buffer[i].getValor());
		}
	}

	public void setDestino(int i, int j) {
		buffer[i].setDestino(j);
	}

}
