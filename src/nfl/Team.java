package nfl;

/**
 * 
 * @author mori
 *
 */
public enum Team {

	BUF("AFC","E","Buffalo","Bills"), 
	MIA("AFC","E","Miami","Dolphins"), 
	NE("AFC","E","New England","Patriots"), 
	NYJ("AFC","E","New York","Jets"), 
	BAL("AFC","N","Baltimore","Ravens"), 
	CIN("AFC","N","Cincinnati","Bengals"), 
	CLE("AFC","N","Cleveland","Browns"), 
	PIT("AFC","N","Pittsburgh","Steelers"), 
	HOU("AFC","S","Houston","Texans"), 
	IND("AFC","S","Indianapolis","Colts"), 
	JAX("AFC","S","Jacksonville","Jaguars"), 
	TEN("AFC","S","Tennessee","Titans"), 
	DEN("AFC","W","Denver","Broncos"), 
	KC("AFC","W","Kansas City","Chiefs"), 
	LV("AFC","W","Las Vegas","Raiders"), 
	LAC("AFC","W","Los Angeles","Chargers"), 
	DAL("NFC","E","Dallas","Cowboys"), 
	NYG("NFC","E","New York","Giants"), 
	PHI("NFC","E","Philadelphia","Eagles"), 
	WAS("NFC","E","Washington","Football"), 
	CHI("NFC","N","Chicago","Bears"), 
	DET("NFC","N","Detroit","Lions"), 
	GB("NFC","N","Green Bay","Packers"), 
	MIN("NFC","N","Minnesota","Vikings"), 
	ATL("NFC","S","Atlanta","Falcons"), 
	CAR("NFC","S","Carolina","Panthers"), 
	NO("NFC","S","New Orleans","Saints"), 
	TB("NFC","S","Tampa Bay","Buccaneers"), 
	ARI("NFC","W","Arizona","Cardinals"), 
	LAR("NFC","W","Los Angeles","Rams"), 
	SF("NFC","W","San Francisco","49ers"), 
	SEA("NFC","W","Seattle","Seahawks");

	public final String conference;
	public final String division;
	public final String city;
	public final String nickname;
	
	private Team(String conference, String division, String city, String nickname) {
		this.conference = conference;
		this.division = division;
		this.city = city;
		this.nickname = nickname;
	}
}

/*
 * data text used 
 *
AFC|E|Buffalo Bills|Buffalo|Bills|BUF|BUF("AFC","E","Buffalo","Bills"),
AFC|E|Miami Dolphins|Miami|Dolphins|MIA|MIA("AFC","E","Miami","Dolphins"),
AFC|E|New England Patriots|New England|Patriots|NE|NE("AFC","E","New England","Patriots"),
AFC|E|New York Jets|New York|Jets|NYJ|NYJ("AFC","E","New York","Jets"),
AFC|N|Baltimore Ravens|Baltimore|Ravens|BAL|BAL("AFC","N","Baltimore","Ravens"),
AFC|N|Cincinnati Bengals|Cincinnati|Bengals|CIN|CIN("AFC","N","Cincinnati","Bengals"),
AFC|N|Cleveland Browns|Cleveland|Browns|CLE|CLE("AFC","N","Cleveland","Browns"),
AFC|N|Pittsburgh Steelers|Pittsburgh|Steelers|PIT|PIT("AFC","N","Pittsburgh","Steelers"),
AFC|S|Houston Texans|Houston|Texans|HOU|HOU("AFC","S","Houston","Texans"),
AFC|S|Indianapolis Colts|Indianapolis|Colts|IND|IND("AFC","S","Indianapolis","Colts"),
AFC|S|Jacksonville Jaguars|Jacksonville|Jaguars|JAX|JAX("AFC","S","Jacksonville","Jaguars"),
AFC|S|Tennessee Titans|Tennessee|Titans|TEN|TEN("AFC","S","Tennessee","Titans"),
AFC|W|Denver Broncos|Denver|Broncos|DEN|DEN("AFC","W","Denver","Broncos"),
AFC|W|Kansas City Chiefs|Kansas|City Chiefs|KC|KC("AFC","W","Kansas","City Chiefs"),
AFC|W|Oakland Raiders|Oakland|Raiders|OAK|OAK("AFC","W","Oakland","Raiders"),
AFC|W|San Diego Chargers|San Diego|Chargers|SD|SD("AFC","W","San Diego","Chargers"),
NFC|E|Dallas Cowboys|Dallas|Cowboys|DAL|DAL("NFC","E","Dallas","Cowboys"),
NFC|E|New York Giants|New York|Giants|NYG|NYG("NFC","E","New York","Giants"),
NFC|E|Philadelphia Eagles|Philadelphia|Eagles|PHI|PHI("NFC","E","Philadelphia","Eagles"),
NFC|E|Washington Redskins|Washington|Redskins|WAS|WAS("NFC","E","Washington","Redskins"),
NFC|N|Chicago Bears|Chicago|Bears|CHI|CHI("NFC","N","Chicago","Bears"),
NFC|N|Detroit Lions|Detroit|Lions|DET|DET("NFC","N","Detroit","Lions"),
NFC|N|Green Bay Packers|Green Bay|Packers|GB|GB("NFC","N","Green Bay","Packers"),
NFC|N|Minnesota Vikings|Minnesota|Vikings|MIN|MIN("NFC","N","Minnesota","Vikings"),
NFC|S|Atlanta Falcons|Atlanta|Falcons|ATL|ATL("NFC","S","Atlanta","Falcons"),
NFC|S|Carolina Panthers|Carolina|Panthers|CAR|CAR("NFC","S","Carolina","Panthers"),
NFC|S|New Orleans Saints|New Orleans|Saints|NO|NO("NFC","S","New Orleans","Saints"),
NFC|S|Tampa Bay Buccaneers|Tampa Bay|Buccaneers|TB|TB("NFC","S","Tampa Bay","Buccaneers"),
NFC|W|Arizona Cardinals|Arizona|Cardinals|ARI|ARI("NFC","W","Arizona","Cardinals"),
NFC|W|St. Louis Rams|St. Louis|Rams|STL|STL("NFC","W","St. Louis","Rams"),
NFC|W|San Francisco 49ers|San Francisco|49ers|SF|SF("NFC","W","San Francisco","49ers"),
NFC|W|Seattle Seahawks|Seattle|Seahawks|SEA|SEA("NFC","W","Seattle","Seahawks"), 
 *
*/
