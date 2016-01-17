package pl.edu.agh.to2.model;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.common.IWorkerProvider;
import pl.edu.agh.to2.model.generator.BetterDataGenerator;

public class SimpleWorkerProvider implements IWorkerProvider {

	@Override
	public List<IWorker> loadWorkers(List<Integer> idList, boolean included) {
		List<IWorker> workers = BetterDataGenerator.generateWorkers(24);
		List<IWorker> result = new ArrayList<>();
		if (included) {
			for (IWorker w : workers) {
				if (idList.contains(w.getId())) {
					result.add(w);
				}
			}
		} else {
			for (IWorker w : workers) {
				if (!idList.contains(w.getId())) {
					result.add(w);
				}
			}
		}
		
		return result;
	}

}
