package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class BuyJugs implements Activity{
	private Area store = new Area(new Position(3221,3421,0),new Position(3213,3409,0));
	private Script script;
	private String id = "BuyJugs";
	private int hopCount = 0;
	public BuyJugs(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run() throws InterruptedException{
		NPC shop = script.npcs.closest("Shop keeper");
		if(store != null){
			if(!script.store.isOpen() && shop != null && !script.myPlayer().isMoving()){
				if(shop.interact("Trade")){
					new ConditionalSleep(5000){
						@Override
						public boolean condition() throws InterruptedException {
							return script.store.isOpen();
						}
					}.sleep();
				}
			}
			if(script.store.isOpen()){
				if(script.store.getAmount("Empty jug pack") > 0){
					script.store.buy("Empty jug pack",5);
					Script.sleep(Script.random(1000,2000));
					script.store.close();
					Script.sleep(Script.random(1000,2000));
					if(script.inventory.contains("Empty jug pack")){
						script.inventory.getItem("Empty jug pack").interact("Open");
						Script.sleep(Script.random(5000,7000));
					}
					else{
						hopCount++;
						if(hopCount > 2){
							script.store.close();
							if(script.worlds.getCurrentWorld() == 394){
								script.worlds.hop(301);
								hopCount = 0;
							}
							else if(script.worlds.getCurrentWorld() == 301){
								script.worlds.hop(308);
								hopCount = 0;
							}
							else if(script.worlds.getCurrentWorld() == 308){
								script.worlds.hop(316);
								hopCount = 0;
							}
							else if(script.worlds.getCurrentWorld() == 316){
								script.worlds.hop(326);
								hopCount = 0;
							}
							else if(script.worlds.getCurrentWorld() == 326){
								script.worlds.hop(335);
								hopCount = 0;
							}
							else if(script.worlds.getCurrentWorld() == 335){
								script.worlds.hop(382);
								hopCount = 0;
							}
							else{
								script.worlds.hop(394);
								hopCount = 0;
							}
						}
					}
					Script.sleep(Script.random(5000,7000));
				}
			}
		}
	}
	public boolean validate(){
		return store.contains(script.myPosition()) && script.inventory.contains("Coins") && script.inventory.getAmount("Jug") < 2000;
	}
}
