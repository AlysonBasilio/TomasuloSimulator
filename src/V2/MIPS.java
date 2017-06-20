package V2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MIPS {
	
	private String barramentoDeDadosComum;
	
	/* Estações de reserva */
	private static EstacaoDeReserva[] somaFP;
	private static EstacaoDeReserva[] multFP;
	private static EstacaoDeReserva[] cargaFP;
	private static int numElemSoma;
	private static int numElemMult;
	private static int numElemCarga;
	
	/* Fila de instruções */
	static HashMap<Integer, Instrucao> filaDeInstrucoes;
	private static int PC;
	
	/* Registradores */
	private static Registrador[] registradores;
	
	/* Memória */
	private static int[] MEM;
	
	/* Buffer de Reordenação */
	private static BufferDeReordenacao buffer;
	
	/* Lê dados do arquivo e coloca na fila de instruções */
	private static void preencheFilaDeInstrucoes () {
		String nome = "Teste2";
		int auxPC = 0;
		
		try {
	    	FileReader arq = new FileReader(nome);
	    	BufferedReader lerArq = new BufferedReader(arq);
	 
	    	String instAux = lerArq.readLine(); // lê a primeira instrução
	    	while (instAux != null) {
	    		Instrucao in = new Instrucao(instAux.split(" ")[0]);
	    		filaDeInstrucoes.put(auxPC, in);
	    		//System.out.printf("%s\n", in.getInstrucao());
	    		instAux = lerArq.readLine(); // lê da segunda até a última linha
	    		auxPC += 4;
	    	} 
	    	arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	    }
		
	}
	
	/* Retorna posição válida se estação de reserva tem espaço. Em caso negativo retorna -1 */
	public static int verificaEstacao (String tipo) {
		
		/* Verifica estação de reserva de adição */
		if (tipo == "Add") {
			for (int k = 0; k < somaFP.length; k++){
				if (!somaFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		/* Verifica estação de reserva de multiplicação */
		if (tipo == "Mult") {
			for (int k = 0; k < multFP.length; k++){
				if (!multFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		/* Verifica estação de reserva de Load/Store */
		if (tipo == "Load/Store") {
			for (int k = 0; k < cargaFP.length; k++){
				if (!cargaFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		return -1;
	}
	
	/* Função que pega uma instrução e manda para a estação de reserva adequada se possível */
	
	private static void emitir () {
		
		Instrucao instAux = filaDeInstrucoes.get(PC);
		int indice;
		
		String opcode = instAux.getInstrucao().substring(0, 6);
		String funct = instAux.getInstrucao().substring(26);
		String rd = instAux.getInstrucao().substring(16, 21);
		String rt = instAux.getInstrucao().substring(11, 16);
		String rs = instAux.getInstrucao().substring(6, 11);
		String immediate = instAux.getInstrucao().substring(16);
		String targetAddress = instAux.getInstrucao().substring(6);
		
		int regRD = Integer.parseInt(rd,2);
		int regRT = Integer.parseInt(rt,2);
		int regRS = Integer.parseInt(rs,2); 
		int tA = Integer.parseInt(targetAddress,2);
		int decImm = Integer.parseInt(immediate.substring(1),2);
		if(immediate.charAt(0)=='1'){
			decImm = decImm*(-1);
		}
		
		switch(opcode){
		//Instrução tipo R
		case "000000":
			switch(funct){
				//Função Add
				case "100000":
					indice = verificaEstacao ("Add");
					if(indice != -1 && !buffer.isFull()) { 
						somaFP[indice].setBusy(true);
						somaFP[indice].setInst("Add");
						somaFP[indice].setDest(buffer.getPosic());
						buffer.adicionaNoBuffer(instAux, regRD);
						if (registradores[regRS].getQi() == "livre")
							somaFP[indice].setQj(registradores[regRS].getVi());
					}
					System.out.println("add R"+regRD+",R"+regRS+",R"+regRT);
					registradores[regRD].setVi(registradores[regRS].getVi() + registradores[regRT].getVi());
					PC+=4;
					break;
				//Função Mul
				case "011000":
					System.out.println("mul R"+regRD+",R"+regRS+",R"+regRT);
					registradores[regRD].setVi(registradores[regRS].getVi() * registradores[regRT].getVi());
					PC+=4;
					break;
				//Função Sub
				case "100010":
					System.out.println("sub R"+regRD+",R"+regRS+",R"+regRT);
					registradores[regRD].setVi(registradores[regRS].getVi() - registradores[regRT].getVi());
					PC+=4;
					break;
				//Função Nop
				case "000000":
					System.out.println("nop");
					PC+=4;
					break;
			};
			break;
		//Instrução Addi
		case "001000":
			System.out.println("addi R"+regRT+",R"+regRS+","+decImm);
			registradores[regRT].setVi(registradores[regRS].getVi()+decImm);
			PC+=4;
			break;
		//Instrução Beq
		case "000101":
			System.out.println("beq R"+regRT+",R"+regRS+","+decImm);
			PC+=4;
			if(registradores[regRS].getVi()==registradores[regRT].getVi()){
				PC += decImm;
			}
			break;
		//Instrução Ble
		case "000111":
			System.out.println("ble R"+regRT+",R"+regRS+","+decImm);
			PC+=4;
			if(registradores[regRS].getVi()<=registradores[regRT].getVi()){
				PC = decImm;
			}
			break;
		//Instrução Bne
		case "000100":
			System.out.println("bne R"+regRT+",R"+regRS+","+decImm);
			PC+=4;
			if(registradores[regRS].getVi()!=registradores[regRT].getVi()){
				PC += decImm;
			}
			break;
		//Instrução Jmp
		case "000010":
			System.out.println("jmp "+tA);				
			PC = tA;
			break;
		//Instrução Lw
		case "100011":
			System.out.println("lw R"+regRT+","+decImm+"(R"+regRS+")");
			PC+=4;
			registradores[regRT].setVi(MEM[registradores[regRS].getVi()+decImm]);
			break;
		//Instrução Sw
		case "101011":
			System.out.println("sw R"+regRT+","+decImm+"(R"+regRS+")");
			PC+=4;
			MEM[registradores[regRS].getVi()+decImm] =registradores[regRT].getVi();
			break;
		default:
			System.out.println("Comando não interpretado!!!");
			break;
		}
		
	}
	
	private static void executar () {}
	
	private static void gravar() {
		// TODO Auto-generated method stub
		
	}
	
	private static void consolidar() {
		// TODO Auto-generated method stub
		
	}

	
	public static void main(String[] args) {
		
		/* Inicializações do Programa */
		filaDeInstrucoes = new HashMap<Integer, Instrucao> ();
		PC = 0;
		
		buffer = new BufferDeReordenacao (6);
		
		somaFP = new EstacaoDeReserva[5];
		multFP = new EstacaoDeReserva[5];
		cargaFP = new EstacaoDeReserva[5];
		for(int i = 0; i<5; i++){
			somaFP[i] = new EstacaoDeReserva ();
			somaFP[i].Busy = false;
			somaFP[i].Tipo = "Add";
			somaFP[i].ID = "Add" + i;
			multFP[i] = new EstacaoDeReserva ();
			multFP[i].Busy = false;
			multFP[i].Tipo = "Mult";
			multFP[i].ID = "Mul" + i;
			cargaFP[i] = new EstacaoDeReserva ();
			cargaFP[i].Busy = false;
			cargaFP[i].Tipo = "Load/Store";
			cargaFP[i].ID = "L/S" + i;
		}
		numElemSoma = 0;
		numElemMult = 0;
		numElemCarga = 0;
		
		MEM = new int[4096];
		Arrays.fill(MEM, 0);
		
		registradores = new Registrador[32];
		for(int i = 0; i<32; i++){
			registradores[i] = new Registrador();
			registradores[i].setVi(0);
		}
		
		
		
		/* clocks */
		
		int clock = 0;
		
		/* Emissão */
		
		preencheFilaDeInstrucoes ();
		
		while(filaDeInstrucoes.containsKey(PC)){
			emitir ();
			executar ();
			gravar ();
			consolidar ();
			clock++;
		}
		
		System.out.println("Clocks: " + clock );
		System.out.println("Valor de R2 = "+ registradores[2].getVi());
		
	}

}
