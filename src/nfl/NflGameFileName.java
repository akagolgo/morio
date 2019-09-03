package nfl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * main usage of this class is method parse, then, generateName
 * @author mori
 *
 */
public class NflGameFileName  {

	//private File file;
	private String date;
	private int year; // 4 digit year
	private int week;
	private boolean preseason; 
	private boolean condensed; 
	private String ext;
	
	private Team visitor; 
	private Team home;
	
	public NflGameFileName() {
		super();
		week = 0;
		year = 0;
		preseason = false;
		condensed = false;
	}
	
	public void rename(File oldfile) throws IOException {
		
		Path source = oldfile.toPath();
		Files.move(source, source.resolveSibling(generateName()));
		
		// below rename doesnt work on windows
//		File newfile = new File(oldfile.getCanonicalPath() + File.separatorChar + generateName());
//		return oldfile.renameTo(newfile);		
	}
	
	public boolean isWellFormed(String fileName) {
		
		return Pattern.matches("\\d{4}.w\\d{2}.[A-Z]{2,3}@[A-Z]{2,3}.\\w{3}", fileName) ||
				Pattern.matches("\\d{4}.w\\d{2}.[A-Z]{2,3}@[A-Z]{2,3}.con.\\w{3}", fileName) ||
				Pattern.matches("\\d{4}.ps\\d.[A-Z]{2,3}@[A-Z]{2,3}.\\w{3}", fileName) ||
				Pattern.matches("\\d{4}.ps\\d.[A-Z]{2,3}@[A-Z]{2,3}.\\w{2}", fileName) ||
				Pattern.matches("\\d{4}.ps\\d.[A-Z]{2,3}@[A-Z]{2,3}.con.\\w{3}", fileName);
	}
	
	public boolean isATarget(File file) {
		
		if(file.isDirectory() || file.isHidden())
			return false;

		if(!file.isFile())
			return false;
		
		String fileName = file.getName().toUpperCase();
		if(fileName.endsWith("MP4") ||fileName.endsWith("MKV") ||fileName.endsWith("AVI") ||fileName.endsWith("TS") )
			return true; 
		else
			return false;
	}
	
	/**
	 * Build names like 2015.w04.NYG@DAL.mkv
	 * @return
	 */
	public String generateName() {
		StringBuilder sb = new StringBuilder();
		NumberFormat nf = new DecimalFormat("00");
		
		if(year == 0) // guess the year based on today's date
			year = new GregorianCalendar().get(Calendar.YEAR);
		sb.append(year);
		
		if(week != 0)
			sb.append(".w").append(nf.format(week));
		else
			sb.append(".").append(date==null?"null":date);
		
		sb.append(".").append(visitor==null?"null":visitor.name()).append("@").append(home==null?"null":home.name());
		
		sb.append(".").append(condensed?"con.":"").append(ext);
		
		return sb.toString();
	}

