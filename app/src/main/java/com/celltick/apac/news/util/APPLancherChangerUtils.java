package com.celltick.apac.news.util;

import android.content.ComponentName;
import android.content.pm.PackageManager;

/**
 * Created by Larryx on 2/1/2019.
 */

public class APPLancherChangerUtils {

    private PackageManager packageManager;

    public APPLancherChangerUtils(PackageManager packageManager){
        this.packageManager = packageManager;
    }

    public void changeIcon(ComponentName defaultComponent,ComponentName testComponent) {
        disableComponent(defaultComponent);
        enableComponent(testComponent);
    }

    public void changeDefaultIcon(ComponentName defaultComponent,ComponentName testComponent) {
        enableComponent(defaultComponent);
        disableComponent(testComponent);
    }

    /**
     * 启用组件
     *
     * @param componentName
     */
    private void enableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //已经启用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName
     */
    private void disableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            //已经禁用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }




}
