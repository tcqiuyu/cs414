package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class WorkerTest extends TestCase {
	private Company testCompany;
	private Set<Qualification> workerQual1;
	private Set<Qualification> workerQual2;
	private Set<Qualification> projectQual;
	private Project medProj1, medProj2, bigProj1, bigProj2;
	private Worker worker1, worker2;

	protected void setUp() {
		testCompany = new Company("TestCompany");
		workerQual1 = new HashSet<Qualification>();
		workerQual2 = new HashSet<Qualification>();

		workerQual1.add(new Qualification("Qualification 1"));
		workerQual1.add(new Qualification("Qualification 2"));

		workerQual2.add(new Qualification("Qualification 3"));
		workerQual2.add(new Qualification("Qualification 4"));

		projectQual = new HashSet<Qualification>();

		projectQual.add(new Qualification("Qualification 1"));
		projectQual.add(new Qualification("Qualification 2"));
		projectQual.add(new Qualification("Qualification 3"));
		projectQual.add(new Qualification("Qualification 4"));

		worker1 = testCompany.createWorker("worker1", workerQual1);
		worker2 = testCompany.createWorker("worker2", workerQual2);

		medProj1 = testCompany.createProject("med proj1",
				new HashSet<Worker>(), projectQual, ProjectSize.medium);
		medProj2 = testCompany.createProject("med proj2",
				new HashSet<Worker>(), projectQual, ProjectSize.medium);
		bigProj1 = testCompany.createProject("big proj2",
				new HashSet<Worker>(), projectQual, ProjectSize.big);
		bigProj2 = testCompany.createProject("big proj2",
				new HashSet<Worker>(), projectQual, ProjectSize.big);
	}

	@Test
	public void testIsOverloaded() {
		medProj1.addMember(worker1);
		medProj2.addMember(worker1);
		bigProj1.addMember(worker1);

		medProj1.addMember(worker2);
		bigProj1.addMember(worker2);
		bigProj2.addMember(worker2);

		assertTrue(worker1.isOverLoaded());
		assertTrue(!worker2.isOverLoaded());
	}
	
	public void testToString(){
		Worker worker0 = testCompany.createWorker("worker 0", new HashSet<Qualification>());
		assertEquals("worker 0:0:0:0", worker0.toString());
		medProj1.addMember(worker1);
		medProj2.addMember(worker1);
		bigProj1.addMember(worker1);
		worker1.setSalary(10000);
		assertEquals("worker1:3:2:10000", worker1.toString());
		
	}
	
}
