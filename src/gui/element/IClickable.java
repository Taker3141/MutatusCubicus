package gui.element;

public interface IClickable
{
	public void leftClick(int mouseX, int mouseY);
	public void rightClick(int mouseX, int mouseY);
	public void leftReleased(int mouseX, int mouseY);
	public void rightReleased(int mouseX, int mouseY);
	public void entered(int mouseX, int mouseY);
	public void left(int mouseX, int mouseY);
	public boolean isOver(int mouseX, int mouseY);
}
