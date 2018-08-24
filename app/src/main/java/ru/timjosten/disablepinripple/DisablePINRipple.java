package ru.timjosten.disablepinripple;

import android.content.Context;
import android.util.AttributeSet;

import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.*;

public class DisablePINRipple implements IXposedHookLoadPackage
{
  private static final String TAG = DisablePINRipple.class.getSimpleName() + ": ";

  public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam)
  throws Throwable
  {
    if(!lpparam.packageName.equals("com.android.systemui"))
      return;

    try
    {
      XC_MethodHook hook = new XC_MethodHook()
      {
        @Override
        protected void afterHookedMethod(MethodHookParam param)
        throws Throwable
        {
          try
          {
            XposedHelpers.callMethod(param.thisObject, "setBackground", (Object)null);
          }
          catch(Throwable t)
          {
            XposedBridge.log(TAG + t);
          }
        }
      };

      XposedHelpers.findAndHookConstructor("com.android.keyguard.NumPadKey", lpparam.classLoader, Context.class, AttributeSet.class, int.class, hook);
      XposedHelpers.findAndHookConstructor("com.android.keyguard.NumPadKey", lpparam.classLoader, Context.class, AttributeSet.class, int.class, int.class, hook);
    }
    catch(Throwable t)
    {
      XposedBridge.log(TAG + t);
    }
  }
}