	public void parseExt(String fileName) {
		this.ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public void parseCondensed(String fileName) {
		
		if(fileName.toUpperCase().indexOf("CONDENSED") > -1)
			condensed = true;
		else
			condensed = false;
	}
	
	/**
	 * main method of this class
	 * @param fileName
	 */
	public void parse(String fileName) {
//		String fileName = file.getName();
		
		week = parseWeek(fileName);
		//if(week == 0)
			date = parseDate(fileName);
		parseTeams(fileName);
		parseCondensed(fileName);
		parseExt(fileName);
	}
	
	public void parseTeams(String st) {

		Team[] teams = Team.values();
		Team t1 = null, t2 = null;
		int id1 = -1, id2 = -1;
		String s = st.toUpperCase();
		
		// find nicknames first
		for(int i= 0; i<teams.length; i++) {
			if(s.contains(teams[i].nickname.toUpperCase())) {
				if(t1 == null) {
					t1 = teams[i];
					id1 = s.indexOf(teams[i].nickname.toUpperCase());
				} else {
					t2 = teams[i];
					id2 = s.indexOf(teams[i].nickname.toUpperCase());
					break;
				}
			}
		}
		
		// if no nicknames was found, find city
		if(t1 == null || t2 == null) {
			for(int i= 0; i<teams.length; i++) {
				if(s.contains(teams[i].city.toUpperCase())) {
					if(t1 == null) {
						t1 = teams[i];
						id1 = s.indexOf(teams[i].city.toUpperCase());
					} else if(!t1.equals(teams[i])){ // if nnot the first assigned taem
						t2 = teams[i];
						id2 = s.indexOf(teams[i].city.toUpperCase());
						break;
					}
				}
			}
		}

		// if no nicknames or city was found, team is in proper format, just wrong place
		if(t1 == null || t2 == null) {
			for(int i= 0; i<teams.length; i++) {
				if(s.contains(teams[i].name().toUpperCase())) {
					if(t1 == null) {
						t1 = teams[i];
						id1 = s.indexOf(teams[i].name().toUpperCase());
					} else if(!t1.equals(teams[i])){ // if nnot the first assigned taem
						t2 = teams[i];
						id2 = s.indexOf(teams[i].name().toUpperCase());
						break;
					}
				}
			}
		}

		// check who's hosting
		if(id1 < id2) {
			this.visitor = t1;
			this.home = t2;
		} else {
			this.visitor = t2;
			this.home = t1;		
		}
			
			
			
		
			
	}
	
	public int parseWeek(String s) {
		
		Pattern p = Pattern.compile("[Ww][Kk]? ?\\d\\d?");
		Matcher m = p.matcher(s);
		
		if(m.find()) {
			Pattern p2 = Pattern.compile("\\d\\d?");
			Matcher m2 = p2.matcher(m.group());		
			m2.find();
			return Integer.parseInt(m2.group());
		} else
			return 0;
	}
	
	/**
	 * parse int val of year and return string val of date
	 * @param s filename
	 * @return
	 */
	public String parseDate(String s) {

		year = parseYear(s);
		return parseDateString(s);
	}
	
	/**
	 * parse int val of year
	 * @param s filename
	 * @return
	 */
	public int parseYear(String s) {
		
		// find 4 consecutive integers
		Pattern p = Pattern.compile("\\d{4}");
		Matcher m = p.matcher(s);
		String token;
		int ret = 0;
		
		if(m.find()) {
			
			token = m.group();

			// try to guess the int val of year
			try {
				ret = Integer.parseInt(token); // 4 digit year
			} catch(NumberFormatException ex) {
				ret = 0;
			}	
		}
		
		// get 2 digit year from date string. assume year is last two digits (mm-dd-yy)
		if(ret == 0) {
			p = Pattern.compile("\\d{2}.\\d{2}.\\d{2}");
			m = p.matcher(s);
			
			if(m.find()) {
				
				token = m.group();

				// try to guess the int val of year
				try {
					ret = Integer.parseInt(token.substring(6, 2)); // 2 digit year
					ret += 2000;
				} catch(Exception ex) {
					ret = 0;
				}	
			}

		}
		
		return ret;
	}
	
	/**
	 * date is used only for files without week number
	 * @param s filename
	 * @return
	 */
	public String parseDateString(String s) {
		
		Pattern p = Pattern.compile("\\d{2,4}.\\d{2}.\\d{2}");
		Matcher m = p.matcher(s);
		String ret = null;
		
		if(m.find())	
			ret = m.group();
		
		return ret;
	}
	
	//===============================================================
	
//	/**
//	 * @return the file
//	 */
//	public File getFile() {
//		return file;
//	}
//	/**
//	 * @param file the file to set
//	 */
//	public void setFile(File file) {
//		this.file = file;
//	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the week
	 */
	public int getWeek() {
		return week;
	}
	/**
	 * @param week the week to set
	 */
	public void setWeek(int week) {
		this.week = week;
	}
	/**
	 * @return the preseason
	 */
	public boolean isPreseason() {
		return preseason;
	}
	/**
	 * @param preseason the preseason to set
	 */
	public void setPreseason(boolean preseason) {
		this.preseason = preseason;
	}
	/**
	 * @return the visitor
	 */
	public Team getVisitor() {
		return visitor;
	}
	/**
	 * @param visitor the visitor to set
	 */
	public void setVisitor(Team visitor) {
		this.visitor = visitor;
	}
	/**
	 * @return the home
	 */
	public Team getHome() {
		return home;
	}
	/**
	 * @param home the home to set
	 */
	public void setHome(Team home) {
		this.home = home;
	}
	
	
	
	
}
