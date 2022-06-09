package entities;
import java.util.List;

public class VectThread implements Runnable {
	// Attributes
	protected List<ElectronicDevices> devicesList;
	protected double avgWeight;
	
	// file - name of binary file
	public VectThread(String file)
	{
		devicesList = Utils.readBinaryPhones(file);
	}

	@Override
	public void run() {
		if (devicesList.size() == 0)
		{	
			avgWeight = 0.0;
		}
		else
		{
			double sumWeight = 0.0;
			for (ElectronicDevices dev : devicesList)
			{
				Phone ph = (Phone) dev;
				sumWeight += ph.getWeight();
			}
			
			avgWeight = sumWeight / devicesList.size();
		}
	}
	
	// getters
	public List<ElectronicDevices> getDevicesList() {
		return devicesList;
	}
	public double getAvgWeight() {
		return avgWeight;
	}
	
	// setters
	public void setDevicesList(List<ElectronicDevices> devicesList) {
		this.devicesList = devicesList;
	}
	public void setAvgWeight(double avgWeight) {
		this.avgWeight = avgWeight;
	}
	
}
