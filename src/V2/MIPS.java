package V2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MIPS {
	
	private static Barramento barramentoDeDadosComum;
	
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
		else if (tipo == "Mult") {
			for (int k = 0; k < multFP.length; k++){
				if (!multFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		/* Verifica estação de reserva de Load/Store */
		else if (tipo == "Load/Store") {
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
		/*Primeiro veremos se há alguma célula passando para o estado de consolidar*/
		
		celulaDeReordenacao aux = buffer.getPosicaoBuffer(buffer.getInicio());
		if (aux.isBusy() && aux.getEstado() == "Gravando"){
			buffer.getPosicaoBuffer(buffer.getInicio()).setEstado("Consolidando");
		}
		
		Instrucao instAux = filaDeInstrucoes.get(PC);
		int indice;
		
		/*Quebra e interpretação da instrução*/
		
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
					/*Se a estação de reserva e o Buffer de reordenação não estiverem cheios,
					 *acontecerá a emissão da instrução.
					 *A variável indice tem uma posicao vazia da Estação de Reserva.*/
					if(indice != -1 && !buffer.isFull()) {  
						somaFP[indice].setBusy(true);
						somaFP[indice].setInst("Add");
						somaFP[indice].setDest(buffer.getPosic());	//Onde essa instrução 
						buffer.adicionaNoBuffer(instAux, regRD);	//estará no Buffer de Reordenamento.
						/*Após adicionar a instrução no buffer de reordenamento e na Esta-
						 *ção de reserva, vamos preencher os dados da estação de reserva. 
						 *Em primeiro lugar, verificamos se o valor do registrador RS depen-
						 *de do resultado de alguma instrução. Se não depender, apenas copia-
						 *mos o valor para Vj. Se depender, adicionamos que existe uma depen-
						 *dência e adicionamos o índice da posição do Buffer de Reordenação
						 *do resultado dependente.*/
						if (registradores[regRS].getQi() == -1)
							somaFP[indice].setVj(registradores[regRS].getVi());
						else
							somaFP[indice].setQj(registradores[regRS].getQi());	
						/*A mesma coisa para o segundo fator da operação.*/
						if (registradores[regRT].getQi() == -1)
							somaFP[indice].setVk(registradores[regRT].getVi());
						else
							somaFP[indice].setQk(registradores[regRT].getQi());
						/*Aqui adicionamos que o registrador que guardará o resultado dessa
						 *operação está dependendo do resultado dessa operação, guardando
						 *nele o índice da posição da operação no Buffer de Reordenação. */
						registradores[regRD].setQi(somaFP[indice].getDest());
						/*Após emitir a instrução, deve-se atualizar o valor de PC.*/
						PC+=4;
					}
					System.out.println("add R"+regRD+",R"+regRS+",R"+regRT);
					break;
				//Função Mul
				case "011000":
					indice = verificaEstacao ("Mult");
					if(indice != -1 && !buffer.isFull()) { 
						multFP[indice].setBusy(true);
						multFP[indice].setInst("Mul");
						multFP[indice].setDest(buffer.getPosic());
						buffer.adicionaNoBuffer(instAux, regRD);
						if (registradores[regRS].getQi() == -1)
							multFP[indice].setVj(registradores[regRS].getVi());
						else
							multFP[indice].setQj(registradores[regRS].getQi());
						if (registradores[regRT].getQi() == -1)
							multFP[indice].setVk(registradores[regRT].getVi());
						else
							multFP[indice].setQk(registradores[regRT].getQi());
						registradores[regRD].setQi(multFP[indice].getDest());
						PC+=4;
					}
					System.out.println("mul R"+regRD+",R"+regRS+",R"+regRT);
					break;
				//Função Sub
				case "100010":
					indice = verificaEstacao ("Add");
					if(indice != -1 && !buffer.isFull()) { 
						somaFP[indice].setBusy(true);
						somaFP[indice].setInst("Sub");
						somaFP[indice].setDest(buffer.getPosic());
						buffer.adicionaNoBuffer(instAux, regRD);
						if (registradores[regRS].getQi() == -1)
							somaFP[indice].setVj(registradores[regRS].getVi());
						else
							somaFP[indice].setQj(registradores[regRS].getQi());	
						if (registradores[regRT].getQi() == -1)
							somaFP[indice].setVk(registradores[regRT].getVi());
						else
							somaFP[indice].setQk(registradores[regRT].getQi());
						registradores[regRD].setQi(somaFP[indice].getDest());
						PC+=4;
					}
					System.out.println("sub R"+regRD+",R"+regRS+",R"+regRT);
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
			indice = verificaEstacao ("Add");
			if(indice != -1 && !buffer.isFull()) { 
				somaFP[indice].setBusy(true);
				somaFP[indice].setInst("Addi");
				somaFP[indice].setDest(buffer.getPosic());
				buffer.adicionaNoBuffer(instAux, regRT);
				if (registradores[regRS].getQi() == -1)
					somaFP[indice].setVj(registradores[regRS].getVi());
				else
					somaFP[indice].setQj(registradores[regRS].getQi());
				somaFP[indice].setVk(decImm);
				registradores[regRT].setQi(somaFP[indice].getDest());
				PC+=4;
			}
			System.out.println("addi R"+regRT+",R"+regRS+","+decImm);
			break;
		//Instrução Beq
		case "000101":
			indice = verificaEstacao("Load/Store");
			if(indice != -1 && !buffer.isFull()){
				cargaFP[indice].setBusy(true);
				cargaFP[indice].setInst("Beq");
				cargaFP[indice].setDest(buffer.getPosic());
				buffer.adicionaNoBuffer(instAux, -1);
				if(registradores[regRS].getQi() == -1)
					cargaFP[indice].setVj(registradores[regRS].getVi());
				else
					cargaFP[indice].setQj(registradores[regRS].getQi());
				if (registradores[regRT].getQi() == -1)
					cargaFP[indice].setVk(registradores[regRT].getVi());
				else
					cargaFP[indice].setQk(registradores[regRT].getQi());
				buffer.getPosicaoBuffer(cargaFP[indice].getDest()).setValor(decImm);
			}
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
	
	private static void executar () {
		for (int m = 0; m < buffer.getTamanho(); m++) {
			celulaDeReordenacao aux = buffer.getPosicaoBuffer(m);
			/*A execução aqui se trata apenas de decrementar o tempo necessário para realizar
			 *a instrução. Percorre-se todo o buffer de reordenação verificando quem está em
			 *estado de execução. */
			if (aux.isBusy() && aux.getEstado() == "Executando"){
				buffer.getPosicaoBuffer(m).decTempoDeExecucao();
				if(buffer.getPosicaoBuffer(m).getTempoDeExecucao()==0){
					calculaValor(m);
				}
			}
		}
	}
	
	private static void calculaValor(int posBuffer) {
		
		for (int m = 0; m < somaFP.length; m++) {
			if(somaFP[m].isBusy() && somaFP[m].getDest()==posBuffer){
				if(somaFP[m].getInst()=="Add")
					buffer.getPosicaoBuffer(posBuffer).setValor(somaFP[m].getVj()+somaFP[m].getVk());
				else if(somaFP[m].getInst()=="Sub")
					buffer.getPosicaoBuffer(posBuffer).setValor(somaFP[m].getVj()-somaFP[m].getVk());
				else if(somaFP[m].getInst()=="Addi")
					buffer.getPosicaoBuffer(posBuffer).setValor(somaFP[m].getVj()+somaFP[m].getVk());
				return;
			}
		}
		
		for (int m = 0; m < multFP.length; m++) {
			if(multFP[m].isBusy() && multFP[m].getDest()==posBuffer){
				if(somaFP[m].getInst()=="Mul")
					buffer.getPosicaoBuffer(posBuffer).setValor(somaFP[m].getVj()*somaFP[m].getVk());
				return;
			}
		}
		
		for (int m = 0; m < cargaFP.length; m++) {
		
		}
	}

	private static void gravar() {
		
		/* Pega a primeira instrução no buffer de reordenação com o status gravando e 
		 * joga o seu valor no barramento juntamente com o destino  
		 */
		for (int m = 0; m < buffer.getTamanho(); m++) {
			celulaDeReordenacao aux = buffer.getPosicaoBuffer((buffer.getInicio()+m)%buffer.getTamanho());
			if (aux.isBusy() && aux.getEstado() == "Gravando"){
					barramentoDeDadosComum.setBusy(true);
					barramentoDeDadosComum.setDado(aux.getValor());
					/*Aqui é setado a posição do Buffer de Reordenação que contêm o resultado da instrução.*/
					barramentoDeDadosComum.setLocal((buffer.getInicio()+m)%buffer.getTamanho());
					break;
			}
		}
		if (barramentoDeDadosComum.isBusy()) {
			/* Percorre as estações de reserva para verificar se o dado no barramento é 
			 * utilizado para retirar alguma dependência de alguma estação.
			 * Caso as dependências deixem de existir, o estado da instrução passa de 
			 * emitida para executando.
			 */
			for (int m = 0; m < somaFP.length; m++) {
				if (somaFP[m].thereIsQj() && somaFP[m].getQj() == barramentoDeDadosComum.getLocal()){
					somaFP[m].setVj(barramentoDeDadosComum.getDado());
					somaFP[m].setbQj(false);
				}
				if (somaFP[m].thereIsQk() && somaFP[m].getQk() == barramentoDeDadosComum.getLocal()){
					somaFP[m].setVk(barramentoDeDadosComum.getDado());
					somaFP[m].setbQk(false);
				}
				if(buffer.getPosicaoBuffer(somaFP[m].getDest()).getEstado()=="Emitida" && !somaFP[m].thereIsQj() && !somaFP[m].thereIsQk())
					buffer.getPosicaoBuffer(somaFP[m].getDest()).setEstado("Executando");
			}
			
			for (int m = 0; m < multFP.length; m++) {
				if (multFP[m].thereIsQj() && multFP[m].getQj() == barramentoDeDadosComum.getLocal()){
					multFP[m].setVj(barramentoDeDadosComum.getDado());
					multFP[m].setbQj(false);
				}
				if (multFP[m].thereIsQk() && multFP[m].getQk() == barramentoDeDadosComum.getLocal()){
					multFP[m].setVk(barramentoDeDadosComum.getDado());
					multFP[m].setbQk(false);
				}
				if(buffer.getPosicaoBuffer(multFP[m].getDest()).getEstado()=="Emitida" && !multFP[m].thereIsQj() && !multFP[m].thereIsQk())
					buffer.getPosicaoBuffer(multFP[m].getDest()).setEstado("Executando");
			}
			
			for (int m = 0; m < cargaFP.length; m++) {
				if (cargaFP[m].thereIsQj() && cargaFP[m].getQj() == barramentoDeDadosComum.getLocal()){
					cargaFP[m].setVj(barramentoDeDadosComum.getDado());
					cargaFP[m].setbQj(false);
				}
				if (cargaFP[m].thereIsQk() && cargaFP[m].getQk() == barramentoDeDadosComum.getLocal()){
					cargaFP[m].setVk(barramentoDeDadosComum.getDado());
					cargaFP[m].setbQk(false);
				}
				if(buffer.getPosicaoBuffer(cargaFP[m].getDest()).getEstado()=="Emitida" && !cargaFP[m].thereIsQj() && !cargaFP[m].thereIsQk())
					buffer.getPosicaoBuffer(cargaFP[m].getDest()).setEstado("Executando");
			}
		}
	}
	
	private static void consolidar() {
		/* Primeiro verificaremos se alguma instrução passou de executando para gravando.*/
		for (int m = 0; m < buffer.getTamanho(); m++) {
			celulaDeReordenacao aux = buffer.getPosicaoBuffer(m);
			if (aux.isBusy() && aux.getEstado() == "Executando" && aux.getTempoDeExecucao()==0)
				buffer.getPosicaoBuffer(m).setEstado("Gravando");
		}
		/* Pega a primeira instrução no buffer de reordenação com o status consolidando e 
		 * grava o seu valor no registrador destino.  
		 */
		celulaDeReordenacao aux = buffer.getPosicaoBuffer(buffer.getInicio());
		if (aux.isBusy() && aux.getEstado() == "Consolidando"){
			consolidaValor(aux.getValor(),buffer.getInicio());
			barramentoDeDadosComum.setBusy(false);
			buffer.removeDoBuffer();
		}
	}

	private static void consolidaValor(int valor, int i) {
		for(int m=1; m<registradores.length; m++){
			if(registradores[m].getQi() == i){
				registradores[m].setVi(valor);
				registradores[m].setQi(-1);
				return;
			}
		}
	}

	public static void main(String[] args) {
		
		/* Inicializações do Programa */
		filaDeInstrucoes = new HashMap<Integer, Instrucao> ();
		PC = 0;
		
		buffer = new BufferDeReordenacao (6);
		
		barramentoDeDadosComum = new Barramento ();
		
		somaFP = new EstacaoDeReserva[5];
		multFP = new EstacaoDeReserva[5];
		cargaFP = new EstacaoDeReserva[5];
		for(int i = 0; i<5; i++){
			somaFP[i] = new EstacaoDeReserva ();
			somaFP[i].setBusy(false);
			somaFP[i].setTipo("Add");
			somaFP[i].setID("Add" + i);
			multFP[i] = new EstacaoDeReserva ();
			multFP[i].setBusy(false);
			multFP[i].setTipo("Mult");
			multFP[i].setID("Mul" + i);
			cargaFP[i] = new EstacaoDeReserva ();
			cargaFP[i].setBusy(false);
			cargaFP[i].setTipo("Load/Store");
			cargaFP[i].setID("L/S" + i);
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
