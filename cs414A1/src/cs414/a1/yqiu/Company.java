package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

public class Company {

	private String name;

	private Set<Worker> unemployedWorker;
	private Set<Worker> employedWorker;
	private Set<Project> projSet;

	public Company(String nm) {
		name = nm;
		unemployedWorker = new HashSet<Worker>();
		employedWorker = new HashSet<Worker>();
		projSet = new HashSet<Project>();

	}

	public void hire(Worker w) {
		employedWorker.add(w);
		unemployedWorker.remove(w);
		for (Project p : projSet) {
			if (p.getSize() == ProjectSize.small && p.isHelpful(w)) {
				p.addMember(w);

			}
		}
	}

	public void fire(Worker w) {
		employedWorker.remove(w);
		unemployedWorker.add(w);
		for (Project p : projSet) {
			if (p.getMemberSet().contains(w)) {
				p.removeMember(w);

			}
		}
	}

	public void start(Project p) {
		if (p.getMemberSet().size() >= 1) {
			p.setStatus(ProjectStatus.active);
		}
	}

	public void finish(Project p) {
		if (p.getStatus().equals(ProjectStatus.active)) {
			p.setStatus(ProjectStatus.finished);
			for (Worker w : p.getMemberSet()) {
				p.removeMember(w);
			}
		}
	}

	public Set<Worker> getEmployedWorker() {
		return employedWorker;
	}

	public Set<Worker> getUnemployedWorker() {
		return unemployedWorker;
	}

	public Set<Project> getProjectSet() {
		return projSet;
	}

	private void addToWorkerSet(Worker w) {
		unemployedWorker.add(w);
	}

	public Worker createWorker(String nn, Set<Qualification> qs) {
		if (qs.isEmpty()) {
			return null;
		} else {
			Worker newWorker = new Worker(this, nn, qs);
			this.addToWorkerSet(newWorker);
			return newWorker;
		}
	}

	public Project createProject(String n, Set<Worker> ws,
			Set<Qualification> qs, ProjectSize size) {
		if (qs.isEmpty()) {
			return null;
		} else {
			Project tmpProject = new Project(n, size, ProjectStatus.planned, qs);
			if (!ws.isEmpty()) {
				for (Worker w : ws) {
					tmpProject.addMember(w);
				}
			}
			projSet.add(tmpProject);
			return tmpProject;
		}
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name + ":" + this.getEmployedWorker().size() + ":"
				+ this.getProjectSet().size();
	}
}
