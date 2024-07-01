package acsse.csc2a.fmb.threading;

import acsse.csc2a.fmb.generator.E_TASK_SIZE;
import acsse.csc2a.fmb.generator.FireworkDisplayGenerator;

public class GenerateTask implements Runnable{
	private E_TASK_SIZE task_size;
	private String dataRoot;
	
	public GenerateTask(E_TASK_SIZE task_size, String dataRoot) {
		this.task_size = task_size;
		this.dataRoot = dataRoot;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Task " + (i+1) + "/10");
			switch (task_size) {
			case SMALL:
				FireworkDisplayGenerator.GenerateSmallDisplay(dataRoot);
				break;
			case MEDIUM:
				FireworkDisplayGenerator.GenerateMediumDisplay(dataRoot);
				break;
			case LARGE:
				FireworkDisplayGenerator.GenerateLargeDisplay(dataRoot);
				break;
			case GO_ALL_OUT:
				FireworkDisplayGenerator.GenerateGoAllOutDisplay(dataRoot);
				break;
			}
		}
	}
	
}
