package V1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MIPS {

	public static void main(String[] args) {
		int[] MEM = new int[4096];
		HashMap<Integer,Instrucao> FilaDeInstrucoes = new HashMap<Integer,Instrucao>();
		Registrador[] Registradores = new Registrador[32];
		for(int i = 0; i<32; i++){
			Registradores[i] = new Registrador();
			Registradores[i].setVi(0);
		}
		for(int i=0;i<4096;i++){
			MEM[i]=0;
		}
		
		String nome = "Teste";
		/*Configuração 1*/
		//Registradores[1].setValor(3);
		/*Configuração 2*/
		//Registradores[1].setValor(5);
		/*Configuração 3*/
		//Registradores[1].setValor(6);
		
		//Inicialização de PC:
		int PC = 0;
		
		//Leitura do arquivo de entrada e preenchimento da fila.
	    		
		try {
	    	FileReader arq = new FileReader(nome);
	    	BufferedReader lerArq = new BufferedReader(arq);
	 
	    	String instAux = lerArq.readLine(); // lê a primeira instrução
	    	while (instAux != null) {
	    		Instrucao in = new Instrucao(instAux.split(" ")[0]);
	    		FilaDeInstrucoes.put(PC, in);
	    		PC+=4;
	    		//System.out.printf("%s\n", in.getInstrucao());
	    		instAux = lerArq.readLine(); // lê da segunda até a última linha
	    	} 
	    	arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	    }
		PC = 0;
		//Nessa primeira versão vamos começar programando um processador que executa 
		//as instruções sequencialmente
		
		while(FilaDeInstrucoes.containsKey(PC)){
			System.out.print("PC = "+PC+" -> ");
			Instrucao i = FilaDeInstrucoes.get(PC);
			
			//Tradução da instrução
			
			String opcode = i.getInstrucao().substring(0, 6);
			String funct = i.getInstrucao().substring(26);
			String rd = i.getInstrucao().substring(16, 21);
			String rt = i.getInstrucao().substring(11, 16);
			String rs = i.getInstrucao().substring(6, 11);
			String immediate = i.getInstrucao().substring(16);
			String targetAddress = i.getInstrucao().substring(6);
			int regRD = Integer.parseInt(rd,2);
			int regRT = Integer.parseInt(rt,2);
			int regRS = Integer.parseInt(rs,2);
			int decImm = Integer.parseInt(immediate,2);
			int tA = Integer.parseInt(targetAddress,2);
			switch(opcode){
			//Instrução tipo R
			case "000000":
				switch(funct){
					//Função Add
					case "100000":
						System.out.println("add R"+regRD+",R"+regRS+",R"+regRT);
						Registradores[regRD].setVi(Registradores[regRS].getVi() + Registradores[regRT].getVi());
						PC+=4;
						break;
					//Função Mul
					case "011000":
						System.out.println("mul R"+regRD+",R"+regRS+",R"+regRT);
						Registradores[regRD].setVi(Registradores[regRS].getVi() * Registradores[regRT].getVi());
						PC+=4;
						break;
					//Função Sub
					case "100010":
						System.out.println("sub R"+regRD+",R"+regRS+",R"+regRT);
						Registradores[regRD].setVi(Registradores[regRS].getVi() - Registradores[regRT].getVi());
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
				Registradores[regRT].setVi(Registradores[regRS].getVi()+decImm);
				PC+=4;
				break;
			//Instrução Beq
			case "000101":
				System.out.println("beq R"+regRT+",R"+regRS+","+decImm);
				PC+=4;
				if(Registradores[regRS].getVi()==Registradores[regRT].getVi()){
					PC += decImm;
				}
				break;
			//Instrução Ble
			case "000111":
				System.out.println("ble R"+regRT+",R"+regRS+","+decImm);
				PC+=4;
				if(Registradores[regRS].getVi()<=Registradores[regRT].getVi()){
					PC = decImm;
				}
				break;
			//Instrução Bne
			case "000100":
				System.out.println("bne R"+regRT+",R"+regRS+","+decImm);
				PC+=4;
				if(Registradores[regRS].getVi()!=Registradores[regRT].getVi()){
					PC+=decImm;
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
				Registradores[regRT].setVi(MEM[Registradores[regRS].getVi()+decImm]);
				break;
			//Instrução Sw
			case "101011":
				System.out.println("sw R"+regRT+","+decImm+"(R"+regRS+")");
				PC+=4;
				MEM[Registradores[regRS].getVi()+decImm] =Registradores[regRT].getVi();
				break;
			default:
				System.out.println("Comando não interpretado!!!");
				break;
			}
			
		}
		for(int i=0; i<7; i++)
			System.out.println("Valor de R"+i+" = "+ Registradores[i].getVi());
			
	}

}
