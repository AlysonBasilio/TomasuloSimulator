package V2;

public class EstacaoDeReserva {
	String ID;
	String Tipo;
	boolean Busy;
	Instrucao inst;
	String Dest;
	int Vj, Vk, Qj, Qk;
	String A;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public boolean isBusy() {
		return Busy;
	}
	public void setBusy(boolean busy) {
		Busy = busy;
	}
	public Instrucao getInst() {
		return inst;
	}
	public void setInst(Instrucao inst) {
		this.inst = inst;
	}
	public String getDest() {
		return Dest;
	}
	public void setDest(String dest) {
		Dest = dest;
	}
	public int getVj() {
		return Vj;
	}
	public void setVj(int vj) {
		Vj = vj;
	}
	public int getVk() {
		return Vk;
	}
	public void setVk(int vk) {
		Vk = vk;
	}
	public int getQj() {
		return Qj;
	}
	public void setQj(int qj) {
		Qj = qj;
	}
	public int getQk() {
		return Qk;
	}
	public void setQk(int qk) {
		Qk = qk;
	}
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	
}
