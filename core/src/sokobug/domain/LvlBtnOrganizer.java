package sokobug.domain;

import sokobug.screens.ChooseLevelScreen;

public class LvlBtnOrganizer {
	public static MenuButton[] linkButtons(ChooseLevelScreen screen, int buttonsPerRow, int buttonsPerCollum, int numberOfButtons) {
		MenuButton[] button = new MenuButton[numberOfButtons];
		
		for (int i = 0; i < numberOfButtons; i++)
			button[i] = new MenuButton(screen.game, Integer.toString(i + 1),
					MenuButton.LEVEL, screen.uiSkin, "default-level-btn");
		
		for (int i = 0; i < numberOfButtons; i++) {
			int btnLevel = i + 1; 											//The actual level of the button
			
			//Setting the upper neighbor
			if (btnLevel - buttonsPerRow <= 0) { 							//The button is on the first line
				int upPos = i + (buttonsPerCollum - 1)  * buttonsPerRow; 	//The theoretical position of the up neighbor
				
				if (upPos + 1 > numberOfButtons) 							//The position may exceed the actual numberOfButtons,
					upPos = numberOfButtons - 1;					    	//so we set it to the position of the last button.
					
				button[i].setUpNeighbour(button[upPos]);
			}
			else 															//The button is not on the first line
				button[i].setUpNeighbour(button[i - buttonsPerRow]);
			
			//Setting the bottom neighbor
			if (btnLevel + buttonsPerRow > numberOfButtons) {				//The button is on the last line or no element exists under it
				int downPos = i - (buttonsPerCollum - 1) * buttonsPerRow;   //The position of the down neighbor
				
				if (downPos < 0)
					downPos = numberOfButtons - 1;
				
				button[i].setDownNeighbour(button[downPos]);
			}
			else															//The button is not on the last line
				button[i].setDownNeighbour(button[i + buttonsPerRow]);
			
			//Setting the right neighbor
			if (btnLevel == numberOfButtons)								//We are on the last button
				button[i].setRightNeighbour(button[0]);
			else															//We are not on the last button
				button[i].setRightNeighbour(button[i + 1]);
			
			//Setting the left neighbor
			if (btnLevel == 1) 												//We are on the first button
				button[i].setLeftNeighbour(button[numberOfButtons - 1]);
			else															//We are not on the first button
				button[i].setLeftNeighbour(button[i - 1]);
		}
			
		return button;
	}
}
