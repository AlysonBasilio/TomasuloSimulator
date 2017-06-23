package V2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MIPS {
	
	private static Barramento barramentoDeDadosComum;
	
	/* Esta��es de reserva */
	private static EstacaoDeReserva[] somaFP;
	private static EstacaoDeReserva[] multFP;
	private static EstacaoDeReserva[] cargaFP;
	private static int numElemSoma;
	private static int numElemMult;
	private static int numElemCarga;
	
	/* Fila de instru��es */
	static HashMap<Integer, Instrucao> filaDeInstrucoes;
	private static int PC;
	
	/* Registradores */
	private static Registrador[] registradores;
	
	/* Mem�ria */
	private static int[] MEM;
	
	/* Buffer de Reordena��o */
	private static BufferDeReordenacao buffer;
	
	/* L� dados do arquivo e coloca na fila de instru��es */
	private static void preencheFilaDeInstrucoes () {
		String nome = "Teste";
		int auxPC = 0;
		
		try {
	    	FileReader arq = new FileReader(nome);
	    	BufferedReader lerArq = new BufferedReader(arq);
	 
	    	String instAux = lerArq.readLine(); // l� a primeira instru��o
	    	while (instAux != null) {
	    		Instrucao in = new Instrucao(instAux.split(" ")[0]);
	    		filaDeInstrucoes.put(auxPC, in);
	    		//System.out.printf("%s\n", in.getInstrucao());
	    		instAux = lerArq.readLine(); // l� da segunda at� a �ltima linha
	    		auxPC += 4;
	    	} 
	    	arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	    }
		
	}
	
	/* Retorna posi��o v�lida se esta��o de reserva tem espa�o. Em caso negativo retorna -1 */
	public static int verificaEstacao (String tipo) {
		
		/* Verifica esta��o de reserva de adi��o */
		if (tipo == "Add") {
			for (int k = 0; k < somaFP.length; k++){
				if (!somaFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		/* Verifica esta��o de reserva de multiplica��o */
		else if (tipo == "Mult") {
			for (int k = 0; k < multFP.length; k++){
				if (!multFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		/* Verifica esta��o de reserva de Load/Store */
		else if (tipo == "Load/Store") {
			for (int k = 0; k < cargaFP.length; k++){
				if (!cargaFP[k].isBusy())
					return k;
			}
			return -1;
		}
		
		return -1;
	}
	
	/* Fun��o que pega uma instru��o e manda para a esta��o de reserva adequada se poss�vel */
	
	private static void emitir () {
		/*Leitura da Instru��o a partir da fila de instru��es.*/
		
		Instrucao instAux = filaDeInstrucoes.get(PC);
		int indice;
		int destinoBuffer;
		
		/*Quebra e interpreta��o da instru��o*/
		
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
		//Instru��o tipo R
		case "000000":
			switch(funct){
				//Fun��o Add
				case "100000":
					indice = verificaEstacao ("Add");
					//System.out.println("Indice = "+indice);
					/*Se a esta��o de reserva e o Buffer de reordena��o n�o estiverem cheios,
					 *acontecer� a emiss�o da instru��o.
					 *A vari�vel indice tem uma posicao vazia da Esta��o de Reserva.*/
					if(indice != -1 && !buffer.isFull()) {  
						somaFP[indice].setBusy(true);
						somaFP[indice].setInst("Add");
						somaFP[indice].setDest(buffer.getPosic());	//Onde essa instru��o estar� no Buffer de Reordenamento.
						buffer.adicionaNoBuffer(instAux, regRD);
						/*Ap�s adicionar a instru��o no buffer de reordenamento e na Esta-
						 *��o de reserva, vamos preencher os dados da esta��o de reserva. 
						 *Em primeiro lugar, verificamos se o valor do registrador RS depen-
						 *de do resultado de alguma instru��o. Se n�o depender, apenas copia-
						 *mos o valor para Vj. Se depender, adicionamos que existe uma depen-
						 *d�ncia e adicionamos o �ndice da posi��o do Buffer de Reordena��o
						 *do resultado dependente.*/
						if (registradores[regRS].isBusy()) {
							destinoBuffer = registradores[regRS].getQi();
							if (buffer.getItemBuffer(destinoBuffer).isReady()) {
								somaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
								somaFP[indice].setQj(-1);  //indicar que n�o h� depend�ncia
							}
							else
								somaFP[indice].setQj(destinoBuffer);	
						}
						else {
							somaFP[indice].setVj(registradores[regRS].getVi());
							somaFP[indice].setQj(-1);  //indicar que n�o h� depend�ncia
						}
						/*A mesma coisa para o segundo fator da opera��o.*/
						if (registradores[regRT].isBusy()) {
							destinoBuffer = registradores[regRT].getQi();
							if (buffer.getItemBuffer(destinoBuffer).isReady()) {
								somaFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
								somaFP[indice].setQk(-1);  //indicar que n�o h� depend�ncia
							}
							else
								somaFP[indice].setQk(destinoBuffer);	
						}
						else {
							somaFP[indice].setVk(registradores[regRT].getVi());
							somaFP[indice].setQk(-1);
						}
						/*Aqui adicionamos que o registrador que guardar� o resultado dessa
						 *opera��o est� dependendo do resultado dessa opera��o, guardando
						 *nele o �ndice da posi��o da opera��o no Buffer de Reordena��o. */
						registradores[regRD].setQi(somaFP[indice].getDest());
						registradores[regRD].setBusy(true);
						/*Ap�s emitir a instru��o, deve-se atualizar o valor de PC.*/
						PC+=4;
					}
					System.out.println("add R"+regRD+",R"+regRS+",R"+regRT);
					break;
				//Fun��o Mul
				case "011000":
					indice = verificaEstacao ("Mult");
					System.out.println("mul R"+regRD+",R"+regRS+",R"+regRT);
					if(indice != -1 && !buffer.isFull()) { 
						multFP[indice].setBusy(true);
						multFP[indice].setInst("Mul");
						multFP[indice].setDest(buffer.getPosic());
						buffer.adicionaNoBuffer(instAux, regRD);
						System.out.println("RS = "+regRS);
						if (registradores[regRS].isBusy()) {
							destinoBuffer = registradores[regRS].getQi();
							if (buffer.getItemBuffer(destinoBuffer).isReady()) {
								multFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
								multFP[indice].setQj(-1);  
							}
							else
								multFP[indice].setQj(destinoBuffer);	
						}
						else {
							multFP[indice].setVj(registradores[regRS].getVi());
							multFP[indice].setQj(-1);  
						}
						if (registradores[regRT].isBusy()) {
							destinoBuffer = registradores[regRT].getQi();
							if (buffer.getItemBuffer(destinoBuffer).isReady()) {
								multFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
								multFP[indice].setQk(-1);  
							}
							else
								multFP[indice].setQk(destinoBuffer);	
						}
						else {
							multFP[indice].setVk(registradores[regRT].getVi());
							multFP[indice].setQk(-1);
						}
						registradores[regRD].setQi(multFP[indice].getDest());
						registradores[regRD].setBusy(true);
						PC+=4;
					}
					System.out.println("Mult> Qj = "+multFP[indice].getQj()+" Qk = "+multFP[indice].getQk()+" Vj = "+multFP[indice].getVj()+" Vk = "+multFP[indice].getVk());
					break;
				//Fun��o Sub
				case "100010":
					indice = verificaEstacao ("Add");
					if(indice != -1 && !buffer.isFull()) { 
						somaFP[indice].setBusy(true);
						somaFP[indice].setInst("Sub");
						somaFP[indice].setDest(buffer.getPosic()); 
						buffer.adicionaNoBuffer(instAux, regRD);
						if (registradores[regRS].isBusy()) {  
							destinoBuffer = registradores[regRS].getQi();
							if (buffer.getItemBuffer(destinoBuffer).isReady()) {
								somaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
								somaFP[indice].setQj(-1);  
							}
							else
								somaFP[indice].setQj(destinoBuffer);	
						}
						else {
							somaFP[indice].setVj(registradores[regRS].getVi());
							somaFP[indice].setQj(-1);
						}
						if (registradores[regRT].isBusy()) {
							destinoBuffer = registradores[regRT].getQi();
							if (buffer.getItemBuffer(destinoBuffer).isReady()) {
								somaFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
								somaFP[indice].setQk(-1); 
							}
							else
								somaFP[indice].setQk(destinoBuffer);	
						}
						else {
							somaFP[indice].setVk(registradores[regRT].getVi());
							somaFP[indice].setQk(-1);
						}
						registradores[regRD].setQi(somaFP[indice].getDest());
						registradores[regRD].setBusy(true);
						PC+=4;
					}
					System.out.println("sub R"+regRD+",R"+regRS+",R"+regRT);
					break;
				//Fun��o Nop
				case "000000":
					System.out.println("nop");
					PC+=4;
					break;
			};
			break;
		//Instru��o Addi
		case "001000":
			indice = verificaEstacao ("Add");
			//System.out.println("Indice = "+indice);
			if(indice != -1 && !buffer.isFull()) { 
				//System.out.println("Instru��o addi");
				somaFP[indice].setBusy(true);
				somaFP[indice].setInst("Addi");
				somaFP[indice].setDest(buffer.getPosic());
				buffer.adicionaNoBuffer(instAux, regRT);
				if (registradores[regRS].isBusy()) {  
					destinoBuffer = registradores[regRS].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQj(-1);  
					}
					else
						somaFP[indice].setQj(destinoBuffer);
				}
				else {
					somaFP[indice].setVj(registradores[regRS].getVi());
					somaFP[indice].setQj(-1);
				}
				somaFP[indice].setQk(-1);
				somaFP[indice].setA(decImm);
				registradores[regRT].setQi(somaFP[indice].getDest());
				registradores[regRT].setBusy(true);
				PC+=4;
			}
			System.out.println("addi R"+regRT+",R"+regRS+","+decImm);
			break;
		//Instru��o Beq
		case "000101":
			indice = verificaEstacao("Add");
			if(indice != -1 && !buffer.isFull()){
				somaFP[indice].setBusy(true);
				somaFP[indice].setInst("Beq");
				somaFP[indice].setDest(buffer.getPosic()); 
				buffer.adicionaNoBuffer(instAux, -1);
				if (registradores[regRS].isBusy()) {  
					destinoBuffer = registradores[regRS].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQj(-1);  
					}
					else
						somaFP[indice].setQj(destinoBuffer);	
				}
				else {
					somaFP[indice].setVj(registradores[regRS].getVi());
					somaFP[indice].setQj(-1);
				}
				if (registradores[regRT].isBusy()) {
					destinoBuffer = registradores[regRT].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQk(-1); 
					}
					else
						somaFP[indice].setQk(destinoBuffer);	
				}
				else {
					somaFP[indice].setVk(registradores[regRT].getVi());
					somaFP[indice].setQk(-1);
				}
				somaFP[indice].setA(PC+4);
				PC+=4 + decImm;
			}
			System.out.println("beq R"+regRT+",R"+regRS+","+decImm);
			break;
		//Instru��o Ble
		case "000111":
			indice = verificaEstacao("Add");
			if(indice != -1 && !buffer.isFull()){
				somaFP[indice].setBusy(true);
				somaFP[indice].setInst("Ble");
				somaFP[indice].setDest(buffer.getPosic()); 
				buffer.adicionaNoBuffer(instAux, -1);
				if (registradores[regRS].isBusy()) {  
					destinoBuffer = registradores[regRS].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQj(-1);  
					}
					else
						somaFP[indice].setQj(destinoBuffer);	
				}
				else {
					somaFP[indice].setVj(registradores[regRS].getVi());
					somaFP[indice].setQj(-1);
				}
				if (registradores[regRT].isBusy()) {
					destinoBuffer = registradores[regRT].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQk(-1); 
					}
					else
						somaFP[indice].setQk(destinoBuffer);	
				}
				else {
					somaFP[indice].setVk(registradores[regRT].getVi());
					somaFP[indice].setQk(-1);
				}
				somaFP[indice].setA(PC+4);
				PC+=decImm;
			}
			System.out.println("ble R"+regRT+",R"+regRS+","+decImm);
			break;
		//Instru��o Bne
		case "000100":
			indice = verificaEstacao("Add");
			if(indice != -1 && !buffer.isFull()){
				somaFP[indice].setBusy(true);
				somaFP[indice].setInst("Bne");
				somaFP[indice].setDest(buffer.getPosic()); 
				buffer.adicionaNoBuffer(instAux, -1);
				if (registradores[regRS].isBusy()) {  
					destinoBuffer = registradores[regRS].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQj(-1);  
					}
					else
						somaFP[indice].setQj(destinoBuffer);	
				}
				else {
					somaFP[indice].setVj(registradores[regRS].getVi());
					somaFP[indice].setQj(-1);
				}
				if (registradores[regRT].isBusy()) {
					destinoBuffer = registradores[regRT].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						somaFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
						somaFP[indice].setQk(-1); 
					}
					else
						somaFP[indice].setQk(destinoBuffer);	
				}
				else {
					somaFP[indice].setVk(registradores[regRT].getVi());
					somaFP[indice].setQk(-1);
				}
				somaFP[indice].setA(PC+4);
				PC+=4+decImm;
			}
			System.out.println("bne R"+regRT+",R"+regRS+","+decImm);
			break;
		//Instru��o Jmp
		case "000010":
			System.out.println("jmp "+tA);				
			PC = tA;
			break;
		//Instru��o Lw
		case "100011":
			indice = verificaEstacao ("Load/Store");
			if(indice != -1 && !buffer.isFull()) { 
				cargaFP[indice].setBusy(true);
				cargaFP[indice].setInst("Load");
				cargaFP[indice].setDest(buffer.getPosic()); 
				buffer.adicionaNoBuffer(instAux, regRT);
				if (registradores[regRS].isBusy()) {  
					destinoBuffer = registradores[regRS].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						cargaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
						cargaFP[indice].setQj(-1);  
					}
					else
						cargaFP[indice].setQj(destinoBuffer);	
				}
				else {
					cargaFP[indice].setVj(registradores[regRS].getVi());
					cargaFP[indice].setQj(-1);
				}
				cargaFP[indice].setA(decImm);
				registradores[regRT].setQi(cargaFP[indice].getDest());
				registradores[regRT].setBusy(true);
				PC+=4;
			}
			System.out.println("lw R"+regRT+","+decImm+"(R"+regRS+")");
			break;
		//Instru��o Sw
		case "101011":
			indice = verificaEstacao ("Load/Store");
			if(indice != -1 && !buffer.isFull()) { 
				cargaFP[indice].setBusy(true);
				cargaFP[indice].setInst("Store");
				cargaFP[indice].setDest(buffer.getPosic()); 
				buffer.adicionaNoBuffer(instAux, -1);
				if (registradores[regRS].isBusy()) {  
					destinoBuffer = registradores[regRS].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						cargaFP[indice].setVj(buffer.getItemBuffer(destinoBuffer).getValor());
						cargaFP[indice].setQj(-1);  
					}
					else
						cargaFP[indice].setQj(destinoBuffer);	
				}
				else {
					cargaFP[indice].setVj(registradores[regRS].getVi());
					cargaFP[indice].setQj(-1);
				}
				if (registradores[regRT].isBusy()) {
					destinoBuffer = registradores[regRT].getQi();
					if (buffer.getItemBuffer(destinoBuffer).isReady()) {
						cargaFP[indice].setVk(buffer.getItemBuffer(destinoBuffer).getValor());
						cargaFP[indice].setQk(-1); 
					}
					else
						cargaFP[indice].setQk(destinoBuffer);	
				}
				else {
					cargaFP[indice].setVk(registradores[regRT].getVi());
					cargaFP[indice].setQk(-1);
				}
				cargaFP[indice].setA(decImm);
				PC+=4;
			}
			System.out.println("sw R"+regRT+","+decImm+"(R"+regRS+")");
			break;
		default:
			System.out.println("Comando n�o interpretado!!!");
			break;
		}
		
	}
	
	private static void executar () {
		for (int m = 0; m < buffer.getTamanho(); m++) {
			celulaDeReordenacao aux = buffer.getItemBuffer(m);
			/*A execu��o aqui se trata apenas de decrementar o tempo necess�rio para realizar
			 *a instru��o. Percorre-se todo o buffer de reordena��o verificando quem est� em
			 *estado de execu��o. */
			if(aux.isBusy() && aux.getEstado() == "Executando" && aux.getTempoDeExecucao()==0){
				calculaValor(m);
				buffer.setEstado(m, "Gravando");
				buffer.setTempoExecucao(m, 1);
			}
		}
		
	}
	
	private static void calculaValor(int posBuffer) {
		
		for (int m = 0; m < somaFP.length; m++) {
			if(somaFP[m].isBusy() && somaFP[m].getDest()==posBuffer){
				if(somaFP[m].getInst()=="Add")
					buffer.setValor(posBuffer, somaFP[m].getVj()+somaFP[m].getVk());
				else if(somaFP[m].getInst()=="Sub" || somaFP[m].getInst()=="Bne" || somaFP[m].getInst()=="Ble" || somaFP[m].getInst()=="Beq")
					buffer.setValor(posBuffer, somaFP[m].getVj()-somaFP[m].getVk());
				else if(somaFP[m].getInst()=="Addi")
					buffer.setValor(posBuffer, somaFP[m].getVj()+somaFP[m].getA());
				return;
			}
		}
		
		for (int m = 0; m < multFP.length; m++) {
			if(multFP[m].isBusy() && multFP[m].getDest()==posBuffer){
				if(multFP[m].getInst()=="Mul")
					buffer.setValor(posBuffer, multFP[m].getVj()*multFP[m].getVk());
				return;
			}
		}
		
		for (int m = 0; m < cargaFP.length; m++) {
			if(cargaFP[m].isBusy() && cargaFP[m].getDest()==posBuffer){
				if(cargaFP[m].getInst()=="Load"){
					cargaFP[m].setA(cargaFP[m].getA()+cargaFP[m].getVj());
					buffer.setValor(posBuffer, MEM[cargaFP[m].getA()]);
				}
				else if(cargaFP[m].getInst()=="Store")
					buffer.setValor(posBuffer, cargaFP[m].getVj()+cargaFP[m].getA());
				return;
			}
		}
	}

	private static void gravar() {
		/* Pega a primeira instru��o no buffer de reordena��o com o status gravando e 
		 * joga o seu valor no barramento juntamente com o destino  
		 */
		int posicBuffer;
		for (int m = 0; m < buffer.getTamanho(); m++) {
			posicBuffer = (buffer.getInicio()+m)%buffer.getTamanho();
			celulaDeReordenacao aux = buffer.getItemBuffer(posicBuffer);
			if (aux.isBusy() && aux.getEstado() == "Gravando" && aux.getTempoDeExecucao() == 0){
				System.out.println("Gravando B"+posicBuffer);
				buffer.setReady(posicBuffer,true);
				if (buffer.getItemBuffer(posicBuffer).getInstrucao().getInstrucao().substring(0, 6) != "101011")
					liberaEstacao(posicBuffer);
				barramentoDeDadosComum.setBusy(true);
				barramentoDeDadosComum.setDado(aux.getValor());
				/*Aqui � setado a posi��o do Buffer de Reordena��o que cont�m o resultado da instru��o.*/
				barramentoDeDadosComum.setLocal((buffer.getInicio()+m)%buffer.getTamanho());
				buffer.setEstado(posicBuffer, "Consolidando");
				buffer.setTempoExecucao(posicBuffer, 1);
				break;
			}
		}
		if (barramentoDeDadosComum.isBusy()) {
			/* Percorre as esta��es de reserva para verificar se o dado no barramento � 
			 * utilizado para retirar alguma depend�ncia de alguma esta��o.
			 * Caso as depend�ncias deixem de existir, o estado da instru��o passa de 
			 * emitida para executando.
			 */
			for (int m = 0; m < somaFP.length; m++) {
				if (somaFP[m].getQj() != -1 && somaFP[m].getQj() == barramentoDeDadosComum.getLocal()){
					somaFP[m].setVj(barramentoDeDadosComum.getDado());
					somaFP[m].setQj(-1);
				}
				if (somaFP[m].getQk() != -1 && somaFP[m].getQk() == barramentoDeDadosComum.getLocal()){
					somaFP[m].setVk(barramentoDeDadosComum.getDado());
					somaFP[m].setQk(-1);
				}
				if(buffer.getItemBuffer(somaFP[m].getDest()).getEstado()=="Emitida" && somaFP[m].getQj() == -1 && somaFP[m].getQk() == -1)
					if (buffer.getItemBuffer(somaFP[m].getDest()).getTempoDeExecucao() == 0){
						buffer.setEstado(somaFP[m].getDest(),"Executando");
						buffer.setTempoExecucao(m, 1);
					}
			}
			
			for (int m = 0; m < multFP.length; m++) {
				if (multFP[m].getQj() != -1 && multFP[m].getQj() == barramentoDeDadosComum.getLocal()){
					multFP[m].setVj(barramentoDeDadosComum.getDado());
					multFP[m].setQj(-1);
				}
				if (multFP[m].getQk() != -1 && multFP[m].getQk() == barramentoDeDadosComum.getLocal()){
					multFP[m].setVk(barramentoDeDadosComum.getDado());
					multFP[m].setQk(-1);
				}
				if(buffer.getItemBuffer(multFP[m].getDest()).getEstado()=="Emitida" && multFP[m].getQj() == -1 && multFP[m].getQk() == -1)
					if (buffer.getItemBuffer(multFP[m].getDest()).getTempoDeExecucao() == 0){
						buffer.setEstado(multFP[m].getDest(), "Executando");
						buffer.setTempoExecucao(m, 3);
					}
			}
			
			for (int m = 0; m < cargaFP.length; m++) {
				if (cargaFP[m].getQj() != -1 && cargaFP[m].getQj() == barramentoDeDadosComum.getLocal()){
					cargaFP[m].setVj(barramentoDeDadosComum.getDado());
					cargaFP[m].setQj(-1);
				}
				if (cargaFP[m].getQk() != -1 && cargaFP[m].getQk() == barramentoDeDadosComum.getLocal()){
					cargaFP[m].setVk(barramentoDeDadosComum.getDado());
					cargaFP[m].setQk(-1);
				}
				if (buffer.getItemBuffer(cargaFP[m].getDest()).getEstado()=="Emitida" && cargaFP[m].getQk() == -1){
					if (cargaFP[m].getInst() == "Store")
						if (buffer.getItemBuffer(cargaFP[m].getDest()).getTempoDeExecucao() == 0) {
							buffer.setEstado(cargaFP[m].getDest(), "Executando");
							buffer.setTempoExecucao(m, 4);
						}
					else if (cargaFP[m].getInst() == "Load" && cargaFP[m].getQj() == -1)
						if (buffer.getItemBuffer(cargaFP[m].getDest()).getTempoDeExecucao() == 0) {
							buffer.setEstado(cargaFP[m].getDest(), "Executando");
							buffer.setTempoExecucao(m, 4);
						}
				}
			}
		}
		for (int m = 0; m < somaFP.length; m++) {
			//System.out.println("somaFP["+m+"] - Estado = "+buffer.getItemBuffer(somaFP[m].getDest()).getEstado()+" - Qj = "+somaFP[m].getQj()+" - Qk = "+somaFP[m].getQk()+" - Busy = "+somaFP[m].isBusy());
			if(buffer.getItemBuffer(somaFP[m].getDest()).getEstado()=="Emitida" && somaFP[m].getQj() == -1 && somaFP[m].getQk() == -1 && buffer.getItemBuffer(somaFP[m].getDest()).getTempoDeExecucao() == 0){
				//System.out.println("Mudan�a de Estado");
				buffer.setEstado(somaFP[m].getDest(),"Executando");
				buffer.setTempoExecucao(m, 1);
			}
		}
		
		for (int m = 0; m < multFP.length; m++) {
			//System.out.println("multFP["+m+"] - Estado = "+buffer.getItemBuffer(somaFP[m].getDest()).getEstado()+" - Qj = "+somaFP[m].getQj()+" - Qk = "+somaFP[m].getQk()+" - Busy = "+somaFP[m].isBusy());
			if(buffer.getItemBuffer(multFP[m].getDest()).getEstado()=="Emitida" && multFP[m].getQj() == -1 && multFP[m].getQk() == -1 && buffer.getItemBuffer(multFP[m].getDest()).getTempoDeExecucao() == 0){
				buffer.setEstado(multFP[m].getDest(), "Executando");
				buffer.setTempoExecucao(m, 3);
			}
		}
		
		for (int m = 0; m < cargaFP.length; m++) {
			if (buffer.getItemBuffer(cargaFP[m].getDest()).getEstado()=="Emitida" && cargaFP[m].getQk() == -1 && buffer.getItemBuffer(cargaFP[m].getDest()).getTempoDeExecucao() == 0){
				if (cargaFP[m].getInst() == "Store")
					buffer.setEstado(cargaFP[m].getDest(), "Executando");
				else if (cargaFP[m].getInst() == "Load" && cargaFP[m].getQj() == -1)
					buffer.setEstado(cargaFP[m].getDest(), "Executando");
				buffer.setTempoExecucao(m, 4);
			}
		}
	}
	
	private static void liberaEstacao(int posic) {
		for (int m = 0; m < somaFP.length; m++)
			if (somaFP[m].getDest() == posic) {
				somaFP[m].setBusy(false);
				return;
			}
		
		for (int m = 0; m < multFP.length; m++)
			if (multFP[m].getDest() == posic) {
				multFP[m].setBusy(false);
				return;
			}
		
		for (int m = 0; m < cargaFP.length; m++)
			if (cargaFP[m].getDest() == posic) {
				cargaFP[m].setBusy(false);
				return;
			}
	}
	
	private static void consolidar() {
		System.out.println("Valor de R3 = "+registradores[3].getVi());
		/* Pega a primeira instru��o no buffer de reordena��o com o status consolidando e 
		 * grava o seu valor no registrador destino.  
		 */
		celulaDeReordenacao aux = buffer.getItemBuffer(buffer.getInicio());
		if (aux.isBusy() && aux.getEstado() == "Consolidando" && aux.getTempoDeExecucao() == 0){
			if(aux.getInstrucao().getInstrucao().substring(0, 6) == "000101"){
				if (aux.getValor() == 0)
					buffer.removeDoBuffer();
				else {
					PC = consultaEstacao(aux.getDestino()).getA();
					limpaTudo ();
				}
			}
			else if(aux.getInstrucao().getInstrucao().substring(0, 6) == "000111"){
				if (aux.getValor() <= 0)
					buffer.removeDoBuffer();
				else {
					PC = consultaEstacao(aux.getDestino()).getA();
					limpaTudo ();
				}
			}
			else if(aux.getInstrucao().getInstrucao().substring(0, 6) == "000100"){
				if (aux.getValor() != 0)
					buffer.removeDoBuffer();
				else {
					PC = consultaEstacao(aux.getDestino()).getA();
					limpaTudo ();
				}
			}
			else if (aux.getInstrucao().getInstrucao().substring(0,6) == "101011") {
				MEM[aux.getDestino()] = aux.getValor();
				buffer.removeDoBuffer();
			}
			else {
				System.out.println("Entrou aqui R3 = "+registradores[3].getVi()+" Instru��o = "+aux.getInstrucao().getInstrucao());
				consolidaValor(aux.getValor(),buffer.getInicio());
				buffer.removeDoBuffer();
			}
			barramentoDeDadosComum.setBusy(false);
		}
		System.out.println("Valor de R3 depois = "+registradores[3].getVi());
	}

	private static void consolidaValor(int valor, int i) {
		for(int m=1; m<registradores.length; m++){
			if(registradores[m].getQi() == i){
				registradores[m].setVi(valor);
				registradores[m].setQi(-1);
				registradores[m].setBusy(false);
				return;
			}
		}
	}

	public static EstacaoDeReserva consultaEstacao (int dest) {

		for (int m = 0; m < somaFP.length; m++)
			if (somaFP[m].getDest() == dest)
				return somaFP[m];
						
		for (int m = 0; m < multFP.length; m++)
			if (multFP[m].getDest() == dest)
				return multFP[m];
		
		for (int m = 0; m < cargaFP.length; m++)
			if (cargaFP[m].getDest() == dest)
				return cargaFP[m];
		
		return null;
		
	}
	
	public static void limpaTudo () {
		
		for (int m = 0; m < somaFP.length; m++)
			somaFP[m].setBusy(false);

		for (int m = 0; m < multFP.length; m++)
			multFP[m].setBusy(false);

		for (int m = 0; m < cargaFP.length; m++)
			cargaFP[m].setBusy(false);
		
		for (int m = 0; m < buffer.getTamanho(); m++)
			buffer.setBusy(m, false);
		
	}
	
	public static void main(String[] args) {
		
		/* Inicializa��es do Programa */
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
		
		/* Emiss�o */
		
		preencheFilaDeInstrucoes ();
		
		while(filaDeInstrucoes.containsKey(PC)){
			buffer.decTempoDeExecucao();
			System.out.println("PC = "+PC);
			emitir ();
			executar ();
			gravar ();
			consolidar ();
			//buffer.imprimeTodosOsValores();
			clock++;
		}
		while(!buffer.isEmpty()){
			buffer.decTempoDeExecucao();
			System.out.println("PC = "+PC);
			executar ();
			gravar ();
			consolidar ();
			clock++;
		}
		System.out.println("Clocks: " + clock );
		for(int i=0; i<registradores.length; i++)
			System.out.println("Valor de R"+i+" = "+ registradores[i].getVi());
		buffer.imprimeTodosOsValores();
	}

}
