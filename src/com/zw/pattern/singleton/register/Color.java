package com.zw.pattern.singleton.register;

/**
 * 枚举类单例
 * 常量，通常在API中使用
 */
public enum  Color {

    RED(){
        private int r = 255;
        private int g = 0;
        private int b = 0;
    },BLACK(){
        private int r = 0;
        private int g = 0;
        private int b = 0;
    },WHITE(){
        private int r = 255;
        private int g = 255;
        private int b = 255;
    }

}
