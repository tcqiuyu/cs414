package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class CompanyTest extends TestCase {

	private Company testCompany;
	private Set<Qualification> workerQualificationSet1;
	private Set<Qualification> workerQualificationSet2;
	private Set<Qualification> projectQualificationSet1;
	private Set<Qualification> projectQualificationSet2;

	protected void setUp() {
		testCompany = new Company("TestCompany");
		workerQualificationSet1 = new HashSet<Qualification>();
		workerQualificationSet2 = new HashSet<Qualification>();

		workerQualificationSet1.add(new Qualification("Qualification 1"));
		workerQualificationSet1.add(new Qualification("Qualification 2"));

		workerQualificationSet2.add(new Qualification("Qualification 1"));
		workerQualificationSet2.add(new Qualification("Qualification 3"));

		projectQualificationSet1 = new HashSet<Qualification>();
		projectQualificationSet2 = new HashSet<Qualification>();

		projectQualificationSet1.add(new Qualification("Qualification 1"));
		projectQualificationSet1.add(new Qualification("Qualification 2"));
		projectQualificationSet1.add(new Qualification("Qualification 3"));
		projectQualificationSet1.add(new Qualification("Qualification 4"));

		projectQualificationSet2.add(new Qualification("Qualification 2"));
		projectQualificationSet2.add(new Qualification("Qualification 3"));
	}

	@Test
	// test hire method
	public void testSimpleHire() {
		Worker testWorker = testCompany.createWorker("Worker",
				workerQualificationSet1);
		testCompany.hire(testWorker);

		assertTrue(testCompany.getEmployedWorker().contains(testWorker));
		assertTrue(!testCompany.getUnemployedWorker().contains(testWorker));
	}

	public void testHireEmployed() {
		Worker testWorker = testCompany.createWorker("Worker",
				workerQualificationSet1);
		testCompany.hire(testWorker);
		testCompany.hire(testWorker);
		Set<Worker> testWorkerSet = new HashSet<Worker>();
		testWorkerSet.add(testWorker);
		assertEquals(testWorkerSet, testCompany.getEmployedWorker());
	}

	public void testHireAddtoSmallProj() {
		Project smallProj1 = testCompany.createProject("small proj 1",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);
		Project smallProj2 = testCompany.createProject("small proj 2",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);
		Set<Qualification> otherQs = new HashSet<Qualification>();
		otherQs.add(new Qualification("Other Qualification"));
		Project smallProj3 = testCompany.createProject("small proj 3",
				new HashSet<Worker>(), otherQs, ProjectSize.small);
		Project medProj = testCompany.createProject("med proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.medium);
		Project bigProj = testCompany.createProject("large proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.big);

		Worker testWorker = testCompany.createWorker("Worker",
				workerQualificationSet1);
		testCompany.hire(testWorker);

		assertTrue(smallProj1.getMemberSet().contains(testWorker));
		assertTrue(smallProj2.getMemberSet().contains(testWorker));
		assertTrue(!smallProj3.getMemberSet().contains(testWorker));
		assertTrue(!medProj.getMemberSet().contains(testWorker));
		assertTrue(!bigProj.getMemberSet().contains(testWorker));

	}

	public void testHireSatisfiedRemainQual() {
		Project smallProj1 = testCompany.createProject("small proj 1",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.small);
		Project smallProj2 = testCompany.createProject("small proj 2",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);

		Worker worker = testCompany.createWorker("Worker1",
				projectQualificationSet2);

		testCompany.hire(worker);

		assertEquals(ProjectStatus.planned, smallProj1.getStatus());
		assertEquals(ProjectStatus.active, smallProj2.getStatus());
	}

	// here or in qualification test?
	public void testHireOverSatisfiedQual() {
		Project smallProj = testCompany.createProject("small proj",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);
		Set<Qualification> workerQual = projectQualificationSet2;
		workerQual.add(new Qualification("Additional Qualification"));
		Worker worker = testCompany.createWorker("worker", workerQual);
		testCompany.hire(worker);

		assertEquals(ProjectStatus.active, smallProj.getStatus());

	}

	// test fire method
	public void testSimpleFire() {
		Worker worker = testCompany.createWorker("Worker",
				workerQualificationSet1);
		testCompany.hire(worker);
		testCompany.fire(worker);

		assertTrue(!testCompany.getEmployedWorker().contains(worker));
		assertTrue(testCompany.getUnemployedWorker().contains(worker));
	}

	public void testFireUnemployed() {
		Worker worker = testCompany.createWorker("Worker",
				workerQualificationSet1);

		testCompany.fire(worker);

		assertTrue(!testCompany.getEmployedWorker().contains(worker));
		assertTrue(testCompany.getUnemployedWorker().contains(worker));
	}

	public void testFireRemoveFromProjList() {
		Worker worker = testCompany.createWorker("Worker",
				workerQualificationSet1);
		Project project = testCompany.createProject("Project",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.small);
		testCompany.hire(worker);
		assertTrue(project.getMemberSet().contains(worker));
		testCompany.fire(worker);
		assertTrue(!project.getMemberSet().contains(worker));
	}

	public void testFireProjContinue() {
		Project smallProj = testCompany.createProject("small proj",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);
		Worker betterWorker = testCompany.createWorker("betterWorker",
				projectQualificationSet2);
		Worker firedWorker = testCompany.createWorker("firedWorker",
				workerQualificationSet1);

		testCompany.hire(betterWorker);
		testCompany.hire(firedWorker);
		assertEquals(ProjectStatus.active, smallProj.getStatus());
		testCompany.fire(firedWorker);
		assertEquals(ProjectStatus.active, smallProj.getStatus());
	}

	public void testFireProjSuspend() {
		Project smallProj = testCompany.createProject("small proj",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);
		Worker worker = testCompany.createWorker("Worker",
				projectQualificationSet2);

		testCompany.hire(worker);
		assertEquals(ProjectStatus.active, smallProj.getStatus());
		testCompany.fire(worker);
		assertEquals(ProjectStatus.suspended, smallProj.getStatus());
	}

	// test start method
	public void testStartPlanned() {
		Project plannedProj = testCompany.createProject("planned proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.big);
		testCompany.start(plannedProj);
		assertEquals(ProjectStatus.active, plannedProj.getStatus());
	}

	public void testStartStarted() {
		Project plannedProj = testCompany.createProject("planned proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.big);
		testCompany.start(plannedProj);
		testCompany.start(plannedProj);
		assertEquals(ProjectStatus.active, plannedProj.getStatus());

	}

	public void testStartSuspended() {
		Project smallProj = testCompany.createProject("small proj",
				new HashSet<Worker>(), projectQualificationSet2,
				ProjectSize.small);
		Worker worker = testCompany.createWorker("betterWorker",
				projectQualificationSet2);
		testCompany.hire(worker);
		testCompany.fire(worker);
		testCompany.start(smallProj);
		assertEquals(ProjectStatus.active, smallProj.getStatus());
	}

	// test finish method
	public void testFinishActive() {
		Project testProj = testCompany.createProject("test proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.big);
		testCompany.start(testProj);
		testCompany.finish(testProj);

		assertEquals(ProjectStatus.finished, testProj.getStatus());
	}

	public void testFinishSuspended() {
		Project testProj = testCompany.createProject("test proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.big);
		Worker worker = testCompany.createWorker("worker",
				projectQualificationSet1);
		testCompany.hire(worker);
		testProj.addMember(worker);
		testProj.removeMember(worker);
		testCompany.finish(testProj);
		assertEquals(ProjectStatus.suspended, testProj.getStatus());
	}

	public void testFinishPlanned() {
		Project testProj = testCompany.createProject("test proj",
				new HashSet<Worker>(), projectQualificationSet1,
				ProjectSize.big);
		testCompany.finish(testProj);
		assertEquals(ProjectStatus.planned, testProj.getStatus());
	}

	// test createworker
	public void testSimpleCreateWorker() {
		Worker testWorker = testCompany.createWorker("Worker",
				workerQualificationSet1);

		assertEquals("Worker", testWorker.getNickName());
		assertTrue(testCompany.getUnemployedWorker().contains(testWorker));

		for (Qualification qual : testWorker.getQualification()) {
			assertTrue(qual.getWorkerSet().contains(testWorker));
		}

	}

	// test create project
	public void testSimpleCreateProject() {
		Set<Worker> testWorkerSet = new HashSet<Worker>();
		Worker testWorker1 = testCompany.createWorker("Worker 1",
				workerQualificationSet1);
		Worker testWorker2 = testCompany.createWorker("Worker 2",
				workerQualificationSet2);
		testWorkerSet.add(testWorker1);
		testWorkerSet.add(testWorker2);
		Set<Qualification> testProjectRequirement = new HashSet<Qualification>();
		testProjectRequirement.add(new Qualification("Qualification 1"));
		testProjectRequirement.add(new Qualification("Qualification 2"));
		testProjectRequirement.add(new Qualification("Qualification 3"));
		testProjectRequirement.add(new Qualification("Qualification 4"));
		Project testProject = testCompany.createProject("Test Project",
				testWorkerSet, testProjectRequirement, ProjectSize.small);

		// add to worker list
		assertTrue(testCompany.getUnemployedWorker().contains(testWorker1));
		assertTrue(testCompany.getUnemployedWorker().contains(testWorker2));
		// add to project list
		assertTrue(testCompany.getProjectSet().contains(testProject));
		// add to project member list
		assertTrue(testProject.getMemberSet().contains(testWorker1));
		assertTrue(testProject.getMemberSet().contains(testWorker2));
		// check requirement
		assertEquals(testProjectRequirement,
				testProject.getRequiredQualification());
		// check misc
		assertEquals(ProjectStatus.planned, testProject.getStatus());
		assertEquals(ProjectSize.small, testProject.getSize());
		assertEquals("Test Project", testProject.getName());
	}

	public void testToString(){
		assertEquals("TestCompany:0:0",testCompany.toString());
		testCompany.createProject("proj 1", new HashSet<Worker>(), projectQualificationSet1, ProjectSize.big);
		testCompany.createProject("proj 2", new HashSet<Worker>(), projectQualificationSet2, ProjectSize.small);
		Worker w1 = testCompany.createWorker("worker 1", workerQualificationSet1);
		Worker w2 = testCompany.createWorker("worker 2", workerQualificationSet2);
		testCompany.hire(w1);
		testCompany.hire(w2);
		assertEquals("TestCompany:2:2", testCompany.toString());
	}
}
