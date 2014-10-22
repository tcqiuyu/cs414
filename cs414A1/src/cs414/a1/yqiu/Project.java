package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

public class Project {

	private String name;
	private ProjectSize size;
	private ProjectStatus status;
	private Set<Worker> projectMember;
	private Set<Qualification> requiredQualification, currentQualification;

	public Project(String nm, ProjectSize sz, ProjectStatus st,
			Set<Qualification> qs) {
		name = nm;
		size = sz;
		status = st;
		projectMember = new HashSet<Worker>();
		requiredQualification = new HashSet<Qualification>();
		currentQualification = new HashSet<Qualification>();
		requiredQualification = qs;
	}

	public void addMember(Worker w) {
		projectMember.add(w);
		this.getCurrentQualSet();
		if (this.missingQualifications().isEmpty()) {
			this.setStatus(ProjectStatus.active);
		}
	}

	public void removeMember(Worker w) {
		projectMember.remove(w);
		this.getCurrentQualSet();
		if (this.getStatus().equals(ProjectStatus.active)
				&& !this.missingQualifications().isEmpty()) {
			this.setStatus(ProjectStatus.suspended);
		}
	}

	public String getName() {
		return name;
	}

	public Set<Worker> getMemberSet() {
		return projectMember;
	}

	public Set<Qualification> getRequiredQualification() {
		return requiredQualification;
	}

	public ProjectSize getSize() {
		return size;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public Set<Qualification> getCurrentQualSet() {
		currentQualification = new HashSet<Qualification>();
		for (Worker w : projectMember) {
			for (Qualification q : w.getQualification())
				currentQualification.add(q);
		}
		return currentQualification;
	}

	// should I???
	public void setStatus(ProjectStatus pStatus) {
		status = pStatus;
	}

	public Set<Qualification> missingQualifications() {
		// missing qualification is a reference of required qualification!!!!
		// Set<Qualification> missingQualifications = requiredQualification;
		Set<Qualification> missingQualification = new HashSet<Qualification>();
		for (Qualification p : requiredQualification) {
			if (!this.getCurrentQualSet().contains(p)) {
				missingQualification.add(p);
			}
		}
		return missingQualification;
	}

	public boolean isHelpful(Worker w) {
		for (Qualification q : this.missingQualifications()) {
			if (w.getQualification().contains(q)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		int memNum = this.getMemberSet().size();

		String status = "";
		if(this.getStatus().equals(ProjectStatus.active)){
			status = "active";
		}
		else if(this.getStatus().equals(ProjectStatus.planned)){
			status = "planned";

		}
		else if(this.getStatus().equals(ProjectStatus.finished)){
			status = "finished";
		}
		else if(this.getStatus().equals(ProjectStatus.suspended)){
			status = "suspended";

		}
		return name+":"+memNum+":"+status;
	}
}
