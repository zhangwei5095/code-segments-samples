package net.fqjj.www;

public class MyFirstThread extends Thread {

	private int i;

	@Override
	public void run() {

		for (; i < 10; i++) {
			System.out.println(this.getName() + "===" + i);
		}
	}
	
	public static void main(String[] args) {
		new MyFirstThread().start();
		new MyFirstThread().start();
		System.out.println(Thread.currentThread().getName());
	}
	
	
	

}
