
package V1;

public class EstacaoDeReserva {
	
	String ID;
	String Tipo;
	boolean Busy;
	String inst;
	int Dest;
	int Vj, 
		Vk, 
		Qj, 
		Qk;
	String A;
	
	public String getID() { return ID; }
	public void setID(String iD) { this.ID = iD; }
	public String getTipo() { return Tipo; }
	public void setTipo(String tipo) { this.Tipo = tipo; }
	public boolean isBusy() { return Busy; }
	public void setBusy(boolean busy) { this.Busy = busy; }
	public String getInst() { return inst; }
	public void setInst(String inst) { this.inst = inst; }
	public int getDest() { return Dest; }
	public void setDest(int dest) { this.Dest = dest; }
	public int getVj() { return Vj; }
	public void setVj(int vj) { this.Vj = vj; }
	public int getVk() { return Vk; }
	public void setVk(int vk) { this.Vk = vk; }
	public int getQj() { return Qj; }
	public void setQj(int qj) { this.Qj = qj; }
	public int getQk() { return Qk; }
	public void setQk(int qk) { this.Qk = qk; }
	public String getA() { return A; }
	public void setA(String a) { this.A = a; }
	
}

