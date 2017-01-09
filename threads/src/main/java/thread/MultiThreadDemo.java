package thread2;

public class MultiThreadDemo {

	public static final int ARRAY_SIZE = 1000;
	public static final int THREAD_NUM = 20;


	public static void main(String[] args) {

		Thread[] threads = new Thread[THREAD_NUM];
		int multyplier = ARRAY_SIZE/THREAD_NUM;

		long beginMillis = System.currentTimeMillis();

		for(int i =0; i < THREAD_NUM; i++) {
			threads[i] = new Thread(new SomeBigTask(multyplier * i, multyplier * (i + 1)));
			threads[i].start();
		}

		boolean allDone = false;
		while(!allDone) {
			allDone = true;
			for(Thread thread : threads) {
				allDone = allDone && !thread.isAlive();
			}
		}
		System.out.println("process run " + (System.currentTimeMillis() - beginMillis) + " millis");
	}
}

class SomeBigTask implements Runnable{
	int beginIndex;
	int endIndex;

	public SomeBigTask(int beginIndex, int endIndex) {
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}

	public void run() {
		bigMethod(beginIndex, endIndex);
	}

	public void bigMethod(int beginIndex, int endIndex) {
		for(int i = beginIndex; i < endIndex; i++) {
			for (long j = 0; j < 25_000_000l; j++) {
				int u = 0;
				u += j;
			}
			if(i % 10 == 0) {
				System.out.println(i);
			}
		}
	}
}