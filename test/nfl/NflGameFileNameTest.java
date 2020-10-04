package nfl;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class NflGameFileNameTest  {

	private String names[] = new String[] {
			"NFL WK 13 11-30-2014 Giants at Jaguars (Condensed) (1280x720) [Phr0stY].mkv",                // 0
			"NFL.W13.14.11.30_Chargers@Ravens.mkv",
			"2014.w08.Eagles-Cardinals.720p.mkv",
			"NFL.W15.14.12.14_Steelers@Falcons.mkv",
			"2014.w08.Ravens@Bengals.mkv",
			"2014.w12.Denver Broncos - Miami Dolphins 23.11.14.mkv",                                      //5
			"NFL.2014.12.21.Vikings.vs.Dolphins.720p.HDTV.30fps.x264-Reborn4HD.mp4",
			"NFL2014.W15.Cowboys-Eagles.720p.mkv",
			"NFL WK 14 12-08-2014 Steelers at Bengals (Condensed) (1280x720) [Phr0stY].mkv",
			"2014.w07.Seahawks@Rams.mkv",
			"San Francisco 49ers - San Diego Chargers 20.12.14.mkv",                                       //10
			"2014.w12.New Orleans Saints - Baltimore Ravens 24.11.14.mkv",
			"NFL.W14.14.12.07_Colts@Browns.mkv",
			"NFL.PS.14.08.16_Giants@Colts.mkv",
			"NFL WK 15 12-14-2014 Bengals at Browns (Condensed) (1280x720) [Phr0stY].mkv",
			"2014.w12.New York Giants - Dallas Cowboys 23.11.14.mkv",                                      //15
			"NFL.W14.14.12.08_Falcons@Packers.mkv",
			"NFL.2014.12.21.Bills.vs.Raiders.540p.HDTV.30fps.x264-Reborn4HD.mp4",
			"2014.w07.Saints.at.Lions.540p.mkv",
			"NFL.W15.14.12.15_Saints@Bears.mkv",
			"2014.w11.Seahawks@Chiefs.mkv",                                                                //20
			"nfl.2014.11.30.saints.vs.steelers.720p.hdtv.x264-bajskorv.mkv",
			"NFL.2014.08.07.Pre.Week.01.Cincinnati Bengals @ Kansas City Chiefs.720p.mkv",
			"2014.w05.Browns.at.Titans.720p.mkv",
			"2014.w11.Patriots@Colts.mkv",
			"NFL WK 14 12-08-2014 Jets at Vikings (Condensed) (1280x720) [Phr0stY].mkv",                   //25
			"NFL WK 13 11-30-2014 Redskins at Colts (Condensed) (1280x720) [Phr0stY].mkv",
			"NFL.PS.14.08.16_Cardinals@Vikings.mkv",
			"Cincinnati Bengals - Denver Broncos 22.12.14.mkv",                                             //28
			"2014.w02.San Francisco 49's - Chicago Bears 14.09.14.mkv",
			"NFL-2015-10-04_PHI@WAS.720_RS.W4.mkv"};                                   //30
	

	public NflGameFileNameTest() { 
		super(); 
	} 

	/** 
	 * Sets up the test fixture. 
	 * 
	 * Called before every test case method. 
	 */ 
	@Before
	public void setUp() throws Exception {
		
//		File targetDir = new File("M:\\video\\NFL\\unwatched");
//		
//		File[] targetFiles = targetDir.listFiles();
//		for(int i = 0; i < targetFiles.length; i++)
//			System.out.println("\"" + targetFiles[i].getName() + "\",");
	} 




	/** 
	 * Tears down the test fixture. 
	 * 
	 * Called after every test case method. 
	 */ 
	@After
	public void tearDown() throws Exception { 
	} 

	@Test
	public void testRename() throws Exception { 

		File f = new File(names[0]);
		File f2 = new File("2014.w13.NYG@JAX.con.mkv");
		
		if(f.exists())
			f.delete();
		
		if(f2.exists())
			f2.delete();
		
		Assert.assertFalse(f.exists());
		Assert.assertTrue(f.createNewFile());
		Assert.assertTrue(f.exists());
		Assert.assertTrue(f.canWrite());
		
		NflGameFileName g = new NflGameFileName(f);
		g.parse();
		g.rename();
		Assert.assertFalse(f.exists());

		
		Assert.assertTrue(f2.exists());

		Assert.assertTrue(f2.delete());
		Assert.assertFalse(f2.exists());
	} 

	@Test
	public void testIsWellFormed() throws Exception { 

		Assert.assertFalse(new NflGameFileName(names[0]).isFilenameFormalized());
		Assert.assertTrue(new NflGameFileName("2014.w13.NYG@JAX.mkv").isFilenameFormalized());
	} 

	/**
	 * Test new filenames here
	 * @throws Exception
	 */
	@Test
	public void testParse() throws Exception { 

		NflGameFileName g = new NflGameFileName(names[0]);
		g.parse();
		Assert.assertEquals("2014.w13.NYG@JAX.con.mkv", g.generateName());
		
		g = new NflGameFileName(names[29]);
		g.parse();
		Assert.assertEquals("2014.w02.SF@CHI.mkv", g.generateName());
		
		g = new NflGameFileName(names[30]);
		g.parse(); // NFL-2015-10-04_PHI@WAS.720_RS.W4.mkv
		Assert.assertEquals("2015.w04.PHI@WAS.mkv", g.generateName());
		
//		for(int i = 1; i < names.length; i++) {
//			g.parse(names[i]);
//			System.out.println(g.generateName());
//		}
	} 


	@Test
	public void testParseTeams() throws Exception { 

		NflGameFileName g = new NflGameFileName();

		g.parseTeams(names[0]);
		Assert.assertEquals(Team.NYG, g.getVisitor());
		Assert.assertEquals(Team.JAX, g.getHome());
		
		g.parseTeams(names[21]);
		Assert.assertEquals(Team.NO, g.getVisitor());
		Assert.assertEquals(Team.PIT, g.getHome());
		
		g.parseTeams(names[29]);
		Assert.assertEquals(Team.SF, g.getVisitor());
		Assert.assertEquals(Team.CHI, g.getHome());
		
		g.parseTeams(names[30]); // NFL-2015-10-04_PHI@WAS.720_RS.W4.mkv
		Assert.assertEquals(Team.PHI, g.getVisitor());
		Assert.assertEquals(Team.WAS, g.getHome());
		
	} 

	@Test
	public void testParseWeek() throws Exception { 

		NflGameFileName g = new NflGameFileName();

		int i = 0;
		Assert.assertEquals(13, g.parseWeek(names[i++]));
		Assert.assertEquals(13, g.parseWeek(names[i++]));
		Assert.assertEquals(8, g.parseWeek(names[i++]));
		Assert.assertEquals(15, g.parseWeek(names[i++]));
		Assert.assertEquals(8, g.parseWeek(names[i++]));
		Assert.assertEquals(12, g.parseWeek(names[i++])); //5
		Assert.assertEquals(0, g.parseWeek(names[i++]));
		Assert.assertEquals(15, g.parseWeek(names[i++]));
		Assert.assertEquals(14, g.parseWeek(names[i++]));
		Assert.assertEquals(7, g.parseWeek(names[i++]));
		Assert.assertEquals(0, g.parseWeek(names[i++])); //10
		Assert.assertEquals(12, g.parseWeek(names[i++]));
		Assert.assertEquals(14, g.parseWeek(names[i++]));
		Assert.assertEquals(0, g.parseWeek(names[i++]));
		Assert.assertEquals(15, g.parseWeek(names[i++]));
		Assert.assertEquals(12, g.parseWeek(names[i++])); //15
		Assert.assertEquals(14, g.parseWeek(names[i++]));
		Assert.assertEquals(0, g.parseWeek(names[i++]));
		Assert.assertEquals(7, g.parseWeek(names[i++]));
		Assert.assertEquals(15, g.parseWeek(names[i++]));
		Assert.assertEquals(11, g.parseWeek(names[i++])); //20
		Assert.assertEquals(0, g.parseWeek(names[i++]));
		Assert.assertEquals(0, g.parseWeek(names[i++]));
		Assert.assertEquals(5, g.parseWeek(names[i++]));
		Assert.assertEquals(11, g.parseWeek(names[i++]));
		Assert.assertEquals(14, g.parseWeek(names[i++])); //25
		Assert.assertEquals(13, g.parseWeek(names[i++]));
		Assert.assertEquals(0, g.parseWeek(names[i++]));
		Assert.assertEquals(0, g.parseWeek(names[i++])); //28
		Assert.assertEquals(2, g.parseWeek(names[i++])); //
		Assert.assertEquals(4, g.parseWeek(names[i++])); //30
	} 
	/**
	 * date is used only for files without week number
	 * @throws Exception
	 */
	@Test
	public void testParseDate() throws Exception { 

		NflGameFileName g = new NflGameFileName();
		
		Assert.assertEquals("2014.12.21", g.parseDate(names[6]));
		Assert.assertEquals("20.12.14", g.parseDate(names[10])); //10
		Assert.assertEquals("14.08.16", g.parseDate(names[13]));
		Assert.assertEquals("2014.12.21", g.parseDate(names[17]));
//		Assert.assertEquals("", g.parseDate(names[21]));
//		Assert.assertEquals("", g.parseDate(names[22]));
//		Assert.assertEquals("", g.parseDate(names[27]));
//		Assert.assertEquals("", g.parseDate(names[28])); //28
	} 
}
