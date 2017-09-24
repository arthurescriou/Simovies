package robotsimulator;

import characteristics.IFrontSensorResult;
import characteristics.IFrontSensorResult.Types;


public class FrontSensorResult
  implements IFrontSensorResult
{
  private IFrontSensorResult.Types type;
  
  public FrontSensorResult(IFrontSensorResult.Types type)
  {
    this.type = type;
  }
  
  public IFrontSensorResult.Types getObjectType() { return type; }
}
