package supportGUI;







class HardCodedParameters
{
  protected static String defaultParamFileName = "in.parameters";
  
  protected static final int defaultWidth = 1280;
  protected static final int defaultHeight = 1024;
  protected static final double resolutionShrinkFactor = 0.95D;
  protected static final double userBarShrinkFactor = 0.25D;
  protected static final double menuBarShrinkFactor = 0.5D;
  protected static final double logBarShrinkFactor = 0.15D;
  protected static final double logBarCharacterShrinkFactor = 0.1175D;
  protected static final double logBarCharacterShrinkControlFactor = 0.01275D;
  protected static final double menuBarCharacterShrinkFactor = 0.175D;
  protected static final int displayZoneXStep = 5;
  protected static final int displayZoneYStep = 5;
  protected static final int displayZoneXZoomStep = 5;
  protected static final int displayZoneYZoomStep = 5;
  protected static final double displayZoneAlphaZoomStep = 0.98D;
  protected static final Object loadingLock = new Object();
  protected static final String greetingsZoneId = String.valueOf(15539326);
  protected static final String simulatorZoneId = String.valueOf(5367678);
  
  HardCodedParameters() {}
  
  protected static <T> T instantiate(String className, Class<T> type) { try { return type.cast(Class.forName(className).newInstance());
    } catch (InstantiationException e) {
      throw new IllegalStateException(e);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }
}
