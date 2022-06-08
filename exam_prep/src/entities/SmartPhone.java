package entities;

public class SmartPhone extends Phone {
	protected int batteryDuration;
	
	@Override
	public String infoDevice()
	{
		return Integer.toString(batteryDuration);
	}
	
	public int getBatteryDuration() {
		return batteryDuration;
	}

	public void setBatteryDuration(int batteryDuration) throws Exception {
		if (batteryDuration <= 0)
			throw new Exception("Battery duration is invalid");
		this.batteryDuration = batteryDuration;
	}
}	
