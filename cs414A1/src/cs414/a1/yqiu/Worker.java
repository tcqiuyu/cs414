package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

public class Worker {

	private String nickName;
	private int salary;

	private Company company;
	private Set<Qualification> qualSet;

	public Worker(Company c, String nn, Set<Qualification> q) {

		nickName = nn;
		qualSet = q;
		company = c;
		registerQualification();

	}

	private Set<Project> getProjectSet() {
		Set<Project> companyProjSet = company.getProjectSet();
		Set<Project> ps = new HashSet<Project>();
		for (Project p : companyProjSet) {
			if (p.getMemberSet().contains(this)) {
				ps.add(p);
			}
		}
		return ps;
	}

	public void setSalary(int sal) {
		salary = sal;
	}

	public String getNickName() {
		return nickName;
	}

	public Set<Qualification> getQualification() {
		return qualSet;
	}

	private void registerQualification() {
		for (Qualification q : qualSet) {
			q.addWorker(this);
		}
	}

	public boolean isOverLoaded() {
		Set<Project> workerProjSet = this.getProjectSet();
		int tmp = 0;
		for (Project p : workerProjSet) {
			if (p.getSize().equals(ProjectSize.medium)) {
				tmp = tmp + 2;
			} else if (p.getSize().equals(ProjectSize.big)) {
				tmp++;
			}
		}
		if (tmp > 4) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		return nickName.hashCode();
	}

	public boolean equals(Object o) {
		while (o instanceof Worker) {
			Worker aWorker = (Worker) o;
			return this.getNickName() == aWorker.getNickName();
		}
		return false;
	}

	public String toString() {
		int projNum = this.getProjectSet().size();
		int qualNum = this.getQualification().size();
		return nickName + ":" + projNum + ":" + qualNum + ":" + salary;
	}
}
