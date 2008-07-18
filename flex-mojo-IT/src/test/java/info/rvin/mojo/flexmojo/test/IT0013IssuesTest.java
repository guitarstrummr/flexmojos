package info.rvin.mojo.flexmojo.test;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.integrationtests.AbstractMavenIntegrationTestCase;
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.codehaus.plexus.util.IOUtil;

public class IT0013IssuesTest extends AbstractMavenIntegrationTestCase {

	private void standardIssueTester(String issueNumber) throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/" + issueNumber);
		customIssueTester(testDir, "info.rvin.itest.issues", issueNumber,
				"1.0-SNAPSHOT", "swf", "install");
	}

	private void customIssueTester(File testDir, String groupId,
			String artifactId, String version, String type, String goal)
			throws Exception {
		customIssueTester(testDir, groupId, artifactId, version, type, goal,
				new HashMap<String, String>());
	}

	private void customIssueTester(File testDir, String groupId,
			String artifactId, String version, String type, String goal,
			Map<String, String> args) throws Exception {
		Verifier verifier = new Verifier(testDir.getAbsolutePath(), true);
		verifier.deleteArtifact(groupId, artifactId, version, type);
		verifier.executeGoal(goal, args);
		verifier.verifyErrorFreeLog();
		verifier.resetStreams();
	}

	public void testIssue8_1() throws Exception {
		standardIssueTester("issue-0008-1");
	}

	public void testIssue8_2() throws Exception {
		standardIssueTester("issue-0008-2");
	}

	public void testIssue11() throws Exception {
		standardIssueTester("issue-0011");
	}

	public void testIssue13() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0013");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0013",
				"1.0-SNAPSHOT", "swf", "install");

		File reportDir = new File(testDir, "target/surefire-reports");
		assertEquals(2, reportDir.listFiles().length);
	}

	public void testIssue14() throws Exception {
		try {
			standardIssueTester("issue-0014");
			fail("This test must throw errors");
		} catch (VerificationException e) {
			// expected exception
			// System.out.println("Got required Fail =D");
		}
	}

	public void testIssue15() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0015");
		try {
			customIssueTester(testDir, "info.rvin.itest.issues", "issue-0015",
					"1.0-SNAPSHOT", "swf", "install");
			fail("testing error unit, must fail!");
		} catch (Exception e) {
			// expected exception
		}

		File reportDir = new File(testDir, "target/surefire-reports");
		assertEquals(2, reportDir.listFiles().length);
	}

	public void testIssue17() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0017");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0017",
				"1.0-SNAPSHOT", "swf", "asdoc:asdoc");

		File asdoc = new File(testDir, "target/asdoc");
		assertTrue("asdoc directory must exist", asdoc.isDirectory());
	}

	public void testIssue27() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0027");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0027",
				"1.0-SNAPSHOT", "swf", "asdoc:asdoc");
	}

	public void testIssue29() throws Exception {
		standardIssueTester("issue-0029");
	}

	public void testIssue32() throws Exception {
		standardIssueTester("issue-0032");
	}

	public void testIssue39() throws Exception {
		standardIssueTester("issue-0039");
	}

	public void testIssue43() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0014");
		Map<String, String> args = new HashMap<String, String>();
		args.put("maven.test.failure.ignore", "true");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0014",
				"1.0-SNAPSHOT", "swf", "install", args);
	}

	public void testIssue44() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0044");
		try {
			customIssueTester(testDir, "info.rvin.itest.issues", "issue-0044",
					"1.0-SNAPSHOT", "swf", "asdoc:asdoc");
			fail("testing error unit, must fail!");
		} catch (Exception e) {
			// expected exception
		}
	}

	public void testIssue53() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0014");
		Map<String, String> args = new HashMap<String, String>();
		args.put("maven.test.skip", "true");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0014",
				"1.0-SNAPSHOT", "swf", "install", args);

		args = new HashMap<String, String>();
		args.put("skipTests", "true");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0014",
				"1.0-SNAPSHOT", "swf", "install", args);
	}

	public void testIssue61() throws Exception {
		standardIssueTester("issue-0061");
	}

	public void testIssue67() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0067");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0067",
				"1.0-SNAPSHOT", "swf", "asdoc:asdoc");
	}

	public void testIssue68() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0068");
		customIssueTester(testDir, "info.rvin.itest.issues", "issue-0068",
				"1.0-SNAPSHOT", "swf", "asdoc:asdoc");
	}

	public void testIssue69() throws Exception {
		final String[] trusts = new String[] {
				"AppData/Roaming/Macromedia/Flash Player/#Security/FlashPlayerTrust",
				"Application Data/Macromedia/Flash Player/#Security/FlashPlayerTrust",
				".macromedia/Flash_Player/#Security/FlashPlayerTrust",
				"Library/Preferences/Macromedia/Flash Player/#Security/FlashPlayerTrust" };

		File userHome = new File(System.getProperty("user.home"));

		File mavenCfg = null;
		for (String folder : trusts) {
			File fpTrustFolder = new File(userHome, folder);
			if (fpTrustFolder.exists() && fpTrustFolder.isDirectory()) {
				mavenCfg = new File(fpTrustFolder, "maven.cfg");
				if (mavenCfg.exists()) {
					mavenCfg.delete();
				}
				break;
			}
		}

		standardIssueTester("issue-0069");

		File testDir = ResourceExtractor.simpleExtractResources(getClass(),
				"/issues/issue-0069");

		assertTrue("Flex-mojos should generate maven.cfg: "
				+ mavenCfg.getAbsolutePath(), mavenCfg.exists());

		String cfg = IOUtil.toString(new FileReader(mavenCfg));

		assertTrue("Flex-mojos should write trust localtion", cfg
				.contains(testDir.getAbsolutePath()));
	}

	public void testIssue70() throws Exception {
		standardIssueTester("issue-0070");
	}

}
