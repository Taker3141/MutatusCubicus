package entity;

import inventory.Item;

public interface IEdible
{
	static enum FoodType
	{
		ROCK(1F), TOXIC_WASTE(10), ORGANIC(2.4F), FUEL(5);
		
		public final float digestPerSecond;
		
		private FoodType(float d)
		{
			digestPerSecond = d;
		}
	}
	
	FoodType getType();
	float getAmmount();
	float getEnergy();
	Item getItem();
}
