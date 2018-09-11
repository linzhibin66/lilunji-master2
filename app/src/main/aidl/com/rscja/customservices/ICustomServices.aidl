// ICustomServices.aidl
package com.rscja.customservices;

// Declare any non-default types here with import statements

interface ICustomServices {


     void setDate(int year,int month,int day);
     void setTime(int hourOfDay, int minute,int second);
     void setHomeKeyEnable(boolean enable);
     void setAppSwitchKeyEnable(boolean enable);
     void shutdown();
     void reboot();
//     void setLockScreen(boolean locked);
     void goToSleep();
     void setStatusBarDown(boolean enable);
     void changeBrightness(int brightness);
     void setDefaultLauncher();
     void setLauncher(String packageName,String className);
     //
     void setUsb(String config);

     int addAPN(String name,
                           String apn,
                           String type,
                           String proxy,
                           String port,
                           String mmsproxy,
                           String mmsport,
                           String user,
                           String server,
                           String password,
                           String mmsc);
      void setAPN(int id);
}