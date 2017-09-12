package gameengine.attributes.interfaces;

public interface RacerInterface extends AttributeInterface {

	public void flipStart();

	public void flipFinish();

	public boolean isStart();

	public boolean isFinish();

	public void increaseLapCount();

	public void decreaseLapCount();

	public int retrieveLapCount();

	public void flipFinishing();

	public boolean isFinishing();

}
