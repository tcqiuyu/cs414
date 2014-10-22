package cs414.a1.yqiu;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class ProjectTest extends TestCase {

	private Company testCompany;
	private Set<Qualification> workerQual1, workerQual2, projectQual1,
			projectQual2;

	protected void setUp() {
		testCompany = new Company("TestCompany");
		workerQual1 = new HashSet<Qualification>();
		workerQual2 = new HashSet<Qualification>();

		workerQual1.add(new Qualification("Qualification 1"));
		workerQual1.add(new Qualification("Qualification 2"));

		workerQual2.add(new Qualification("Qualification 3"));
		workerQual2.add(new Qualification("Qualification 4"));

		projectQual1 = new HashSet<Qualification>();
		projectQual2 = new HashSet<Qualification>();

		projectQual1.add(new Qualification("Qualification 1"));
		projectQual1.add(new Qualification("Qualification 2"));
		projectQual1.add(new Qualification("Qualification 3"));
		projectQual1.add(new Qualification("Qualification 4"));

		projectQual2.add(new Qualification("Qualification 2"));
		projectQual2.add(new Qualification("Qualification 3"));
	}

	@Test
	public void testMissingQualifications() {
		Project testProj = testCompany.createProject("test proj",
				new HashSet<Worker>(), projectQual1, ProjectSize.medium);
		Worker worker = testCompany.createWorker("Worker", workerQual1);
		testProj.addMember(worker);
		Set<Qualification> missingQual = new HashSet<Qualification>();
		missingQual.add(new Qualification("Qualification 3"));
		missingQual.add(new Qualification("Qualification 4"));
		assertEquals(missingQual, testProj.missingQualifications());

	}

	public void testMissingQualificationsReturnEmpty() {
		Project testProj = testCompany.createProject("test proj",
				new HashSet<Worker>(), projectQual1, ProjectSize.medium);
		Worker worker = testCompany.createWorker("Worker", projectQual1);
		testProj.addMember(worker);
		Set<Qualification> missingQual = new HashSet<Qualification>();
		assertEquals(missingQual, testProj.missingQualifications());
	}

	public void testIsHelpful() {
		Project testProj = testCompany.createProject("test proj",
				new HashSet<Worker>(), projectQual1, ProjectSize.medium);
		Set<Qualification> workerQual1 = new HashSet<Qualification>();
		Set<Qualification> workerQual2 = new HashSet<Qualification>();
		workerQual1.add(new Qualification("Qualification 1"));
		workerQual2.add(new Qualification("Qualification 5"));
		Worker helpfulWorker = testCompany
				.createWorker("Worker 1", workerQual1);
		Worker notHelpfulWorker = testCompany.createWorker("Worker 2",
				workerQual2);

		assertTrue(testProj.isHelpful(helpfulWorker));
		assertTrue(!testProj.isHelpful(notHelpfulWorker));
	}

	public void testToString() {
		Project testProj0 = testCompany.createProject("proj 0",
				new HashSet<Worker>(), new HashSet<Qualification>(),
				ProjectSize.small);
		Project testProj1 = testCompany.createProject("proj 1",
				new HashSet<Worker>(), projectQual1, ProjectSize.medium);
		Worker worker1 = testCompany.createWorker("worker 1", workerQual1);
		Worker worker2 = testCompany.createWorker("worker 2", workerQual1);

		testProj1.addMember(worker1);
		testProj1.addMember(worker2);
		testCompany.start(testProj1);
		assertEquals("proj 0:0:planned",testProj0.toString());
		assertEquals("proj 1:2:active",testProj1.toString());
		
	}
}
