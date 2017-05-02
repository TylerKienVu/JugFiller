package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class Wait implements Activity{
	private Script script;
	private Area bankarea = new Area(new Position(3250,3424,0),new Position(3257,3419,0));
	private RS2Object bank;
	public String id = "Wait";
	public Wait(){
		
	}
	public Wait(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run() throws InterruptedException{
		if(Script.random(1,10) == 10){
			script.camera.moveYaw(script.camera.getYawAngle() + Script.random(40,100));
		}
		Script.sleep(Script.random(2000,3000));
		if(!bankarea.contains(script.myPosition())){
			script.walking.webWalk(new Position(3253,3420,0));
		}
		else if(bankarea.contains(script.myPosition())){
			this.bank = script.objects.closest("Bank booth");
			if(!this.script.getBank().isOpen() && bank != null){ //if bank not open and bank exists
				if(bank.interact("Bank")){
					new ConditionalSleep(5000){
						@Override
						public boolean condition() throws InterruptedException {
							return script.bank.isOpen();
						}
					}.sleep();
				}
			}
			if(script.bank.isOpen() && !script.inventory.isEmpty()){
				Script.sleep(Script.random(2000,3000));
				script.bank.depositAll();
				if(!script.bank.contains("Coins")){
					script.log("Out of coins, stopping script");
					script.stop();
				}
			}
		}
	}
	public boolean validate(){
		return true;
	}
}
