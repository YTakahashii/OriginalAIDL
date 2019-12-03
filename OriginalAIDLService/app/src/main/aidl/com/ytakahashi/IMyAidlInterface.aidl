// IMyAidlInterface.aidl
package com.ytakahashi;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    String getMessage(String name);
    String getJankenResult(int player_hands, int cpu_hands);
    int getCpuHands();
}
