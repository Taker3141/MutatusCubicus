package entity;

public interface IEdible
{
	static enum FoodType
	{
		ROCK(0.5F), TOXIC_WASTE(5), ORGANIC(1.2F);
		
		public final float digestPerSecond;
		
		private FoodType(float d)
		{
			digestPerSecond = d;
		}
	}
	
	FoodType getType();
	float getAmmount();
	float getEnergy();
}
