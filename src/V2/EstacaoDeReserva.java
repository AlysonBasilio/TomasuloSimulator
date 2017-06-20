package V2;

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
	private boolean bVj, 
					bVk, 
					bQj, 
					bQk; 
	private	String A;
	
	public String getID() {	return ID; }
	public void setID(String iD) { ID = iD;	}
	public String getTipo() { return Tipo; }
	public void setTipo(String tipo) { Tipo = tipo;	}
	public boolean isBusy() { return Busy; }
	public void setBusy(boolean busy) {	
		Busy = busy;
		if(!busy){
			bVj = false;
			bVk = false;
			bQj = false;
			bQk = false;
		}		
	}
	public String getInst() { return inst; }
	public void setInst(String inst) { this.inst = inst; }
	public int getDest() { return Dest; }
	public void setDest(int dest) { Dest = dest; }
	public int getVj() { return Vj; }
	public void setVj(int vj) { 
		Vj = vj; 
		bVj = true;
	}
	public int getVk() { return Vk; }
	public void setVk(int vk) { 
		Vk = vk; 
		bVk = true;
	}
	public int getQj() { return Qj;	}
	public void setQj(int qj) { 
		Qj = qj; 
		bQj = true;
	}
	public int getQk() { return Qk;	}
	public void setQk(int qk) {
		Qk = qk;
		bQk = true;
	}
	public boolean thereIsVj() { return bVj; }
	public void setbVj(boolean bVj) { this.bVj = bVj; }
	public boolean thereIsVk() { return bVk; }
	public void setbVk(boolean bVk) { this.bVk = bVk; }
	public boolean thereIsQj() { return bQj; }
	public void setbQj(boolean bQj) { this.bQj = bQj; }
	public boolean thereIsQk() { return bQk; }
	public void setbQk(boolean bQk) { this.bQk = bQk; }
	public String getA() { return A; }
	public void setA(String a) { A = a; }
	
}

