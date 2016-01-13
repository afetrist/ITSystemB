package pl.edu.agh.to2.model;

import java.util.List;

public interface IWorkerProvider {
	public List<IWorker> loadWorkers(List<Integer> idList, boolean included);
}
