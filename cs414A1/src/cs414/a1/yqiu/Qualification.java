package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

public class Qualification {

	private String description;

	private Set<Worker> ws;

	public Qualification(String s) {
		ws = new HashSet<Worker>();
		description = s;
	}

	public Set<Worker> getWorkerSet() {
		return ws;
	}

	public void addWorker(Worker w) {
		ws.add(w);
	}

	public void removeWorker(Worker w) {
		ws.remove(w);
	}

	public String description() {
		return description;
	}

	public String toString() {
		return description;
	}

	//need to rewrite hashCode()!!!
	public int hashCode(){
		return description.hashCode();
	}
	public boolean equals(Object o) {
		if (o instanceof Qualification) {
			Qualification tmpQ = (Qualification) o;
			return this.description() == tmpQ.description();
		}
		return false;
	}
}
