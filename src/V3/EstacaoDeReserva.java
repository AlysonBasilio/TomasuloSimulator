package V3;

public class EstacaoDeReserva {
	
	private String ID;
	private String Tipo;
	private boolean Busy;
	private String inst;
	private int Dest;
	private int Vj, 
				Vk, 
				Qj, 
				Qk;
	private	int A;
	
	public String getID() {	return ID; }
	public void setID(String iD) { this.ID = iD;	}
	public String getTipo() { return Tipo; }
	public void setTipo(String tipo) { this.Tipo = tipo;	}
	public boolean isBusy() { return Busy; }
	public void setBusy(boolean busy) {	this.Busy = busy; }
	public String getInst() { return inst; }
	public void setInst(String inst) { this.inst = inst; }
	public int getDest() { return Dest; }
	public void setDest(int dest) { this.Dest = dest; }
	public int getVj() { return Vj; }
	public void setVj(int vj) { this.Vj = vj; }
	public int getVk() { return Vk; }
	public void setVk(int vk) { this.Vk = vk; }
	public int getQj() { return Qj;	}
	public void setQj(int qj) { this.Qj = qj; }
	public int getQk() { return Qk;	}
	public void setQk(int qk) { this.Qk = qk; }
	public int getA() { return A; }
	public void setA(int a) { this.A = a; }
	
}

