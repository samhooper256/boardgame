package base;

import fxutils.Images;

public class Die extends ImagePane {
	
	public Die() {
		super(Images.DIE0);
		this.setOnMouseClicked(eh -> {
			roll();
		});
	}
	
	public void roll() {
		System.out.println("roll");
	}

}
