package controller;

import java.util.concurrent.Semaphore;

public class IngressoThread extends Thread {
	
	public static Integer qtdIngressos =100;
	private Integer idPessoa;
	private Integer tempoEsperaLogin;
	private Integer tempoEsperaCompra;
	private Integer qtdIngressosRequisitados;
	private Semaphore semaforo;
	private Integer numeroAleatorio;
	
	public IngressoThread (Integer idPessoa, Semaphore semaforo){
		this.idPessoa = idPessoa;
		this.semaforo = semaforo;
		this.tempoEsperaLogin=0;
		this.tempoEsperaCompra=0;
		this.qtdIngressosRequisitados=0;
	}
	
	public void run (){
		if (loginNoSistema()) {
			if (processoCompra()) {
				try {
					semaforo.acquire();
					validacaoCompra();
				} catch (InterruptedException e) {
					//
					e.printStackTrace();
				} finally {
					semaforo.release();
				}
			}
		}
	}
	
	public boolean loginNoSistema(){
		
		numeroAleatorio=(int) ((Math.random() * 2001) + 500);
		this.tempoEsperaLogin = numeroAleatorio;
		try {
		  sleep (this.tempoEsperaLogin);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		if (this.tempoEsperaLogin>=1000) {
			System.out.println("Usuário id " + idPessoa + " recebeu timeout após " + this.tempoEsperaLogin + " ms.");
			return false;
		} else {
			System.out.println("Usuário id " + idPessoa + " Conseguiu logar no sistema após " + this.tempoEsperaLogin + " ms.");
			numeroAleatorio=(int) (1 + Math.random() * 5);
			this.qtdIngressosRequisitados = numeroAleatorio;
			return true;
		}
	}

	public boolean processoCompra() {
		
		numeroAleatorio=(int) ((Math.random() * 3001) + 1000);
		this.tempoEsperaCompra = numeroAleatorio;
		try {
		  sleep (this.tempoEsperaCompra);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		if (this.tempoEsperaCompra>=2500) {
			System.out.println("Usuário id " + idPessoa + " teve a sessão finalizada após " + this.tempoEsperaCompra + "ms.");
			return false;
		}
		return true;
	}
	
	public boolean validacaoCompra() {
		if (qtdIngressos >= this.qtdIngressosRequisitados){
			qtdIngressos -= this.qtdIngressosRequisitados;
			System.out.println("Usuário de id " + idPessoa + " consegiu comprar " + this.qtdIngressosRequisitados + ". Restam apenas: " + qtdIngressos + "ingressos disponíveis");
			return true;
		}else {
			System.out.println("Usuário de id " + idPessoa + " não consegiu comprar " + this.qtdIngressosRequisitados+ ". ingressos pois restam apenas: " + qtdIngressos + "ingressos disponíveis");
		return false;
		}
	}
}
