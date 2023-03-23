package controller;

import java.util.concurrent.Semaphore;

public class ExercicioIngressoPrincipal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		for (int i=0; i <=300; i++){
			Thread ingresso = new IngressoThread (i,semaforo);
			ingresso.start();
		}

	}

}
