import java.util.ArrayList;


//Team : Cessna Skyhawk
//Michael Jordan
//Lucy Byrne
//Fiachra Dunn
//
//



public class YourTeamName implements Bot {

	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	private static int decision;
	private static BoardAPI board;
	private static PlayerAPI player;
	private static DiceAPI dice;
	ColourGroup brownProperty;
	ColourGroup lightBlueProperty;
	ColourGroup pinkProperty;
	ColourGroup orangeProperty;
	ColourGroup redProperty;
	ColourGroup yellowProperty;
	ColourGroup greenProperty;
	ColourGroup darkBlueProperty;

	YourTeamName (BoardAPI board, PlayerAPI player, DiceAPI dice) {
		this.board = board;
		this.player = player;
		this.dice = dice;
		brownProperty = ((Site) board.getProperty(1)).getColourGroup();
		lightBlueProperty = ((Site) board.getProperty(6)).getColourGroup();
		pinkProperty = ((Site) board.getProperty(11)).getColourGroup();
		orangeProperty = ((Site) board.getProperty(16)).getColourGroup();
		redProperty = ((Site) board.getProperty(21)).getColourGroup();
		yellowProperty = ((Site) board.getProperty(26)).getColourGroup();
		greenProperty = ((Site) board.getProperty(31)).getColourGroup();
		darkBlueProperty = ((Site) board.getProperty(37)).getColourGroup();
		return;
	}

	public String getName () {
		return "CessnaSkyhawk";
	}

	public String getCommand () {
		switch (decision){
		case 0 : 
			return "roll";
			
		case 1 :
			return "done";
		case 2 : 
			return "pay rent";
		case 3 :
			return "build";
		case 4 :
			return "demolish";
		case 5 :
			return "mortgage";
		case 6 :
			return "redeem";
		case 7 : 
			return "bankrupt";
		case 8 :
			return "pay"; //in jail
		case 9 : 
			return "card"; //in jail

		default : 
			decision = 1;
			return "roll";
		}
	}

	public String getDecision () {
		// Add your code here
		return "pay";
	}

	public String inJail(){
		if(player.getNumProperties() < 10){
			if(player.getBalance() < 50){
				return "roll"; //Roll because our balance is too low and we will lose the game if we pay out
			}
			else if(player.hasGetOutOfJailCard()){
				return "card"; //use the card to get out for free quickly
			}
			else return "pay"; //pay out to get out ASAP
		}
			
			return "roll";
	}

	//Function tries to build three houses on all properties, from most desired property to least.
	public String considerBuild(){
		int colourIndex = 0;
		String command = "";
		ColourGroup[] colourGroups = {orangeProperty, redProperty, yellowProperty, pinkProperty, greenProperty,
										darkBlueProperty, lightBlueProperty};
		
		//Loop attempts to build on houses from most to least desirable, building one house at a time.
		//
		
		//We attempt to build all houses to level 3, then all houses to level 4, then 5.
		for(int housesToBuild = 3; housesToBuild < 5; housesToBuild++){
			while(colourIndex < 7 ){	//	While Player can afford, and hasn't reached end of colour groups
					
					if(ownsGroup(colourGroups[colourIndex])){									//If player owns group, loop through.
						ArrayList<Site> siteList = colourGroups[colourIndex].getMembers();		//List of properties of this colour group
						for(int j=0; j < siteList.size(); j++){
							Site p = siteList.get(j);
							if(p.getNumHouses() < housesToBuild && player.getBalance() > 500){
								decision = 2;													//Loop back to same function if we succeed in building.
								command = "build " + p.getShortName() + " 1";
								return command;													//Build one house at a time.
							}
						}
						
					}
				
				colourIndex++;		
				}
		}
		
		
			decision = 3; 											//move onto next function
			return command;

	
	}
	


	public int mortgage(int goal){
		ArrayList<Site> sites = {brownProperty, lightBlueProperty, darkBlueProperty, greenProperty, pinkProperty, yellowProperty,  redProperty,  orangeProperty};
		ArrayList<Property> properties = player.getProperties();

		int earned = 0;
		int i;
		int j = 0;

		for(i = 0; i < properties.size(); i++){
			if(properties.get(i).getColourGroup() == sites.get(j)){
				if(countColoursOwned(sites.get(j)) < 2){
					//mortgage
				}	
			}
			j++;
		}
	}

	public int countColoursOwned (Site site) {
		boolean owns = 3;
		ColourGroup colourGroup = site.getColourGroup();
		for (Site s : colourGroup.getMembers()) {
			if (!s.isOwned() || (s.isOwned() && s.getOwner() != this))
				owns--;
		}
		return owns;
	}

	
	public boolean ownsGroup(ColourGroup colour){
		if( player.isGroupOwner(colour.getMembers().get(0)) ){
			
			return true;
		}

		return false;
	}
	
	public boolean threeHousesOrMore(ColourGroup colour){
		ArrayList<Site> propertyList = colour.getMembers();
		
		return false;
	}
}