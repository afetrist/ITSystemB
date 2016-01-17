package pl.edu.agh.to2.common;

import java.util.List;

public interface IWorkerProvider {
	public List<IWorker> loadWorkers(List<Integer> idList, boolean included);
}
