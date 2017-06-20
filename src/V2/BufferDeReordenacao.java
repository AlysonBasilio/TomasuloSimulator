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
			posic = (posic+1)%buffer.length;
	}
	
	public int getPosic () { return posic; }
	
	public boolean isFull () {
		if ( numCelulasOcupadas == buffer.length)
			return true;
		else
			return false;
	}
	
}
