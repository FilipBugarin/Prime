package test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.SwingWorker;

public class PrimeNumbersWorker extends SwingWorker<List<Integer>, Integer>{

	private int numberOfPrimes;
	private Consumer<List<Integer>> chunksProcessor;
	private Runnable onDone;
	
	public PrimeNumbersWorker(int numberOfPrimes, Consumer<List<Integer>> chunksProcessor,Runnable onDone) {
		this.numberOfPrimes=numberOfPrimes;
		this.chunksProcessor=chunksProcessor;
		this.onDone=onDone;
	}
	
	
	@Override
	protected List<Integer> doInBackground() throws Exception{
		
		List<Integer> primeNumbers = new LinkedList<>();
		
		int count=0;
		int number =2;
		boolean isPrime;
		
		while(count < numberOfPrimes) {
			isPrime=true;
			
			int limit = (int) Math.sqrt(number);
			for(int divisor = 2; divisor <= limit;divisor++) {
				if(number % divisor == 0) {
					isPrime=false;
					break;
				}
			}
			
			if(isPrime) {
				count++;
				
				primeNumbers.add(number);
				
				//Set progress
				setProgress(100*count/numberOfPrimes);
				//Publish prime number
				publish(number);
				
				//slow down simulated
				try {
				Thread.sleep(500);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			number++;
		}
		
		return primeNumbers;
	}
	
	@Override
	protected void process(List<Integer> chunks) {
		chunksProcessor.accept(chunks);		//ovisno o tome kakav consumer posaljemo u konstruktor
	}
	
	@Override
	protected void done() {
		onDone.run();						//ovisno o tome kakav runable posaljemo u konstruktor
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
