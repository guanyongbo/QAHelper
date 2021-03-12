package com.noxgroup.qahelper.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: SongRan
 * @Date: 2021/1/22
 * @Desc: App数据
 */
public class AppDataConfig {

    public static Map<String, String> getAppData() {
        Map<String, String> appMap = new LinkedHashMap<>();
        appMap.put("NoxCleaner", "com.noxgroup.app.cleaner");
        appMap.put("NoxSecurity", "com.noxgroup.app.security");
        appMap.put("iClean", "com.iclean.master.boost");
        appMap.put("iSecurity", "com.security.antivirus.clean");
        appMap.put("vibe", "com.vibe.video.maker");
        appMap.put("NoxFit", "com.nox.fitness.weight.loss.workout");
        appMap.put("DailyBurn", "com.fit.daily.burn");
        appMap.put("NoxLucky", "com.wallpaper.background.hd");
        appMap.put("Bloom", "com.bloom.selfie.camera.beauty");
        appMap.put("Animate", "com.video.group.animate");
        appMap.put("Sleep", "com.noxgroup.app.sleeptheory");
        appMap.put("Lime", "com.noxgroup.app.noxlime");
        return appMap;
    }

    public static String getAppFullPath(String packageName) {
        Map<String, String> appMap = new LinkedHashMap<>();
        appMap.put("com.noxgroup.app.cleaner", "com.noxgroup.app.cleaner.module.main.SplashActivity");
        appMap.put("com.noxgroup.app.security", "com.noxgroup.app.security.module.splash.SplashActivity");
        appMap.put("com.iclean.master.boost", "com.iclean.master.boost.module.setting.SplashActivity");
        appMap.put("com.security.antivirus.clean", "com.security.antivirus.clean.module.home.SplashActivity");
        appMap.put("com.vibe.video.maker", "com.vibe.video.maker.ui.MainActivity");
        appMap.put("com.nox.fitness.weight.loss.workout", "com.noxgroup.app.fitness.module.splash.ui.SplashActivity");
        appMap.put("com.fit.daily.burn", "com.noxgroup.app.fitness.module.splash.ui.SplashActivity");
        appMap.put("com.wallpaper.background.hd", "com.wallpaper.background.hd.main.SplashActivity");
        appMap.put("com.bloom.selfie.camera.beauty", "com.bloom.selfie.camera.beauty.module.main.SplashActivity");
        appMap.put("com.video.group.animate", "com.video.group.animate/com.watchanime.group.animate.ui.activity.SplashActivity");
        appMap.put("com.noxgroup.app.sleeptheory", "com.noxgroup.app.sleeptheory.ui.activity.SplashActivity");
        appMap.put("com.noxgroup.app.noxlime", "com.noxgroup.app.noxlime.ui.splash.SplashActivity");
        return appMap.get(packageName);
    }
}
