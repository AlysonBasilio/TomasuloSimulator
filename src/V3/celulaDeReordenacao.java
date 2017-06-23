package V3;

public class celulaDeReordenacao {
	
	private boolean ready;
	private boolean busy;
	private Instrucao instrucao;
	private String estado;
	private int destino;
	private int valor;
	private int tempoDeExecucao;
	
	public void decTempoDeExecucao () { this.tempoDeExecucao--; }
	
	public boolean isReady() { return ready; }
	public void setReady(boolean ready) { this.ready = ready; }
	public int getTempoDeExecucao() { return tempoDeExecucao; }
	public void setTempoDeExecucao(int tempoDeExecucao) { this.tempoDeExecucao = tempoDeExecucao; }
	public boolean isBusy() { return busy; }
	public void setBusy(boolean busy) { this.busy = busy; }
	public Instrucao getInstrucao() { return instrucao; }
	public void setInstrucao(Instrucao instrucao) { this.instrucao = instrucao; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public int getDestino() { return destino; }
	public void setDestino(int destino) { this.destino = destino; }
	public int getValor() { return valor; }
	public void setValor(int valor) { this.valor = valor; }
	
}
